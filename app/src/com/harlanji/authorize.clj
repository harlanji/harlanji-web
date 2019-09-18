(ns com.harlanji.authorize
  
  (:import [java.math BigDecimal RoundingMode])

  (:import [net.authorize Environment])
  (:import [net.authorize.api.contract.v1
              MerchantAuthenticationType
              PaymentType CreditCardType
              TransactionRequestType
              TransactionTypeEnum
              CreateTransactionRequest
              CreateTransactionResponse
              MessageTypeEnum 
              ARBCreateSubscriptionRequest
              ARBCreateSubscriptionResponse
              CreateCustomerProfileRequest
              CreateCustomerProfileResponse
              CustomerPaymentProfileType
              CustomerTypeEnum
              CustomerProfileType
              CreateCustomerProfileRequest
              ValidationModeEnum
              CreateCustomerProfileResponse
              MessageTypeEnum
              ANetApiResponse
            ])
  (:import [net.authorize.api.controller.base
             ApiOperationBase])
  (:import [net.authorize.api.controller 
             CreateTransactionController
             CreateCustomerProfileController
             ARBCreateSubscriptionController])
             
             
(:import [net.authorize.api.contract.v1
            PaymentScheduleType
            PaymentScheduleType$Interval
            ARBSubscriptionUnitEnum
            ARBSubscriptionType
            CustomerProfileIdType
            CustomerAddressType
  ])
  
  ;; 
  (:import [javax.xml.datatype DatatypeFactory XMLGregorianCalendar])  
  )


(defn init [a b]
; Common code to set for all requests
  (ApiOperationBase/setEnvironment Environment/SANDBOX)
  
  (let [merchant-authentication-type (MerchantAuthenticationType.)]
    (.setName merchant-authentication-type "2A5u8tSQk3")
    (.setTransactionKey merchant-authentication-type "982VvF524bpMpzC4")
    
    (ApiOperationBase/setMerchantAuthentication merchant-authentication-type)
    
     (println "Authorize initialized.")))


(defn create-test-payment-request []
  (let [payment-type (PaymentType.)
        credit-card (CreditCardType.)]
        (.setCardNumber credit-card "4242424242424242")
        (.setExpirationDate credit-card"0922")
        (.setCreditCard payment-type credit-card)
        
        (let [txn-request (TransactionRequestType.)]
          (.setTransactionType txn-request (.value TransactionTypeEnum/AUTH_CAPTURE_TRANSACTION))
          (.setPayment txn-request payment-type)
          (.setAmount txn-request (BigDecimal. 500.00))
          
          txn-request)
        

))


(defn create-test-recurring-subscription-request-static []

  {"ARBCreateSubscriptionRequest" {
        "merchantAuthentication" {
            "name" "5KP3u95bQpv",
            "transactionKey" "346HZ32z3fP4hTG2"
        },
        "refId" "123456",
        "subscription" {
            "name" "Sample subscription",
            "paymentSchedule" {
                "interval" {
                    "length" "1",
                    "unit" "months"
                },
                "startDate" "2020-08-30",
                "totalOccurrences" "12",
                "trialOccurrences" "1"
            },
            "amount" "10.29",
            "trialAmount" "0.00",
            "payment" {
                "creditCard" {
                    "cardNumber" "4111111111111111",
                    "expirationDate" "2020-12"
                }
            },
            "billTo" {
                "firstName" "John",
                "lastName" "Smith"
            }
        }
    }
})


(defn create-test-recurring-subscription-request [customer-profile-id payment-profile-id amount]
  (let [schedule (PaymentScheduleType.)
        interval (PaymentScheduleType$Interval.)
        start-date (. (. DatatypeFactory (newInstance)) (newXMLGregorianCalendar)) ; we can condense this if we like.
        customer-profile (CustomerProfileIdType.)
        arb-subscription (ARBSubscriptionType.)
        api-request (ARBCreateSubscriptionRequest.)
  
        ]
        
    (.setLength interval (.shortValue 1))
    (.setUnit interval ARBSubscriptionUnitEnum/MONTHS)
    
    (.setInterval schedule interval)

    (.setDay start-date (.shortValue 3))
    (.setMonth start-date (.shortValue 9))
    (.setYear start-date 2019)

    (.setStartDate schedule start-date)
        
    
    
    (.setTotalOccurrences schedule (.shortValue 12))
    (.setTrialOccurrences schedule (.shortValue 1))
    
    (.setCustomerProfileId customer-profile customer-profile-id) ; this is the number returned from create-test-subscription.
    (.setCustomerPaymentProfileId customer-profile payment-profile-id)
    
    ; I should be able to manually grab these from the Customer Information Manager.    
    ; Yep.    
    
    ;(.setCustomerAddressId customer-profile "456") ; optional for non-shipped goods?
    

    (.setPaymentSchedule arb-subscription schedule)
    (.setAmount arb-subscription (.setScale (BigDecimal. amount) 2 RoundingMode/CEILING))
    (.setTrialAmount arb-subscription (.setScale (BigDecimal. 0.0) 2 RoundingMode/CEILING))
    (.setProfile arb-subscription customer-profile)
    
    (.setSubscription api-request arb-subscription)
    
       
       
    (let [controller (ARBCreateSubscriptionController. api-request)] 
	   (.execute controller)
      (let [	response (.getApiResponse controller)]
        (if response
          (if (= (.getResultCode (.getMessages response)) MessageTypeEnum/OK)
            (println "Subscription created. ID=" (.getSubscriptionId response))
            (println "Failed response. Code=" (.getCode (.get (.getMessage (.getMessages response)) 0))
              "Text=" (.getText (.get (.getMessage (.getMessages response)) 0)))
            ; System.out.println(response.getMessages().getMessage().get(0).getCode())
            ;; Failed response. Code= #object[net.authorize.api.contract.v1.MessageTypeEnum 0x1c91e770 ERROR]
            
            
            )
          (println "No response. Error."))))  
      
        
        
        
            
    
    [schedule interval arb-subscription]))


;; upon request... restarting with error removed.

; save types we use to import them:
; CreditCardType PaymentType 
(defn create-test-subscription
  "
  Creates a subscription for an email address.
  
  An error is expected each time after the first.
  
  We can test by using a new unique email address, for now.
  
  We use the Subscription manager in the web interface once the subscription has been created.
  
  We are moving on to ARB code, that works with created subscriptions.  
  "
  [customer-email-address]

  (let [credit-card (CreditCardType.)
        payment (PaymentType.)
        customer-profile (CustomerProfileType.)
        customer-payment-profile (CustomerPaymentProfileType.)
        customer-payment-address (CustomerAddressType.)
        ]

    (.setCardNumber credit-card "4111111111111111")
    (.setExpirationDate credit-card "1220")
    
    (.setCreditCard payment credit-card)
    
    ;; doto might be a good macro.
    ; (doto customer-payment-profile
    ;    (.Function "Argument")
    (.setCustomerType customer-payment-profile CustomerTypeEnum/INDIVIDUAL)
    (.setPayment customer-payment-profile payment)
       
    (.setMerchantCustomerId customer-profile (str "M_" customer-email-address))
    (.setDescription customer-profile (str "Profile description for " customer-email-address))
    (.setEmail customer-profile customer-email-address)
    
    (.setFirstName customer-payment-address  "H")
    (.setLastName customer-payment-address  "I")
    
    (.setBillTo customer-payment-profile customer-payment-address)
    
    (.add (.getPaymentProfiles customer-profile) customer-payment-profile)   
    
    
    
    (let [api-request (CreateCustomerProfileRequest.)
   

          _ (.setProfile api-request customer-profile)
          _ (.setValidationMode api-request ValidationModeEnum/TEST_MODE)  
    
          controller (CreateCustomerProfileController. api-request)]
   
      
      
      
      
      
      (if-let [response (.executeWithApiResponse controller)]
          (do
            (println "We got a response.")
            (if (= (-> response (.getMessages) (.getResultCode)) MessageTypeEnum/OK)
              (do
                (println (.getCustomerProfileId response))
                (str (.getCustomerProfileId response))
              )
              (println "Failed to create customer profile:" (-> response (.getMessages) (.getResultCode)))
              )
            )
          (do
            (println "Failed to create customer profile. Probably exists for this email address.")
            nil) ; println returns nil, but let's be clear.
        )
      
      ))
)

(defn send-payment-request [txn-request]
  (let [api-request (CreateTransactionRequest.)]
    (.setTransactionRequest api-request txn-request)
    (let [controller (CreateTransactionController. api-request)
          exec-result (.executeWithApiResponse controller)]
      
      (println "Exec result: " exec-result)
      
      ; is execute sync?
      (let [response (.getApiResponse controller)]
        (if response
          (if (= (.getResultCode (.getMessages response)) MessageTypeEnum/OK)
            (let [result (.getTransactionResponse response)]
              (if (.equals (.getResponseCode result) "1")
                (println "SUCCESS. == " (.getResponseCode result) " // " (.getAuthCode result) " // " (.getTransId result))
                (println "Failed transaction a: " (.getResponseCode result))))
            (println "Failed response. Code=" (.getCode (.get (.getMessage (.getMessages response)) 0))
              "Text=" (.getText (.get (.getMessage (.getMessages response)) 0))))
          (println "No API response."))))))