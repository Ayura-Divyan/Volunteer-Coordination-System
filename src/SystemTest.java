public class SystemTest {
    public static void main(String[] args) {
        // Initialize the system
        VolunteerCoordination system = new VolunteerCoordination();

        System.out.println("=========================================");
        System.out.println("   SMART EVENT SYSTEM - AUTOMATED TESTS  ");
        System.out.println("=========================================\n");

        // ---------------------------------------------------------
        // TEST 1: Priority-Based Assignment and Processing Order
        // ---------------------------------------------------------
        System.out.println("--- TEST 1: Priority-Based Assignment ---");

        // Register out of order to prove queues sort them correctly
        system.registerVolunteer("V1", "Alice", "LOW");
        system.registerVolunteer("V2", "Bob", "HIGH");
        system.registerVolunteer("V3", "Charlie", "MEDIUM");

        // Assign tasks
        system.assignTask("Registration Desk");
        system.assignTask("Crowd Control");

        // Verify priorities were respected
        System.out.println("Expected: Bob (HIGH) gets Registration Desk.");
        System.out.println("Actual: " + system.getVolunteer("V2").getTaskAssigned());

        System.out.println("Expected: Charlie (MEDIUM) gets Crowd Control.");
        System.out.println("Actual: " + system.getVolunteer("V3").getTaskAssigned());

        System.out.println("Expected: Alice (LOW) is Unassigned.");
        System.out.println("Actual: " + system.getVolunteer("V1").getTaskAssigned() + "\n");


        // ---------------------------------------------------------
        // TEST 2: Volunteer Removal and Lazy Deletion
        // ---------------------------------------------------------
        System.out.println("--- TEST 2: Volunteer Removal ---");

        // Register a new high-priority volunteer
        system.registerVolunteer("V4", "Diana", "HIGH");

        // Remove Diana before any tasks are assigned to her
        system.removeVolunteer("V4");

        // Try to assign a task (It should skip Diana and go to Alice)
        system.assignTask("Logistics");

        System.out.println("Expected: Diana (Removed) is Unassigned / Not Found.");
        // We check for null just in case your search method doesn't return removed volunteers
        Volunteer diana = system.getVolunteer("V4");
        System.out.println("Actual: " + (diana != null ? diana.getTaskAssigned() : "Removed from system"));

        System.out.println("Expected: Alice (LOW) gets Logistics (since Diana was removed).");
        System.out.println("Actual: " + system.getVolunteer("V1").getTaskAssigned() + "\n");


        // ---------------------------------------------------------
        // TEST 3: Undo Operations (Assignments and Removals)
        // ---------------------------------------------------------
        System.out.println("--- TEST 3: Undo Operations ---");

        // 1. Undo the last action (Which was assigning Alice to Logistics)
        System.out.println("Action: Undoing Alice's assignment...");
        system.undoLastAction();
        System.out.println("Expected: Alice task after undo is Unassigned");
        System.out.println("Actual: " + system.getVolunteer("V1").getTaskAssigned() + "\n");

        // 2. Undo the action before that (Which was removing Diana)
        System.out.println("Action: Undoing Diana's removal...");
        system.undoLastAction();

        // 3. Re-run an assignment to prove Diana is successfully back in the High-Priority Queue
        System.out.println("Action: Assigning new task (Logistics)...");
        system.assignTask("Logistics");

        // Since Diana is back and is HIGH priority, she should get it before Alice
        System.out.println("Expected: Diana gets Logistics.");
        diana = system.getVolunteer("V4");
        System.out.println("Actual: " + (diana != null ? diana.getTaskAssigned() : "Not Found"));

        System.out.println("=========================================");
        System.out.println("               TESTS COMPLETE            ");
        System.out.println("=========================================");
    }
}