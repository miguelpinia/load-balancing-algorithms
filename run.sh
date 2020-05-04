#!/bin/bash

echo '#############################
# Seis millones de vértices #
#############################'

# echo 'graphType: TORUS_2D
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D_60
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D_40
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: KGRAPH
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D_60
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D_40
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: KGRAPH
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: true' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D_60
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D_40
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: KGRAPH
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_2D_60
# vertexSize: 2500
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: TORUS_3D_40
# vertexSize: 185
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: KGRAPH
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: RANDOM
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
# echo 'graphType: RANDOM
# vertexSize: 6000000
# stepSpanningType: DOUBLE_COLLECT
# iterations: 5
# algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
# directed: false' > properties.props && \
#     java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt

echo '#########################
# Un millon de vértices #
#########################'

echo 'graphType: TORUS_2D
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D_60
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D_40
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: KGRAPH
vertexSize: 1000000
stepSpanningType: DOUBLE_COLLECT
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D_60
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D_40
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: KGRAPH
vertexSize: 1000000
stepSpanningType: DOUBLE_COLLECT
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_LIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: true' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D_60
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D_40
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: KGRAPH
vertexSize: 1000000
stepSpanningType: DOUBLE_COLLECT
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_2D_60
vertexSize: 1000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: TORUS_3D_40
vertexSize: 100
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: KGRAPH
vertexSize: 1000000
stepSpanningType: DOUBLE_COLLECT
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: RANDOM
vertexSize: 1000000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;NBWSMULT_FIFO;B_NBWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt && \
echo 'graphType: RANDOM
vertexSize: 1000000
stepSpanningType: COUNTER
iterations: 5
algorithms: CILK;CHASELEV;IDEMPOTENT_FIFO;WFWSMULT_FIFO;B_WFWSMULT_FIFO
directed: false' > properties.props && \
    java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 |& tee -a ejecucion_experimentos.txt
