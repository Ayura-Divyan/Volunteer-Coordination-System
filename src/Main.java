import datastructures.Stack;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Initialise the input scanner and the coordinationSystem
        Scanner input = new Scanner(System.in);
        VolunteerCoordination coordinationSystem = new VolunteerCoordination();
        boolean running = true;

        while (running) {
            //Main Menu
            System.out.println("\n-------Main Menu-------\n");
            System.out.println("Pick an option (Enter the option number)");
            System.out.println("----------------------------------------");
            System.out.println("1. Register a volunteer");
            System.out.println("2. Remove a volunteer");
            System.out.println("3. Search for a volunteer");
            System.out.println("4. Assign task to a volunteer");
            System.out.println("5. Undo last action");
            System.out.println("6. Display all volunteers");
            System.out.println("7. Display the last action");
            System.out.println("8. Display the next person to be assigned a task");
            System.out.println("9. Exit program");
            System.out.println("----------------------------------------");
            System.out.println("Enter your choice (1-7): ");
            String choice = input.nextLine();
            System.out.println();

            //Process the user choice
            switch (choice) {
                case "1": //Volunteer registration
                    String id;
                    while (true) { //Checks if the id that was entered already exists in the list
                        System.out.println("Enter the volunteer ID: ");
                        id = input.nextLine();

                        if (coordinationSystem.getVolunteer(id) == null) {
                            break;
                        } else {
                            System.out.println("Error: This ID already exists in the database");
                        }
                    }
                    System.out.println("Enter the volunteer name: ");
                    String name = input.nextLine();
                    String priority = "";
                    while (true) {
                        System.out.println("Enter the volunteer priority (HIGH/MEDIUM/LOW): ");
                        priority = input.nextLine();

                        if (priority.equalsIgnoreCase("HIGH") ||
                                priority.equalsIgnoreCase("MEDIUM") ||
                                priority.equalsIgnoreCase("LOW")) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please type exactly High, Medium, or Low.");
                        }
                    }
                    coordinationSystem.registerVolunteer(id, name, priority);
                    System.out.println("Volunteer was successfully registered ");
                    break;
                case "2": //Volunteer removal
                    System.out.println("Enter volunteer ID to be removed: ");
                    String removeId = input.nextLine();
                    if (coordinationSystem.removeVolunteer(removeId)) {
                        System.out.println("Volunteer removed");
                    }
                    break;
                case "3": //Searching for volunteers
                    System.out.println("Enter the name or ID of volunteer: ");
                    String searchTerm = input.nextLine();
                    Volunteer foundVolunteer = coordinationSystem.getVolunteer(searchTerm);
                    if (foundVolunteer != null) {
                        System.out.println("ID: " + foundVolunteer.getVolunteerId());
                        System.out.println("Name: " + foundVolunteer.getName());
                        System.out.println("Task Assigned: " + foundVolunteer.getTaskAssigned());
                        System.out.println("Priority: " + foundVolunteer.getPriorityLevel());
                        System.out.println("Status: " + (foundVolunteer.isAssigned() ? "Yes" : "No"));
                    } else {
                        System.out.println("Error: No volunteer found with ID '" + searchTerm + "'.");
                    }
                    break;
                case "4": //Task assignment
                    System.out.println("Enter the task to assign: ");
                    String task = input.nextLine();
                    coordinationSystem.assignTask(task);
                    break;
                case "5": //Undo actions
                    coordinationSystem.undoLastAction();
                    break;
                case "6": //Print all Volunteers
                    coordinationSystem.getAllVolunteers();
                    break;
                case "7":
                    coordinationSystem.displayLastAction();
                case "8":
                    coordinationSystem.displayNextVolunteer();
                case "9": //Exit program
                    System.out.println("Exiting the program...");
                    running = false;
                    break;
                default: //Invalid input check
                    System.out.println("Invalid input. Please try again");

            }
            if (running) { //Makes sure main menu doesn't load right after you're finished with a sub menu
                System.out.println("\nPress enter to return to main menu...");
                input.nextLine();
            }
        }
        input.close();
    }
}
