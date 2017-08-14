(ns concordance.core
  (:require [concordance.token :as t]))

(defn- frequency-step
  "A variant of clojure.core/frequency that receives the transient coll,
  so that it can build on the previous result."
  [trans-coll words]
  (reduce (fn [counts x]
            (assoc! counts x (inc (get counts x 0))))
          trans-coll
          words))

(defn word-frequency
  "Generate a frequency count of words contained in the sequence of String `lines`.
  Accepts an custom `tokenize-fn` to override the word tokenizer."
  ([lines]
    (word-frequency t/tokenize-words lines))
  ([tokenize-fn lines]
    (->> lines 
         (reduce #(frequency-step % (tokenize-fn %2))
                 (transient {}))
         (persistent!))))

(defn word-list
  "Generate a distinct word list for the sequence of String `lines`.
  Accepts an custom `tokenize-fn` to override the word tokenizer."
  ([lines]
    (word-list t/tokenize-words lines))
  ([tokenize-fn lines]
    (->> lines
         (reduce #(into % (tokenize-fn %2))
                 (sorted-set)))))
