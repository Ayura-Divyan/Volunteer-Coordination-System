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
}
