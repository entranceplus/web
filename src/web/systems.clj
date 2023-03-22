(ns web.systems
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [web.routes :refer [hello-routes site]]
            ;; [ring.middleware.format :refer [wrap-restful-format]]
            [ring.middleware.defaults :refer [wrap-defaults
                                              site-defaults
                                              api-defaults]] 
            (system.components
             [immutant-web :refer [new-immutant-web]]
             [endpoint :refer [new-endpoint]]
             [middleware :refer [new-middleware]]
             [repl-server :refer [new-repl-server]]
            ;;  [konserve :refer [new-konserve]]
             [handler :refer [new-handler]])))
                                        ;
                                        ; (do
                                        ;   (require '[pyro.printer :as printer])
                                        ;   (printer/swap-stacktrace-engine!))


;; (def rest-middleware
;;   (fn [handler]
;;     (wrap-restful-format handler
;;                          :formats [:json-kw]
;;                          :response-options {:json-kw {:pretty true}})))

(defn system-config [config]
  [::site-endpoint (component/using (new-endpoint site)
                                    [::site-middleware])
   ::api-endpoint (component/using (new-endpoint hello-routes)
                                   [::api-middleware])
  ;;  ::void-db (new-konserve :type :filestore :path (:db-path config))
   ::site-middleware (new-middleware {:middleware [[wrap-defaults site-defaults]]})
   ::api-middleware (new-middleware
                     {:middleware [[wrap-defaults api-defaults]]})
   ::handler (component/using (new-handler) [::api-endpoint ::site-endpoint])
   ::api-server (component/using (new-immutant-web :port 8080)
                                 [::handler])])

(defn make-system-map [system-fn config prod?]
  (apply component/system-map (cond-> (system-fn config)
                                prod? (conj :repl-server (-> 8081
                                                             new-repl-server)))))

(defn gen-system
  ([system-config-gen-fn]
   (gen-system system-config-gen-fn env))
  ([system-config-gen-fn config & {:keys [prod?]}]
   (make-system-map system-config-gen-fn config prod?)))


(defn- start-system [{:keys [snow.systems/system-fn snow.systems/config]} prod?]
  (println "Starting a system with config " config " system-fn is " system-fn)
  (component/start (gen-system system-fn config :prod? prod?)))

(defn start-systems [systems & {:keys [prod?]}]
  (doall (map #(-> % (start-system prod?))
              systems)))

(defn stop-systems [systems]
  (map #(component/stop %) systems))

(defn dev-system []
  (gen-system system-config))

(defn prod-system
  "Assembles and returns components for a production deployment"
  []
  (merge (dev-system)
         (component/system-map
          :repl-server (new-repl-server (read-string (env :repl-port))))))
