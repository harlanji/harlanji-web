(ns cryogen.core
  (:gen-class)
  (:require [cryogen-core.compiler :refer [compile-assets-timed]]
            [cryogen-core.plugins :refer [load-plugins]]
            [selmer.filters :as selmer-filters]))

(defn selmer-some-tag? [x y]
  (let [result (some (fn [tag]    (= (str (:name tag)) (str (read-string y))))
                      x)]
    [:safe (str (boolean result))]))


(defn init-cryogen []
  (selmer-filters/add-filter! :selmer-some-tag? selmer-some-tag?)
  (load-plugins))

(defn -main []
  (init-cryogen)
  (compile-assets-timed)
  (System/exit 0))
