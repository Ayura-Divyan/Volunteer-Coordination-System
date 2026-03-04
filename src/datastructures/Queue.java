package datastructures;

public class Queue<T> {
    //Fields
    private Node<T> head;
    private Node<T> tail;

    public void enqueue(T item) {
        Node<T> queueNode = new Node<>(item); //The loop results in O(1)

        if (head == null) {
            head = tail = queueNode;
            return;
        }
    }
}
