(ns concordance.main
  (:gen-class)
  (:require [concordance.core :as c]
            [clojure.java.io :as jio]))

(let [valid-flags #{"--frequency"}]
  (defn parse-flags
    [args]
    (into #{} (filter valid-flags args))))

(defn naive-file-match
  "Match the first non-flag arg."
  [args]
  (first (remove #(.startsWith ^String % "-") args)))

(defn ^String format-word-list
  [result]
  (clojure.string/join "\n" result))

(defn ^String format-word-frequency
  "Sort the word frequency hash-map by frequency ascending, word ascending,
  and return as a tsv."
  [results]
  (let [result (into (sorted-map-by (fn [key1 key2]
                                      (compare [(get results key1) key1]
                                               [(get results key2) key2])))
                     results)]
    (clojure.string/join "\n"
      (for [[word cnt] result]
        (str word "\t" cnt)))))

(defn -main 
  "Run the concordance.jj
  TODO: When dependencies are allowed, replace with proper cli arg handler."
  [& args]
  (println
    (if-let [file-path (naive-file-match args)]
      (try
        (let [flags  (parse-flags args)
              method (if (contains? flags "--frequency")
                       c/word-frequency
                       c/word-list)
              result (with-open [rdr (jio/reader file-path :encoding "UTF-8")]
                       (method (line-seq rdr)))]
          (if (map? result)
            (format-word-frequency result)
            (format-word-list result)))
        (catch java.io.FileNotFoundException e
          (str "Error: " (.getMessage e))))
      "Error: A file argument was expected, but none was provided.")))
