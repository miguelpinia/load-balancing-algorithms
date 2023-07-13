package phd.utils;

/**
 *
 * @author miguel
 */
public class AnchorDeque {

    private int head;
    private int size;

    public AnchorDeque(int head, int size) {
        this.head = head;
        this.size = size;
    }

    public int getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public void set(int head, int size) {
        this.head = head;
        this.size = size;
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
        final AnchorDeque other = (AnchorDeque) obj;
        if (this.head != other.head) {
            return false;
        }
        return this.size == other.size;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.head;
        hash = 47 * hash + this.size;
        return hash;
    }

}
