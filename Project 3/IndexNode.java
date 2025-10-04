public class IndexNode<T> {
    protected T data;
    protected int next;
    protected static final int NUL = -1; // Value used to represent the next index of the last list element

    // Constructor
    public IndexNode(T data) {
        this.data = data;
        next = NUL;
    }

    // Getters
    public T getData() { return data; }

    public int getNext() { return next; }

    // Setters
    public void setData(T data) { this.data = data; }

    public void setNext(int next) { this.next = next; }
}
