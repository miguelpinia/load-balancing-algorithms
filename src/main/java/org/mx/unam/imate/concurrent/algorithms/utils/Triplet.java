package org.mx.unam.imate.concurrent.algorithms.utils;

/**
 *
 * @author miguel
 */
public class Triplet {

    private int head;
    private int size;
    private int tag;

    public Triplet(int head, int size, int tag) {
        this.head = head;
        this.size = size;
        this.tag = tag;
    }

    public int getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public int getTag() {
        return tag;
    }

    public void set(int head, int size, int tag) {
        this.head = head;
        this.size = size;
        this.tag = tag;
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
        final Triplet other = (Triplet) obj;
        if (this.head != other.head) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return this.tag == other.tag;
    }

}
