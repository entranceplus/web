(def project 'web)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"src/cljs" "src/clj" "resources"}
          :checkouts '[[snow "0.1.0-SNAPSHOT"]
                       [voidwalker "0.1.0-SNAPSHOT"]]
          :dependencies   '[[org.clojure/clojure "1.9.0"]
                            [org.clojure/clojurescript "1.9.946"]
                            [org.immutant/immutant "2.1.9"]
                            [org.danielsz/system "0.4.2-SNAPSHOT"]
                            [org.clojure/java.jdbc "0.7.3"]
                            [org.clojure/tools.cli "0.3.5"]
                            [org.clojure/tools.logging "0.4.0"]
                            [metosin/ring-http-response "0.9.0"]
                            [selmer "1.11.7"]
                            [snow "0.1.0-SNAPSHOT"]
                            [voidwalker "0.1.0-SNAPSHOT"]
                            [compojure "1.6.0"]
                            [environ "1.1.0"]
                            [venantius/pyro "0.1.1"]
                            [boot-environ "1.1.0"]
                            [ring "1.6.3"]
                            [org.clojure/tools.nrepl "0.2.12"]
                            [ring/ring-defaults "0.3.1"]
                            [ring-middleware-format "0.7.2"]
                            [adzerk/boot-reload "0.5.2" :scope "test"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [reagent "0.8.0-alpha2"]
                            [reagi "0.10.1"]
                            [funcool/bide "1.6.0"]
                            [adzerk/boot-reload "0.5.2" :scope "test"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [adzerk/boot-cljs "2.1.4" :scope "test"]
                            [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                            [adzerk/boot-test "1.2.0" :scope "test"]
                            [adzerk/boot-reload "0.5.2" :scope "test"]
                            [com.cemerick/piggieback "0.2.1" :scope "test"]
                            [binaryage/devtools "0.9.4" :scope "test"]
                            [weasel "0.7.0" :scope "test"]])

(require '[system.boot :refer [system run]]
         '[web.systems :refer [dev-system]]
         '[clojure.edn :as edn]
         '[environ.core :refer [env]]
         '[environ.boot :refer [environ]])

(require '[adzerk.boot-cljs :refer :all]
         '[adzerk.boot-cljs-repl :refer :all]
         '[adzerk.boot-reload :refer :all])

(task-options!
 aot {:namespace   #{'web.core}}
 jar {:main        'web.core
      :file        (str "web-" version "-standalone.jar")})

(deftask dev
     "run a restartable system"
     []
     (comp
      (environ :env {:http-port "7000"})
      (watch :verbose true)
      (system :sys #'dev-system
              :auto true
              :files ["routes.clj" "systems.clj"])
      (repl :server true
            :host "127.0.0.1")
      (reload :asset-path "public")
      (cljs-repl)
      (cljs :source-map true :optimizations :none)))

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run-project
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[kongauth.core :as app])
  (apply (resolve 'app/-main) args))

(require '[adzerk.boot-test :refer [test]])
