#!/usr/bin/env bb

(require '[babashka.process :refer [shell]])

(def script "java -jar ws.jar")

(def graph-combinations [{:graph "TORUS_2D" :structSize 256 :vertexSize 1000 :directed true}
                         {:graph "TORUS_2D" :structSize 256 :vertexSize 1000 :directed false}
                         {:graph "TORUS_2D" :structSize 1000000 :vertexSize 1000 :directed true}
                         {:graph "TORUS_2D" :structSize 1000000 :vertexSize 1000 :directed false}
                         {:graph "TORUS_2D_60" :structSize 256 :vertexSize 1000 :directed true}
                         {:graph "TORUS_2D_60" :structSize 256 :vertexSize 1000 :directed false}
                         {:graph "TORUS_2D_60" :structSize 1000000 :vertexSize 1000 :directed true}
                         {:graph "TORUS_2D_60" :structSize 1000000 :vertexSize 1000 :directed false}
                         {:graph "TORUS_3D" :structSize 256 :vertexSize 100 :directed true}
                         {:graph "TORUS_3D" :structSize 256 :vertexSize 100 :directed false}
                         {:graph "TORUS_3D" :structSize 1000000 :vertexSize 100 :directed true}
                         {:graph "TORUS_3D" :structSize 1000000 :vertexSize 100 :directed false}
                         {:graph "TORUS_3D_40" :structSize 256 :vertexSize 100 :directed true}
                         {:graph "TORUS_3D_40" :structSize 256 :vertexSize 100 :directed false}
                         {:graph "TORUS_3D_40" :structSize 1000000 :vertexSize 100 :directed true}
                         {:graph "TORUS_3D_40" :structSize 1000000 :vertexSize 100 :directed false}
                         {:graph "RANDOM" :structSize 256 :vertexSize 1000000 :directed true}
                         {:graph "RANDOM" :structSize 256 :vertexSize 1000000 :directed false}
                         {:graph "RANDOM" :structSize 1000000 :vertexSize 1000000 :directed true}
                         {:graph "RANDOM" :structSize 1000000 :vertexSize 1000000 :directed false}])

(def zerocost-combinations [{:structSize 256 :iterations 70 :k 5, :type :putSteals}
                            {:structSize 1000000 :iterations 70 :k 5 :type :putSteals}
                            {:structSize 10000000 :iterations 70 :k 5 :type :putSteals}
                            {:structSize 256 :iterations 70 :k 5, :type :putTakes}
                            {:structSize 1000000 :iterations 70 :k 5 :type :putTakes}
                            {:structSize 10000000 :iterations 70 :k 5 :type :putTakes}])

(def sat-stats-combinations [{:type :satStats :step 50}
                             {:type :satStats :step 100}
                             {:type :satStats :step 250}
                             {:type :satStats :step 500}
                             {:type :satStats :step 1000}
                             {:type :satStats :step 2500}
                             {:type :satStats :step 5000}])

(def sat-mult-combinations [{:type :satCount :step 50}
                            {:type :satCount :step 100}
                            {:type :satCount :step 250}
                            {:type :satCount :step 500}
                            {:type :satCount :step 1000}
                            {:type :satCount :step 2500}
                            {:type :satCount :step 5000}])


(defn build-json-zerocost [{:keys [structSize type iterations k]} algorithms]
  (json/generate-string {:algorithms algorithms
                         type        {:structSize    structSize
                                      :numOperations 10000000
                                      :iterations    iterations
                                      :k             k}}))

(defn build-json-multiplicity [{:keys [graph vertexSize structSize directed]} algorithms]
  (json/generate-string {:algorithms          algorithms
                         :spanningTreeOptions {:graphType        graph
                                               :vertexSize       vertexSize
                                               :structSize       structSize
                                               :stepSpanningType "COUNTER"
                                               :directed         directed
                                               :stealTime        false
                                               :iterations       5}}
                        {:pretty true}))

(defn build-json-statistics [{:keys [graph vertexSize structSize directed]} algorithms]
  (json/generate-string {:algorithms algorithms
                         :statistics {:graphType        graph
                                      :vertexSize       vertexSize
                                      :structSize       structSize
                                      :stepSpanningType "COUNTER"
                                      :directed         directed
                                      :stealTime        false}}
                        {:pretty true}))

(defn build-json-sat [{:keys [type step]}]
  (json/generate-string {type {:datafile "data/pigeon.cnf"
                               :structSize 65536
                               :iterations 1
                               :step step
                               :algorithms ["CILK"
                                            "CHASELEV"
                                            "IDEMPOTENT_LIFO"
                                            "IDEMPOTENT_FIFO"
                                            "IDEMPOTENT_DEQUE"
                                            "WS_MULT"
                                            "B_WS_MULT"]}
                         :algorithms []}
                        {:prettty true}))

(defn write-json [filename json-str]
  (spit filename json-str))

(defn execute-experiment-zerocost [configfile algorithms]
  (doseq [experiment zerocost-combinations]
    (println (str "Zero cost experiment: " experiment))
    (fs/create-dirs "zerocost")
    (->> (build-json-zerocost experiment algorithms)
         (write-json configfile))
    (dotimes [_ 20] (shell script))))

(defn execute-experiment-statistics [configfile algorithms]
  (doseq [experiment graph-combinations]
    (println (str "Graph experiment: " experiment))
    (fs/create-dirs "graph/stats")
    (write-json configfile (build-json-statistics experiment algorithms))
    (dotimes [_ 20] (shell script))))

(defn execute-experiment-multiplicity [configfile algorithms]
  (doseq [experiment graph-combinations]
    (println (str "Graph experiment: " experiment))
    (fs/create-dirs "graph/multiplicity")
    (write-json configfile (build-json-multiplicity experiment algorithms))
    (shell script)))

(defn execute-experiment-sat-stats [configfile]
  (doseq [experiment sat-stats-combinations]
    (println (str "SAT Stats Experiment: " experiment))
    (fs/create-dirs "sat/stats")
    (write-json configfile (build-json-sat experiment))
    (dotimes [_ 20] (shell script))))

(defn execute-experiment-sat-multiplicity [configfile]
  (doseq [experiment sat-stats-combinations]
    (println (str "SAT Multiplicity Experiment: " experiment))
    (fs/create-dirs "sat/multiplicity")
    (write-json configfile (build-json-sat experiment))
    (shell script)))

(def only-deque ["IDEMPOTENT_DEQUE"])
(def graph-algs ["CILK" "CHASELEV" "IDEMPOTENT_LIFO" "IDEMPOTENT_FIFO" "IDEMPOTENT_DEQUE" "WS_NC_MULT_OPT" "B_WS_NC_MULT_OPT" "WS_NC_MULT_LA_OPT" "B_WS_NC_MULT_LA_OPT"])
(def zerocost-algs ["CILK" "CHASELEV" "IDEMPOTENT_LIFO" "IDEMPOTENT_FIFO" "IDEMPOTENT_DEQUE" "WS_NC_MULT_OPT" "B_WS_NC_MULT_OPT" "WS_NC_MULT_LA_OPT" "B_WS_NC_MULT_LA_OPT"])

(execute-experiment-zerocost "config.json" zerocost-algs)
(execute-experiment-multiplicity "config.json" graph-algs)
(execute-experiment-statistics "config.json" only-deque)
(execute-experiment-sat-stats "config.json")
(execute-experiment-sat-multiplicity "config.json")
