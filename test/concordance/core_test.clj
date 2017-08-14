(ns concordance.core-test
  (:require [clojure.test :refer :all]
            [concordance.core :refer :all]))

(deftest negative-word-list
  (testing "when word-list has nothing to do."
    (are [x y] (= x y)
      #{} (word-list nil)
      #{} (word-list [])
      #{} (word-list '())
      #{} (word-list [""]))))

(deftest negative-word-frequency
  (testing "when word-frequency has nothing to do."
    (are [x y] (= x y)
      {} (word-frequency nil)
      {} (word-frequency [])
      {} (word-frequency '())
      {} (word-frequency [""]))))

(deftest positive-word-list
  (testing "simple case of word-list result"
    (is (= (sorted-set "hello" "world")
           (word-list ["hello world!"])))))

(deftest positive-word-list-overlap
  (testing "complex case of word-list, same word appears on many lines"
    (is (= (sorted-set "hello" "world")
           (word-list ["hello world!" "hello"])))))

(deftest positive-word-frequency
  (testing "simple case of word-frequency result"
    (is (= {"hello" 1 "world" 1}
           (word-frequency ["hello world!"])))))

(deftest positive-word-frequency-overlap
  (testing "complex case of word-frequency, same word appears on many lines"
    (is (= {"hello" 2 "world" 1}
           (word-frequency ["hello world!" "hello"])))))
