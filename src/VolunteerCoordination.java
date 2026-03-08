import datastructures.*;

public class VolunteerCoordination {
    private LinkedList<Volunteer> allVolunteers;
    private Queue<Volunteer> highPriorityQueue;
    private Queue<Volunteer> mediumPriorityQueue;
    private Queue<Volunteer> lowPriorityQueue;
    private Stack<Volunteer> assignmentHistory;

    //Constructor
    public VolunteerCoordination() {
        this.allVolunteers = new LinkedList<>();
        this.highPriorityQueue = new Queue<>();
        this.mediumPriorityQueue = new Queue<>();
        this.lowPriorityQueue = new Queue<>();
        this.assignmentHistory = new Stack<>();
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

    //Removing volunteers
    public boolean removeVolunteer(String id) {
        Volunteer volunteer = getVolunteer(id);

        if (volunteer == null) {
            System.out.println("Error: Volunteer with ID" + id + " not found");
            return false;
        }

        volunteer.setAssigned(true);

        allVolunteers.remove(volunteer);
        return true;
    }

    //Assigning tasks
    public void assignTask (String task) {
        if (processQueue(task, highPriorityQueue)) return;

        if (processQueue(task, mediumPriorityQueue)) return;

        if (processQueue(task, lowPriorityQueue)) return;
    }

    private boolean processQueue(String task, Queue<Volunteer> targetQueue) {
        while (!targetQueue.isEmpty()) {
            Volunteer frontPerson = targetQueue.peek();

            if (frontPerson.isAssigned()){
                targetQueue.dequeue();
            } else {
                frontPerson.setAssigned(true);
                frontPerson.setTaskAssigned(task);
                assignmentHistory.push(frontPerson);
               targetQueue.dequeue();
                return true;
            }
        }
        return false;
    }
}
