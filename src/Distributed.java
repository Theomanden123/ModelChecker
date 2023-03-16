import java.util.ArrayList;

public class Distributed extends UnaryOperator {

    private ArrayList<Agent> group;

    public Distributed(ArrayList<Agent> group, Formula formula) {
        super(formula);
        this.group = group;
        operator = "D";
    }
    
    public ArrayList<Agent> getGroup() {
        return group;
    }

    public String toString() {
        return operator + group.toString() + "(" + formula + ")";
    }
}
