import java.util.ArrayList;

/**
 * Created by Klejda Alushi on 20-Jan-17.
 */
public class Manager extends Employee{
    ArrayList<Employee> employeesOfManager = new ArrayList<Employee>();

    public Manager(String name, int salary, int IDnumber) {
        super(name, salary, IDnumber);
    }

    public ArrayList<Employee> getEmployeesOfManager() {
        return employeesOfManager;
    }

    public Manager(String name, int salary) {
        super(name, salary);
    }

    public String toString() {
        return '\n' + name + " (" + IDnumber + ") with salary " + salary + " manages " + employeesOfManager.size() + " employees";
    }
}
