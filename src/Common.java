import java.util.ArrayList;

public class Common extends UnaryOperator {

    private ArrayList<Agent> group;

    public Common(ArrayList<Agent> group, Formula formula) {
        super(formula);
        this.group = group;
        operator = "C";
    }

    public ArrayList<Agent> getGroup() {
        return group;
    }

    public String toString() {
        return operator + group.toString() + "(" + formula + ")";
    }
    
}
