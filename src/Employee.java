import java.util.ArrayList;

/**
 * Created by Klejda Alushi on 20-Jan-17.
 */
public class Employee {
    protected String name;
    protected int salary;
    protected int IDnumber;
    ArrayList<Competence> competences = new ArrayList<Competence>();

    public String getName() {
        return name;
    }

    public ArrayList<Competence> getCompetences() {
        return competences;
    }

    public Employee(String name, int salary, int IDnumber) {
        this.name = name;
        this.salary = salary;
        this.IDnumber = IDnumber;
    }

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return '\n' +  name + " (" + IDnumber + ") with salary " + salary;
    }
}
