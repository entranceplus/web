(ns web.core
  (:gen-class)
  (:require [web.systems :as system]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (system/start-systems [{:snow.systems/system-fn system/system-config}]))
