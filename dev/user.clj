(ns user
  (:require [snow.repl :as r]
            [web.systems :as sys]))

(defn -main []
  (r/start! sys/system-config))
