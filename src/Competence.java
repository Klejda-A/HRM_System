/**
 * Created by Klejda Alushi on 20-Jan-17.
 */
public class Competence {
    private String name;
    private int value;

    public Competence(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name + "(" + value + ")";
    }
}
