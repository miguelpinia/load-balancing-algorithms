package org.mx.unam.imate.concurrent.datastructures;

/**
 *
 * @author miguel
 */
public class Queue {

    private Node head;
    private Node tail;

    public Queue() {
        head = null;
        tail = null;
    }

    public void enqueue(int task) {
        Node newNode = new Node(task, null);
        if (tail == null) {
            head = new Node(task, null);
            tail = head;
            return;
        }
        tail.setNext(newNode);
        tail = newNode;
    }

    public int dequeue() {
        if (head == null) {
            return -1;
        }
        Node tmp = head;
        head = head.getNext();
        int val = tmp.getVal();
        if (head == null) {
            tail = null;
        }
        return val;
    }

}
