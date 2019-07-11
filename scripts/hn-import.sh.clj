#!/usr/bin/env lein-exec

; bootstrap dependencies
(use '[leiningen.exec :only (deps)])

(deps '[[clj-http "3.10.0"]
        [org.clojure/data.json "0.2.6"]
        [org.clojure/tools.cli "0.4.2"]])

; load dependencies
(require '[clj-http.client :as http]
         '[clojure.data.json :as json]
         '[clojure.tools.cli :refer [parse-opts]])
         
(import '[java.net URLEncoder])

; validate arguments
(when (not= (count *command-line-args*) 2)
  (println "Usage: [START_REPL=1]" (first *command-line-args*) "hn-username")
  (System/exit 1))

(def hn-username (nth *command-line-args* 1))
(def start-repl? (= (System/getenv "START_REPL") "1"))

; define functions

(defn encode-uri-component
  [uri-component]
  (URLEncoder/encode (str uri-component)))

(defn user-uri [username]
  (str "https://hacker-news.firebaseio.com/v0/user/" (encode-uri-component username) ".json"))

(defn item-uri [item-id]
  (str "https://hacker-news.firebaseio.com/v0/item/" (encode-uri-component item-id) ".json"))

(def user-response (http/get (user-uri hn-username) {:accept :json}))

(def user-object (json/read-str (:body user-response) :key-fn keyword))

(when (nil? user-object)
  (println "No such user:" hn-username)
  (System/exit 3))

(def post-ids (:submitted user-object))

(def item-response (http/get (item-uri (first post-ids)) {:accept :json}))
(def item-object (json/read-str (:body item-response) :key-fn keyword))

(println "Username:" (:id user-object))
(println "Karma:" (:karma user-object))
(println "About:" (:about user-object))
(println "Number of posts:" (count post-ids))

(when-not (nil? item-object)
  (println "First item html:\n" (:text item-object)))


; explore

(when start-repl?
  (clojure.main/repl))