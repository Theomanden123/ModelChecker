import java.util.ArrayList;

public class Relation {
    
    private World src;
    private World dest;
    private ArrayList<Agent> agents;

    public Relation(World src, World dest) {
        this.src = src;
        this.dest = dest;
        agents = new ArrayList<Agent>();
        src.addOutgoingRelation(this);
        dest.addIngoingRelation(this);
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public boolean contains(Agent agent) {
        return agents.contains(agent);
    }

    public boolean containsAll(ArrayList<Agent> agents) {
        return this.agents.containsAll(agents);
    }

    public World getSrc() {
        return src;
    }

    public World getDest() {
        return dest;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

}