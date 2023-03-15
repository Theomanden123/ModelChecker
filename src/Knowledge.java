public class Knowledge extends UnaryOperator {

    private Agent agent;

    public Knowledge(Agent agent, Formula formula) {
        super(formula);
        this.agent = agent;
        operator = "K";
    }

    public Agent getAgent() {
        return agent;
    }

    public String toString() {
        return operator + agent.getName() + "(" + formula + ")";
    }
    
}
