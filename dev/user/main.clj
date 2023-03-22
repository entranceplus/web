(ns user.main
  (:require [web.systems :as sys]))

(defn -main []
  (sys/start-systems [{:snow.systems/system-fn sys/system-config}]))
