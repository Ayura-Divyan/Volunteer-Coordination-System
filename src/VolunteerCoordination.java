import datastructures.*;

public class VolunteerCoordination {
    private LinkedList<Volunteer> allVolunteers;
    private Queue<Volunteer> highPriorityQueue;
    private Queue<Volunteer> mediumPriorityQueue;
    private Queue<Volunteer> lowPriorityQueue;

    //Constructor
    public VolunteerCoordination() {
        this.allVolunteers = new LinkedList<>();
        this.highPriorityQueue = new Queue<>();
        this.mediumPriorityQueue = new Queue<>();
        this.lowPriorityQueue = new Queue<>();
    }

    //Adding volunteers
    public void registerVolunteer(String id, String name, String priority) {
        Volunteer newVolunteer = new Volunteer(id, name, priority);

        allVolunteers.add(newVolunteer);

        switch(priority.toLowerCase()) {
            case "high":
                highPriorityQueue.enqueue(newVolunteer);
                break;
            case "medium":
                mediumPriorityQueue.enqueue(newVolunteer);
                break;
            case "low":
                lowPriorityQueue.enqueue(newVolunteer);
        }
    }

    //Searching volunteers
    public Volunteer getVolunteer(String id) {
        Node<Volunteer> current =  allVolunteers.getHead();

        //Linear search is used resulting in O(n)
        while (current != null) {
            if (current.getData().getVolunteerId().equals(id)) {
                return current.getData();
            }
            current = current.getNext();
        }
        return null;
    }
}
