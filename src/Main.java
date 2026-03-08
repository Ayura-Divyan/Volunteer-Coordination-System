import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Initialise the input scanner and the system
        Scanner input = new Scanner(System.in);
        VolunteerCoordination system = new VolunteerCoordination();
        boolean running = true;

        while (running) {
            System.out.println("\n-------Main Menu-------\n");
            System.out.println("Pick an option (Enter the option number)");
            System.out.println("---------------------------");
            System.out.println("1. Register a volunteer");
            System.out.println("2. Remove a volunteer");
            System.out.println("3. Search for a volunteer");
            System.out.println("4. Assign task to a volunteer");
            System.out.println("5. Exit program");

        }
    }
}
