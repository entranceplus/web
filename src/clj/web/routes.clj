(ns web.routes
  (:require [compojure.core :refer [routes GET ANY]]
            [ring.util.http-response :as response]
            [selmer.parser :as selmer]
            [voidwalker.content :as content]))

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
  (selmer-response "public/home.html"))

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

(defn get-all-articles [void-db]
  (map (fn [{:keys [url] :as post}]
          (assoc post :url (str "/content/" url)))
       (content/get-post void-db nil)))

(defn get-blog-data [void-db]
  (let [articles (get-all-articles void-db)]
    {:articles articles
     :banner (first articles)}))

;;(require '[entranceplus.core :as e])
;;(def ep-db  (->  @entrance-plus.core/systems last :void-db :store))

;;(def db (-> system.repl/system :void-db :store))
;; (map :datasource  (content/get-templated-post ep-db))

;; (:content  (get-detailed-article ep-db "ranklist-engineering"))

(defn get-detailed-article
  "here detail means with datasource expanded"
  [db url]
  (->> db
       content/get-templated-post
       (filter #(= url (:url %)))
       first))

;; (->>  (get-detailed-article db "that") :datasource first :data first :mq )

; (content/get-post db {:url "http://wwwasas.google.com"})
;
; (def void-db (:void-db system.repl/system))
; (get-all-articles void-db)
; (get-blog-data void-db)
(defn site [{{db :store} :void-db}]
  (routes
   (GET "/" [] (home-page))
   (GET "/ranklist/:type" [type] (list-page type))
   (GET "/entrance-exams" [] (list-page "entrance-exam"))
   (GET "/blog" [] (selmer-response "public/blog.html"
                                    :data (get-blog-data db)))
   (GET "/mentorship" [] (selmer-response "public/mentorship.html"))
   (GET "/terms" [] (selmer-response "public/terms.html"))
   (GET "/article" [] (selmer-response "public/article.html"))
   (GET "/disclaimer" [] (selmer-response "public/disclaimer.html"))
   (GET "/content/:url" [url] (selmer-response "public/article.html"
                                               :data (get-detailed-article db url)))
   (ANY "*" [] (home-page))))

;; next steps
;; transcriptor for testing apis
;; setup test suite for each libraries
;; test and deploy snapshot for each lib via circleci
