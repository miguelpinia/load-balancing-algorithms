package phd.ws.imp;

import phd.ws.WorkStealingStruct;

/**
 *
 * @author miguel
 */
public class SIMPLEWorkStealing implements WorkStealingStruct {

    private Node top;

    public SIMPLEWorkStealing() {
        top = null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void put(int task) {
        Node newNode = new Node(task, top);
        top = newNode;
    }

    @Override
    public int take() {
        if (top == null) {
            return -1;
        }
        Node tmp = top;
        int value = tmp.getVal();
        top = tmp.getNext();
        return value;
    }

    @Override
    public int steal() {
        Node tmp = top;
        if (tmp == null) {
            return -1;
        }
        return tmp.getVal();
    }

    @Override
    public boolean put(int task, int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int take(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int steal(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty(int label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPuts() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getTakes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getSteals() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
