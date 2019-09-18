(ns cryogen.server
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [redirect file-response]]
            [ring.util.codec :refer [url-decode]]
                        [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [cryogen-core.watcher :refer [start-watcher!]]
            [cryogen-core.compiler :refer [compile-assets-timed]]
            [cryogen-core.config :refer [resolve-config]]
            [cryogen-core.io :refer [path]]
            [selmer.filters :as selmer-filters]
            [com.harlanji.authorize :as authorize]
            [cryogen.core :as app-core]))



(defn init []
  (app-core/init-cryogen)
  (authorize/init "API-LOGIN-ID" "TOKEN")
  
  #_ (let [payment-request (authorize/create-test-payment-request)]
    (println "Payment request: " payment-request)
    (let [result (authorize/send-payment-request payment-request)]
      (println "Success! Result: " result)))
  
  (compile-assets-timed)
  (let [ignored-files (-> (resolve-config) :ignored-files)]
    (start-watcher! "content" ignored-files compile-assets-timed)
    (start-watcher! "themes" ignored-files compile-assets-timed)))

(defn wrap-subdirectories
  [handler]
  (fn [request]
    (let [{:keys [clean-urls blog-prefix public-dest]} (resolve-config)
          req-uri (.substring (url-decode (:uri request)) 1)
          res-path (condp = clean-urls
                     :trailing-slash (path req-uri "index.html")
                     :no-trailing-slash (if (or (= req-uri "")
                                                (= req-uri "/")
                                                (= req-uri
                                                   (.substring blog-prefix 1)))
                                          (path req-uri "index.html")
                                          (path (str req-uri ".html")))
                     :dirty (path (str req-uri ".html")))]
      (or (file-response res-path {:root public-dest})
          (handler request)))))

(defroutes routes
  (GET "/" [] (redirect (let [config (resolve-config)]
                          (path (:blog-prefix config)
                                (when (= (:clean-urls config) :dirty)
                                  "index.html")))))
  (GET "/login" [] (fn [req]
    (let [password (-> req :params :password)
          the-password (System/getenv (str "HARLANJI_PASSWORD"))
          ]
      (if (and the-password (= the-password password))
        {:status 200
         :content-type "text/html"
         :body "Hey. Login OK."}
        {:status 401
         :content-type "text/html"
         :body "No login, buddy."})))) 
                            
  (POST "/authorize" [  ] (fn [req] ;; POST... we GET from the address bar. Test...
    ;; I am leaving this hardcoded because I am going to switch to Bidi. I don't care to learn Compojure. For the reason that
    ;; it is macro driven, which requires special syntax memory. too much. I prefer bidi.
    ;; As of 9/3/2019 it is deleted via console, so the first POST will succeed.    
    (if-let [customer-id (authorize/create-test-subscription "biz@harlanji.com")] ;; POST accepts only create. Perfect. 
    
      {:body (str "ok. " customer-id)}
      {:body "error."}))) 
      
      
  (POST "/subscribe" [] (fn [req]
    (if-let [subscription-id (let [customer-profile-id "1920683905" ;"M_biz@harlanji.com"
                                   payment-profile-id "1833666257"
                                   amount 999.0]
                               (authorize/create-test-recurring-subscription-request
                                 customer-profile-id
                                 payment-profile-id
                                 amount))]
      {:body (str "ok.")}
      {:body "error."})))





          
  (route/files "/")
  (route/not-found "Page not found"))

(def handler (-> routes
                 (wrap-keyword-params)
                 (wrap-params)
                 (wrap-subdirectories)))
