package datastructures;

public class Queue<T> {
    //Fields
    private Node<T> front;
    private Node<T> rear;
    
    public void enqueue(T item) {
        Node<T> queueNode = new Node<>(item); //The use of a node results in O(1)

        if (front == null) {
            front = queueNode;
            rear = queueNode;
        } else {
            rear.next = queueNode;
            rear = queueNode;
        }
    }
}