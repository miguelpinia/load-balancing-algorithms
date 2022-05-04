![CI and Build configuration for load-balancing algorithms.](https://github.com/miguelpinia/load-balancing-algorithms/workflows/CI%20and%20Build%20configuration%20for%20load-balancing%20algorithms./badge.svg)

# Load balancing algorithms

There are many algorithms for load-balancing in multiprocessors.
In this repository we'll implement the next algorithms:

* Spanning Tree with SIMPLE methodology
* Spanning Tree with Cilk load-balancing algorithm (THE algorithm)
* Spanning Tree with Chese-Lev load-balancing algorithm
* Spanning Tree with Idempotent load-balancing algorithm (FIFO based)
* Spanning Tree with Idempotent load-balancing algorithm (LIFO based)
* Spanning Tree with Idempotent load-balancing algorithm (DEQUE based)

## Description
This project contains the implementation of many algorithm for
work-stealing, used to give load-balacing for concurrent executions of
programs. In this project, we test these work-stealing algorithms
solving the problem known as parallel spanning tree. Spanning tree is
an important problem because help to build another parallel graph
algorithms.

## Requirements

This project require the following dependencies:

- OpenJDK 17 (17.0.3)
- maven 3.6.3

## Installation

This project can be installed as usual way, like another projects. It
means that you can install it with the next instruction:

```sh
mvn clean install
```

To build a standalone jar with all dependencies included, the
instruction is:

```sh
mvn clean compile assembly:single
```

## Usage

Using the standalone jar we will running as follows:

```sh
$ java -jar concurrentAlgorithms-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Currently, we have a template script to run a set of experiments and
build the charts about the experiments. To run this script, you need
use at least the version `python 3.10` In a dedicated server, run:

```sh
$ nohup python3 run2.py &
```

Inside the script there are many options to configure the experiments.

### config.json

For that to happen, it's necessary have a file named
properties.props. This file must be put in the same directory where is
the standalone jar. The file has the format of a txt file with the key-value elements. The following keys are avalaible:

- **graphType**: The graph type. There are 6 types:
  + *TORUS_2D*: The vertices of the graph are placed on a 2D mesh and
    each vertex is connected to its 4 neighbors in the mesh.
  + *Torus 2D60*: It is a random graph obtained from the previous one
    where each edge has a 60% probability to be present.
  + *Torus 3D*: The vertices of the graph are placed on a 3D mesh and
    each vertex is connected to its 6 neighbors in the mesh.
  + *Torus 3D40*: It is a random graph obtained from the previous one
    where each edge has a 40% probability to be present.
  + *Random*: Random graph of n vertices and m edges by randomly adding
  m unique edges to the vertex set.
  + *K-Graph*: K-regular graph, with K = 3.
- **vertexSize**: Number of vertices for side of the mesh for the case
  of the Torus. In the case for the **RANDOM** and **K-Graph**, is the
  total of vertices.
- **stepSpanningType**: Type of iteration for the spanning tree
  algorithm. **COUNTER** and **DOUBLE_COLLECT** style.
- **iterations**: The total of repetitions of execution for the
  spanning tree algorithm.
- **algorithms**: List of Work Stealing algorithms separated by
  semicolons. The options are:
  + *CILK*
  + *CHASELEV*
  + *IDEMPOTENT_FIFO*
  + *IDEMPOTENT_LIFO*
  + *IDEMPOTENT_DEQUE*
  + *NBWSMULT_FIFO*
  + *B_NBWSMULT_FIFO*
  + *WS_NC_MULT*
  + *B_WS_NC_MULT*
- **directed**: If the graph is directed or not.

## Support

Still in progress
