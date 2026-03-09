import datastructures.*;

public class VolunteerCoordination {
    private LinkedList<Volunteer> allVolunteers;
    private Queue<Volunteer> highPriorityQueue;
    private Queue<Volunteer> mediumPriorityQueue;
    private Queue<Volunteer> lowPriorityQueue;
    private Stack<ActionRecord> volunteerHistory;

    //Constructor
    public VolunteerCoordination() {
        this.allVolunteers = new LinkedList<>();
        this.highPriorityQueue = new Queue<>();
        this.mediumPriorityQueue = new Queue<>();
        this.lowPriorityQueue = new Queue<>();
        this.volunteerHistory = new Stack<>();
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
    public Volunteer getVolunteer(String searchTerm) {
        Node<Volunteer> current =  allVolunteers.getHead();

        //Linear search is used resulting in O(n)
        while (current != null) {
            Volunteer v = current.getData();

            if (v.getVolunteerId().equalsIgnoreCase(searchTerm) || v.getName().equalsIgnoreCase(searchTerm)) {
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
        volunteerHistory.push(new ActionRecord("REMOVE", volunteer));
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
                volunteerHistory.push(new ActionRecord("ASSIGN", frontPerson));
               targetQueue.dequeue();
                return true;
            }
        }
        return false;
    }

    //Undo Method
    public void undoLastAction() {
        if (volunteerHistory.isEmpty()) {
            System.out.println("Error: No more volunteers to undo");
            return;
        }

        ActionRecord lastAction = volunteerHistory.pop();
        Volunteer targetVolunteer = lastAction.getVolunteer();
        String type = lastAction.getActionType();

        if (type.equals("ASSIGN")) {
            targetVolunteer.setAssigned(false);
            targetVolunteer.setTaskAssigned("Unassigned");

            switch(targetVolunteer.getPriorityLevel().toLowerCase()) {
                case "high":
                    highPriorityQueue.enqueue(targetVolunteer);
                    break;
                case "medium":
                    mediumPriorityQueue.enqueue(targetVolunteer);
                    break;
                case "low":
                    lowPriorityQueue.enqueue(targetVolunteer);
                    break;
            }
            System.out.println("Undid assignment for: " + targetVolunteer.getName());
        } else if (type.equals("REMOVE")) {
            targetVolunteer.setAssigned(false);
            allVolunteers.add(targetVolunteer);

            switch(targetVolunteer.getPriorityLevel().toLowerCase()) {
                case "high":
                    highPriorityQueue.enqueue(targetVolunteer);
                    break;
                case "medium":
                    mediumPriorityQueue.enqueue(targetVolunteer);
                    break;
                case "low":
                    lowPriorityQueue.enqueue(targetVolunteer);
                    break;
            }
            System.out.println("Undid removal for: " + targetVolunteer.getName());
        }
    }

    //Print all volunteers method
    public void getAllVolunteers() {
        Node<Volunteer> current = allVolunteers.getHead();

        if (current == null) {
            System.out.println("No volunteers currently registered in the system");
            return;
        }

        System.out.println("\n-----Master Volunteer List-----\n");
        System.out.printf("%-5s | %-15s | %-8s | %-15s | %-10s%n", "ID", "Name", "Priority", "Task", "Status");
        System.out.println("-------------------------------------------");

        while (current != null) {
            Volunteer v = current.getData();

            String status = v.isAssigned() ? "Assigned" : "Available";

            System.out.printf("%-5s | %-15s | %-8s | %-15s | %-10s%n0",
                    v.getVolunteerId(), v.getName(), v.getPriorityLevel(), v.getTaskAssigned(), status);

            current = current.getNext();
        }
        System.out.println("-------------------------------------------");
    }
}
