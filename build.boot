(def project 'entranceplus/web)
(def version "0.1.0")

(set-env! :resource-paths #{"src/cljs" "src/clj" "resources"}
          :checkouts '[[snow "0.1.1"]
                       [voidwalker "0.1.2"]]
          :dependencies   '[[org.clojure/clojure "1.9.0"]
                            [org.clojure/clojurescript "1.9.946"]
                            [org.immutant/immutant "2.1.9"]
                            [org.danielsz/system "0.4.2-SNAPSHOT"]
                            [org.clojure/java.jdbc "0.7.3"]
                            [org.clojure/tools.cli "0.3.5"]
                            [org.clojure/tools.logging "0.4.0"]
                            [metosin/ring-http-response "0.9.0"]
                            [selmer "1.11.7"]
                            [proto-repl "0.3.1"]
                            [snow "0.1.1"]
                            [voidwalker "0.1.2"]
                            [compojure "1.6.0"]
                            [entranceplus/bootlaces "0.1.14"]
                            [environ "1.1.0"]
                            [venantius/pyro "0.1.1"]
                            [boot-environ "1.1.0"]
                            [ring "1.6.3"]
                            [org.clojure/core.async "0.4.474"]
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
 pom {:project project
      :version version}
 jar {:main        'web.core
      :file        (str "web-" version "-standalone.jar")})

(require '[adzerk.bootlaces :refer :all])
(bootlaces! version :dont-modify-paths? true)


(deftask dev
     "run a restartable system"
     []
     (comp
      (environ :env {:http-port "7000"
                     :dbname "voidwalker"
                     :dbuser "void"
                     :db-path "/home/akash/workspace/voidwalker/void-konserve"
                     :dbpassword "walker"})
      (watch :verbose true)
      (system :sys #'dev-system
              :auto true
              :files ["routes.clj" "systems.clj"])
      (repl :server true
            :port 7001
            :bind "0.0.0.0")
      (reload :asset-path "public")))
      ; (cljs-repl)
      ; (cljs :source-map true :optimizations :none)))

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask lib-install
  "install as lib"
  []
  (comp (pom)
        (jar)
        (install)))

(deftask run-project
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[kongauth.core :as app])
  (apply (resolve 'app/-main) args))

(deftask deps [])

(deftask publish []
  (comp
   (build-jar)
   (push-release)))


(require '[adzerk.boot-test :refer [test]])