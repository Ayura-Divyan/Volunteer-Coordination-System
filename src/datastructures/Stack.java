package datastructures;

public class Stack<T> {
    //Field
    private Node<T> top;

    public void push(T item) {
        Node<T> stackNode = new Node<>(item); //The use of a node results in O(1)
        top = top.next;
        top.next = stackNode;
    }

    public T pop() {
        if (isEmpty()) {
            System.out.println("The stack is empty");
            return null;
        }
        T data = top.data;
        top = top.next;

        return data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
