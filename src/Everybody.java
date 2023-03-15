import java.util.ArrayList;

public class Everybody extends UnaryOperator {

    private ArrayList<Agent> group;

    public Everybody(ArrayList<Agent> group, Formula formula) {
        super(formula);
        this.group = group;
        operator = "E";
    }

    public ArrayList<Agent> getGroup() {
        return group;
    }

    public String toString() {
        return operator + group.toString() + "(" + formula + ")";
    }
}
