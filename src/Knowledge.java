import java.util.ArrayList;

public class Knowledge extends UnaryOperator {

    ArrayList<Agent> agents;

    public Knowledge(Agent agent, Formula formula) {
        super(formula);
        agents = new ArrayList<Agent>();
        agents.add(agent);
        operator = "K";
    }

    public Knowledge(ArrayList<Agent> agents, Formula formula) {
        super(formula);
        this.agents = agents;
        operator = "K";
    }


    
}
