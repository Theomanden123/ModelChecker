import java.util.ArrayList;

public class Frame {

    private ArrayList<Agent> agents;
    private ArrayList<World> worlds;
    private ArrayList<World> blacklist;

    public Frame() {
        agents = new ArrayList<Agent>();
        worlds = new ArrayList<World>();
        blacklist = new ArrayList<World>();
    }

    public Frame(ArrayList<World> worlds) {
        agents = new ArrayList<Agent>();
        this.worlds = worlds;
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public ArrayList<World> getBlacklist(Formula formula) {
        Checker.label(this, formula, new ArrayList<World>());
        ArrayList<World> blacklist = getModellingWorlds(formula);
        flushLabels();
        return blacklist;
    }

    public World getWorldFromName(String name) {
        for (World world : worlds) {
            if (world.toString().equals(name)) {
                return world;
            }
        }
        return null;
    }

    public ArrayList<World> getModellingWorlds(Formula formula) {
        ArrayList<World> list = new ArrayList<World>();
        for (World world : worlds) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(formula)) {
                list.add(world);
            }
        }
        return list;
    }  
    
    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public Agent getAgent(String name) {
        for (Agent agent : agents) {
            if (agent.getName().equals(name)) {
                return agent;
            }
        }
        return null;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void flushLabels() {
        for (World world : worlds) {
            world.getLabels().clear();
        }
    }

}
