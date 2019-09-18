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
            [cryogen.core :as app-core]))



(defn init []
  (app-core/init-cryogen)
  
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
    (let [the-password (System/getenv "HARLANJI_PASSWORD")
          password (-> req :params :password)]
      (println "The password: " the-password)
      (println "Params: " (keys (:params req)))
      
      (if (and the-password (= the-password password))
        {:status 200
         :content-type "text/html"
         :body "Hey. Login."}
        {:status 401
         :content-type "text/html"
         :body "No login, buddy."}))))
          
  (route/files "/")
  (route/not-found "Page not found"))

(def handler (-> routes
                 (wrap-keyword-params)
                 (wrap-params)
                 (wrap-subdirectories)))
