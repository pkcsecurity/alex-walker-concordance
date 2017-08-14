# concordance

A word tokenization and frequency library and program, written in Clojure, for the PKC interview project.



## Program Quickstart

Run directly using [leiningen](https://github.com/technomancy/leiningen):

```
$ lein run --frequency sampleFile.txt
```
Or build the uberjar for better performance:

```
$ lein uberjar
$ java -jar ./target/concordance-standalone.jar --frequency sampleFile.txt
```


## Library Quickstart

The main functionality is provided by `concordance.core` namespace:

```
(require '[concordance.core :refer (word-list word-frequency)])
```

```
(word-list lines)
=> #{...}

(word-frequency lines)
=> {...}
```

### Library Data (lines)

Both `word-list` and `word-frequency` expect a lazy sequence of `String` lines.  This is to give you flexibility for how you source your data, whether it be from a file or a database or a web form.

As a refresher for files, prefer this aproach over `slurp`, to read lazily:

```
(with-open [reader (clojure.java.io/reader "sampleFile.txt")]
  (word-list (line-seq reader)))
```

Or just give it sample data:

```
(word-frequency ["The quick brown fox jumped over the lazy dog." "Hello dog!"])
```

### Library Custom Tokenizer

By default, `word-list` and `word-frequency` use a naive tokenizer due to a restriction on external dependencies.  However, with that in mind, you can provide your own custom tokenizer.

```
(defn my-tokenizer [line]
  (clojure.string/split line #"\s+"))

(word-list my-tokenizer lines)
(word-frequency my-tokenizer lines)
```
Note, your custom tokenizer comes before the lines, as the first arg.

## Tests

```
$ lein test
```
