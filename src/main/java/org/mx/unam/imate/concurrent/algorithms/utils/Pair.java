package org.mx.unam.imate.concurrent.algorithms.utils;

/**
 *
 * @author miguel
 */
public class Pair {

    private final int t;
    private final int g;

    public Pair(int t, int g) {
        this.t = t;
        this.g = g;
    }

    public int getT() {
        return t;
    }

    public int getG() {
        return g;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (this.t != other.t) {
            return false;
        }
        return this.g == other.g;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.t;
        hash = 97 * hash + this.g;
        return hash;
    }

}
