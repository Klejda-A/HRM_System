/**
 * Created by Klejda Alushi on 20-Jan-17.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To keep track of the employees within a company, we are going to develop a HRM application.
 * The system records the name, salary and a unique number for each employee. Additionally,
 * the system keeps track of the competences of the employees. An employee can master a
 * competence on three different levels: 0 means that the person is very bad at it, 1 means
 * that the person is reasonably well at it, and 2 means that the person is very good at it.
 * An example competence could be ‘Programming’. Within the company, there are ‘regular’
 * employees and managers. A manager is also an employee, but (s)he manages one or more
 * employees.
 */
public class HRMSystem {
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    /**
     * Add a manager to the system. The name must be unique. If not, an
     * IllegalArgumentException is thrown.
     * @param name the name of the manager
     * @param salary the salary of the manager
     * @return the (automatically generated) unique number assigned to this manager
     * @throws IllegalArgumentException if an employee with the given name already exists
     */
    public int addManager(String name, int salary) throws IllegalArgumentException {
        if (!employeeExists(name)) {
            int ID = employees.size() + 1;
            employees.add(new Manager(name, salary, ID));
            return ID;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Assigns the manager with the name managerName to be the manager of the employee
     * with name employeeName.
     *
     * @param managerName name of the manager
     * @param employeeName name of the employee
     * @throws IllegalArgumentException if a name does not exist in the system or if managerName is
     * not a manager
     */
    public void assignManager(String managerName, String employeeName) throws IllegalArgumentException {
        if (!employeeExists(employeeName)) {
            throw new IllegalArgumentException();
        }
        else {
            Employee employee = getEmployee(employeeName);
            if (getEmployee(managerName) instanceof Manager) {
                ((Manager) getEmployee(managerName)).getEmployeesOfManager().add(employee);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Add an employee to the system.
     * The name must be unique.
     *
     * @param name the name of the employee
     * @param salary the salary of the employee
     * @return the (automatically generated) unique number assigned to this employee
     * @throws IllegalArgumentException if an employee with the given name already exists
     */
    public int addEmployee(String name, int salary) throws IllegalArgumentException {
        if (!employeeExists(name)) {
            int ID = employees.size() + 1;
            employees.add(new Employee(name, salary, ID));
            return ID;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Helper function to check if an employee exists in the system.
     *
     * @param name the name to check
     * @return true if the employee exists, false otherwise
     */
    private boolean employeeExists(String name) {
        for (Employee employee: employees) {
            if (employee.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function to retrieve an Employee object based on the name
     * of an employee.
     *
     * @param name the name of the employee
     * @return the Employee object, or null if there is no employee with the given name.
     */
    private Employee getEmployee(String name) {
        for (Employee employee: employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Retrieve the total number of employees (including managers).
     *
     * @return the total number of employees
     */
    public int numberOfEmployees() {
        return employees.size();
    }

    /**
     * Retrieve the total number of managers
     *
     * @return the total number of managers
     * @see HRMSystem#numberOfEmployees()
     */
    public int numberOfManagers() {
        int numberOfManagers = 0;
        for (Employee employee: employees) {
            if (employee instanceof Manager) {
                numberOfManagers ++;
            }
        }
        return numberOfManagers;
    }

    /**
     * Retrieve the number of employees that are managed by a certain manager.
     *
     * @param 	managerName the name of the manager
     * @return 	the number of employees managed by this manager
     * @throws 	IllegalArgumentException if there is no employee with the name managerName
     * or the employee is not a manager
     */
    public int numberOfEmployeesManagedByManager(String managerName) throws IllegalArgumentException {
        if(!employeeExists(managerName)) {
            throw new IllegalArgumentException();
        }
        else {
            Employee employee = getEmployee(managerName);
            if (employee instanceof Manager) {
                return ((Manager) employee).getEmployeesOfManager().size();
            }
            else {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Read employees from a text file. Lines in the file need to meet several constraints:
     * Fields are separated using commas. Lines starting with a hash symbol (#) and empty
     * lines need to be skipped. Valid lines consist of a name and a salary. If someone is
     * a manager, the line continues with the word manager, followed by the names of the
     * employees the person manages.
     *
     * @param filename name of the file that needs to be read
     * @return the number of persons that have been read (and added to the system) successfully,
     * or -1 if the file could not be read.
     */
    public int readEmployeesFromFile(String filename) {
        int linesRead = 0;
        try {
            Scanner scanner = new Scanner(new File(filename + ".txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#") && !line.trim().equals("")) {
                    try {
                        employees.add(processLine((line)));
                        linesRead++;
                    }catch (LineException lex) {
                        System.out.println(lex.getMessage());
                    }
                }
            }
            return linesRead;
        }
        catch (FileNotFoundException fex) {
            System.out.println("File not found");
        }
        return -1;
    }

    /**
     * Helper method to read a line from the file.
     *
     * @param line the contents of the line that was read from the file
     * @return the employee that was created based on the line
     * @throws LineException if the line does not meet the constraints
     * @see HRMSystem#readEmployeesFromFile(String)
     */
    private Employee processLine(String line) throws LineException {
        String[] split = line.split(",");
        if (split.length < 2) {
            throw new LineException("Salary is missing");
        }
        int salary;
        try {
            salary = Integer.parseInt(split[1]);
        }
        catch (Exception e){
            throw new LineException("Salary must be a number");
        }
        if (split.length == 2) {
            return new Employee(split[0], salary);
        }
        else {
            if (split[2].equals("manager")) {
                Manager manager = new Manager(split[0], salary);
                for (int i = 3; i < split.length; i++) {
                    if (employeeExists(split[i])) {
                        manager.getEmployeesOfManager().add(getEmployee(split[i]));
                    }
                    else {
                        throw new LineException();
                    }
                }
                return manager;
            }
            else {
                throw new LineException();
            }
        }
    }

    /**
     * Adds a competence to an employee with the given name. If the employee does not exist,
     * an IllegalArgumentException is thrown. If the competence level is less than 0, the value 0
     * is used. If the competence level is higher than 2, the value 2 is used.
     *
     * @param employeeName name of the employee
     * @param competenceName name of the competence
     * @param competenceLevel competence level (0, 1, or 2)
     * @throws IllegalArgumentException if there is no such employee
     */
    public void addCompetence(String employeeName, String competenceName, int competenceLevel) throws IllegalArgumentException {
        if (!employeeExists(employeeName)) {
            throw new IllegalArgumentException();
        }
        else {
            Employee employee = getEmployee(employeeName);
            employee.getCompetences().add(new Competence(competenceName, competenceLevel));
        }
    }

    /**
     * Writes a report that shows all employees and their competences. The file must be structured as follows
     * <pre>
     * {@code
     * ### HRM System Summary ###
     * Evert (1) has competences: Programming (2), Cooperating (0)
     * Peter (2) has competences: Research (2), Programming (2)
     * Ruud (3) has no competences
     * }
     * </pre>
     * @param fileName the name of the file to write to
     * @throws FileNotFoundException if the output file could not be created
     */
    public void writeReportToFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.println("### HRM System Summary ###");
        for (Employee employee: employees) {
            if (employee.getCompetences().size() == 0) {
                printWriter.println(employee.name + " (" + employee.IDnumber + ") has no competences");
            }
            else {
                printWriter.println(employee.name + " (" + employee.IDnumber + ") has competences: " + employee.competences);
            }
        }
        printWriter.close();
    }

    public String toString() {
        return "Summary (" + employees.size() + " employee(s)): " +
                employees;
    }

}
