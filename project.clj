(defproject concordance "0.1.0"
  :author       "Alex Walker <alex.p.walker@gmail.com>"
  :description  "Concordance: word tokenization and frequency"
  :url          "https://bitbucket.org/rumrunner/concordance"
  :profiles {
    :dev {
      :main         concordance.main
      :repl-options {:init-ns concordance.core}}
    :uberjar {
      :uberjar-name "concordance-standalone.jar"
      :main         concordance.main
      :aot          :all}}
  :dependencies [[org.clojure/clojure "1.8.0"]])
