package datastructures;

public class Queue<T> {
    //Fields
    private Node<T> front;
    private Node<T> rear;

    //Method to enqueue the volunteers the queue
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

    //Method to dequeue an item at the front
    public void dequeue() {
        if (isEmpty()) {
            System.out.println("The queue is empty");
            return;
        }

        T data = front.data;
        front = front.next;

        //This will make sure that if the queue is null it will reset the rear as well
        if (front == null) {
            rear = null;
        }
    }

    //Method to check if the queue is empty
    public Boolean isEmpty() {
        return front == null;
    }

    //Peek method
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return front.getData();
    }
}