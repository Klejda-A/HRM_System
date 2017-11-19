/**
 * Created by Klejda Alushi on 20-Jan-17.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * The User Interface (UI) for the Human Resource Management Application.
 *
 * This UI displays an option menu and allows users to work with the system.
 */
public class HRMMenu {
    public static void main(String[] args) {
        HRMSystem admin = new HRMSystem();

        boolean quit = false;
        while (!quit) {
            System.out.println("");
            System.out.println("-----------");
            System.out.println("HRM System");
            System.out.println("-----------");
            System.out.println("1.  Add a manager");
            System.out.println("2.  Add an employee");
            System.out.println("3.  Add competences to employee");
            System.out.println("4.  Assign manager for employee");
            System.out.println("5.  Print number of employees");
            System.out.println("6.  Print number of managers");
            System.out.println("7.  Print employees managed by manager");
            System.out.println("8.  Display details of employees");
            System.out.println("9.  Write competence report to file");
            System.out.println("10. Read file");
            System.out.println("11. Exit");
            System.out.println("");

            // Ask for menu option
            System.out.print("Option: ");
            Scanner input = new Scanner(System.in);
            int option = askNumber();

            System.out.println("-----------");

            // Process option
            if (option == 1) {					// Add manager
                System.out.print("Enter the name of the manager: ");
                String name = askString();

                System.out.print("Enter the salary of the manager: ");
                int salary = askNumber();

                try {
                    int number = admin.addManager(name, salary);
                    System.out.println("The manager has been added with number " + number);
                } catch (IllegalArgumentException iae) {
                    System.out.println("ERROR: The manager has not been added. " + iae.getMessage());
                }

            } else if (option == 2) {			// Add employee
                System.out.print("Enter the name of the employee: ");
                String name = askString();

                System.out.print("Enter the salary of the employee: ");
                int salary = askNumber();

                try {
                    int number = admin.addEmployee(name, salary);
                    System.out.println("The employee has been added with number " + number);
                } catch (IllegalArgumentException iae) {
                    System.out.println("ERROR: The employee has not been added. " + iae.getMessage());
                }

            } else if (option == 3) {			// Add a competence
                System.out.print("Enter the name of the employee: ");
                String employeeName = askString();

                System.out.print("Enter the name of the competence: ");
                String competence = askString();

                System.out.print("Enter the level of the competence (0, 1 or 2): ");
                int competentenceLevel = askNumber();

                try {
                    admin.addCompetence(employeeName, competence, competentenceLevel);
                    System.out.println("The competence has been added");
                } catch (IllegalArgumentException iae) {
                    System.out.println("ERROR: The competences has not been added. " + iae.getMessage());
                }

            } else if (option == 4) {			// Assign manager to employee
                System.out.print("Enter the name of the manager: ");
                String managerName = askString();

                System.out.print("Enter the name of the employee: ");
                String employeeName = askString();

                try {
                    admin.assignManager(managerName, employeeName);
                    System.out.println("The manager has been added");
                } catch (IllegalArgumentException iae) {
                    System.out.println("ERROR: The manager has not been added." + iae.getMessage());
                }

            } else if (option == 5) {			// Number of employees
                int amount = admin.numberOfEmployees();
                System.out.println("Number of employees: " + amount);

            } else if (option == 6) {			// Number of managers
                int amount = admin.numberOfManagers();
                System.out.println("Number of managers: " + amount);

            } else if (option == 7) {			// Number of employees per manager
                System.out.print("Enter the name of the manager: ");
                String managerName = askString();

                try {
                    int amount = admin.numberOfEmployeesManagedByManager(managerName);
                    System.out.println("Number of employees managed by this manager: " + amount);
                } catch (IllegalArgumentException iae) {
                    System.out.println("ERROR: " + iae.getMessage());
                }

            } else if (option == 8) {			// Show details of employees
                System.out.println(admin.toString());

            } else if (option == 9) {			// Write competence report
                System.out.print("Enter file name: ");
                String fileName = askString();

                try {
                    admin.writeReportToFile(fileName);
                    System.out.println("The report has been written successfully");
                } catch (FileNotFoundException fnfe) {
                    System.out.println("ERROR: Could not write report");
                }

            } else if (option == 10) {			// Read file
                System.out.print("Enter file name: ");
                String fileName = askString();

                int amount = admin.readEmployeesFromFile(fileName);
                if (amount == -1) {
                    System.out.println("ERROR: The file could not be read");
                } else {
                    System.out.println("Done processing - " + amount + " persons have been added");
                }

            } else if (option == 11) {			// Exit
                quit = true;
            }
        }
    }

    /**
     * Ask the user to enter a number. If the user does not enter a number, the user is
     * requested to enter a number again, until a number has been entered.
     * @return the number the user entered
     */
    private static int askNumber() {
        int numberEntered = -1;
        boolean keepAsking = true;
        while (keepAsking) {
            Scanner input = new Scanner(System.in);
            try {
                numberEntered = Integer.parseInt(input.nextLine());
                keepAsking = false;
            } catch (NumberFormatException e) {
                System.out.print("ERROR: Enter a number: ");
                keepAsking = true;
            }
        }
        return numberEntered;
    }

    /**
     * Ask the user to enter text. If the user enters a blank line, the user is requested
     * to enter text again, until at least one character has been entered.
     * @return the text the user entered
     */
    public static String askString() {
        String stringEntered = "";
        boolean keepAsking = true;
        while (keepAsking) {
            Scanner input = new Scanner(System.in);
            stringEntered = input.nextLine();
            if (stringEntered.length() != 0) {
                keepAsking = false;
            } else {
                System.out.print("ERROR: Please enter at least one character: ");
            }
        }
        return stringEntered;
    }
}
