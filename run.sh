#!/bin/bash
echo '#########################
# Un millón de vértices #
#########################'

source analyzer/.venv/bin/activate && \
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected

echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_2D_60
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_3D
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_3D_40
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
#undirected
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
#RANDOM
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CHASELEV",
    "IDEMPOTENT_LIFO",
    "IDEMPOTENT_FIFO",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&

# CILK & CHASELEV

echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_2D_60
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirecte
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_2D_60",
    "vertexSize": 1000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_3D
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# TORUS_3D_40
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
# undirected
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "TORUS_3D_40",
    "vertexSize": 100,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
#RANDOM
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": true,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json&&
# undirected
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 1000000
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json  &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 32768
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 4096
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json &&
echo '{
  "algorithms": [
    "CILK",
    "CHASELEV",
    "WS_NC_MULT",
    "B_WS_NC_MULT",
    "WS_NC_MULT_LA",
    "NEW_B_WS_NC_MULT_LA"
  ],
  "spanningTreeOptions": {
    "graphType": "RANDOM",
    "vertexSize": 1000000,
    "stepSpanningType": "COUNTER",
    "iterations": 5,
    "directed": false,
    "stealTime": false,
    "structSize": 256
  }
}' > config.json && \
java -jar cc.jar && \
python analyzer/main.py -st experiment-1.json
