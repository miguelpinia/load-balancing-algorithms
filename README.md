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

- OpenJDK 1.8 (1.8.0_252)
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
## Support
