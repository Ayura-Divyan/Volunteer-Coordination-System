package datastructures;

public class LinkedList<T> {
    Node<T> head;

    //The single loop results in O(n)
    public void add(T data) {
        Node<T> newNode = new Node<T>(data);

        if (head == null) {
            //If head is empty it is a new node
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
}
