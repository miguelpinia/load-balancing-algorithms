#!/usr/bin/env bb
(require '[babashka.process :refer [shell]])

(def sizes [100 250 500 1000 2500 5000])
(def script "java -jar ccm-1.jar")

(defn build-json [size]
  (json/generate-string {:datafile "data/pigeon.cnf"
                         :structSize 65636
                         :iterations 5
                         :step size
                         :algorithms ["CILK"
                                      "CHASELEV"
                                      "IDEMPOTENT_LIFO"
                                      "IDEMPOTENT_FIFO"
                                      "IDEMPOTENT_DEQUE"
                                      "WS_MULT"
                                      "B_WS_MULT"]}
                        {:pretty true}))

(loop [sizes sizes]
  (when (seq sizes)
    (println (build-json (first sizes)))
    (recur (rest sizes))))

;; (shell "java" "-jar" "../ccm-1.jar")
