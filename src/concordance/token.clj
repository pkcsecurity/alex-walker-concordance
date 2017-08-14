(ns concordance.token
  (:require [clojure.string :as str]))

;; Due to a lack of proper POS tagging, this makes naive assumptions
;; about past/present tense.
(let [contractions {
        "aren't" "are not"
        "can't" "cannot"
        "couldn't" "could not"
        "didn't" "did not"
        "doesn't" "does not"
        "don't" "do not"
        "hadn't" "had not"
        "hasn't" "has not"
        "haven't" "have not"
        "he'd" "he had"
        "he'll" "he will"
        "he's" "he is"
        "I'd" "I would"
        "I'll" "I will"
        "I'm" "I am"
        "I've" "I have"
        "isn't" "is not"
        "let's" "let us"
        "mightn't" "might not"
        "mustn't" "must not"
        "shan't" "shall not"
        "she'd" "she had"
        "she'll" "she will"
        "she's" "she is"
        "shouldn't" "should not"
        "that's" "that is"
        "there's" "there is"
        "they'd" "they would"
        "they'll" "they will"
        "they're" "they are"
        "they've" "they have"
        "we'd" "we had"
        "we're" "we are"
        "we've" "we have"
        "weren't" "were not"
        "what'll" "what will"
        "what're" "what are"
        "what's" "what is"
        "what've" "what have"
        "where's" "where is"
        "who's" "who is"
        "who'll" "who will"
        "who're" "who are"
        "who've" "who have"
        "won't" "will not"
        "wouldn't" "would not"
        "you'd" "you had"
        "you'll" "you will"
        "you're" "you are"
        "you've" "you have"}]
  (defn- expand-contraction [s]
    (get contractions s s)))

(defn- replace-number
  "Anything starting with a number is replaced with empty string."
  [s]
  (if (re-matches #"\d+.*" s)
    ""
    s))

(defn tokenize-words
  "Basic word tokenizer. Handles an 80/20 of punctuation but is not complete."
  [^String s]
  (->> (-> s
           (str/lower-case)
           (expand-contraction)
           (str/replace #"\-+" " ")
           ;; Strip other punctuation -- not comprehensive
           (str/replace #"[\p{P}=\$\£\|\+]" "")
           (str/split #"\s+"))
       (map replace-number)
       (keep not-empty)))
