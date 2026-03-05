package datastructures;

//Node for the custom data structures
//T is called a generic since the linked list takes multiple data types
public class Node<T> {
    T data;
    Node<T> next;

    //Constructor
    Node(T newData){
        this.data = newData;
        this.next = null;
    }

    //Getters
    public T getData() {return data;}

    public Node<T> getNext() {return next;}
}
