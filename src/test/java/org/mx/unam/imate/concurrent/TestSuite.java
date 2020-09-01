package org.mx.unam.imate.concurrent;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 *
 * @author miguel
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({
    "org.mx.unam.imate.concurrent.algorithms.experiments",
    "org.mx.unam.imate.concurrent.algorithms.experiments.spanningTree",
    "org.mx.unam.imate.concurrent.algorithms.output",
    "org.mx.unam.imate.concurrent.datastructures.graph"
})
public class TestSuite {

}
