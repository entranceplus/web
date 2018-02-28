(ns web.routes
  (:require [compojure.core :refer [routes GET ANY]]
            [ring.util.http-response :as response]
            [selmer.parser :as selmer]))

(defn ok-response [response]
  (-> (response/ok response)
      (response/header "Content-Type" "application/json; charset=utf-8")))

;; TODO move to snow library
(defn selmer-response
  "return html templated with the variable using selmer"
  [file & {:keys [data]}]
  (-> file
      (selmer/render-file (merge {} data))
      response/ok
      (response/header "Content-Type" "text/html; charset=utf-8")))

(defn hello-routes [_]
  (routes
   (GET "/hello" [] (ok-response {:msg "Hello world!!"}))))



(defn home-page []
  (selmer-response "public/index.html"))

(defn list-page [type]
  (let [data (case type
               "engineering" {:title "Ranklist for Engineering colleges"
                              :subtitle "Presenting the ranklists!"}
               "comedk" {:title "Ranklist for comedk colleges"
                          :subtitle "Presenting the ranklists!"}
               "medical" {:title "Ranklist for medical colleges"}
               "entrance-exam" {:title "Engineering Entrance Exams In India"
                                :subtitle "All the Entrance Exams on your fingertips now. We have mentioned all the important exams and their dates below alone with their cutoffs and the level on which the exam is held."})]
    (selmer-response "public/exam-list.html" :data data)))



(defn site [_]
  (routes
   (GET "/" [] (home-page))
   (GET "/ranklist/:type" [type] (list-page type))
   (GET "/entrance-exams" [] (list-page "entrance-exam"))
   (ANY "*" [] (home-page))))
