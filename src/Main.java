import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Initialise the input scanner and the system
        Scanner input = new Scanner(System.in);
        VolunteerCoordination system = new VolunteerCoordination();
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
            System.out.println("6. Exit program");
            System.out.println("----------------------------------------");
            System.out.println("Enter your choice (1-6): ");
            int choice = input.nextInt();

            //Process the user choice
            switch (choice) {
                case 1:
                    System.out.println("Enter the volunteer ID: ");
                    String id = input.nextLine();
                    System.out.println("Enter the volunteer name: ");
                    String name = input.nextLine();
                    System.out.println("Enter the volunteer priority (HIGH/MEDIUM/LOW): ");
                    String priority = input.nextLine();

                    system.registerVolunteer(id, name, priority);
                    System.out.println("Volunteer was successfully registered ");
                    break;
                case 2:
                    System.out.println("Enter volunteer ID to be removed: ");
                    String removeId = input.nextLine();
                    if (system.removeVolunteer(removeId)) {
                        System.out.println("Volunteer removed");
                    }
                    break;
                case 3:
                    System.out.println("Enter the name or ID of volunteer");

            }
        }
    }
}
