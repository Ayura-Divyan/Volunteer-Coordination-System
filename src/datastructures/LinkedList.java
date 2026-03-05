package datastructures;

public class LinkedList<T> {
    private Node<T> head;

    //Method to add data to linked list
    public void add(T data) {
        Node<T> linkedListNode = new Node<T>(data);

        if (head == null) {
            //If head is empty it is a new node
            head = linkedListNode;
        } else {
            Node<T> current = head;
            //The single loop results in O(n)
            while (current.next != null) {
                current = current.next;
            }
            current.next = linkedListNode;
        }
    }

    //Getter for head

    public Node<T> getHead() {
        return head;
    }
}
