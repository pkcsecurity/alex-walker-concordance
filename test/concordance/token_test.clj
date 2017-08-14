(ns concordance.token-test
  (:require [clojure.test :refer :all]
            [concordance.token :refer :all]))

(deftest negative-tokenize-words
  (testing "when tokenize-words has nothing to do."
    (are [x y] (= x y)
      [] (tokenize-words "")
      [] (tokenize-words ". 123 32f !!!"))))

(deftest positive-tokenize-words
  (testing "simple case of tokenize-words result"
    (is (= ["hello" "world"]
           (tokenize-words "hello world!")))))

(deftest positive-tokenize-words-contraction
  (testing "complex case of tokenize-words, contractions are expanded"
    (is (= ["you" "are"]
           (tokenize-words "you're")))))

(deftest positive-tokenize-words-numbers
  (testing "complex case of tokenize-words, numbers are not counted as words"
    (are [x y] (= x y)
      ["foo"] (tokenize-words "foo 123")
      ["foo"] (tokenize-words "foo 123f")
      ["foo"] (tokenize-words "foo 1x2"))))
