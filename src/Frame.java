import java.util.ArrayList;
import java.util.Collections;

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
        ArrayList<ArrayList<Formula>> previousLabelLists = getCopyWorldLabelLists();
        Checker.label(this, formula, new ArrayList<World>());
        ArrayList<World> blacklist = getModellingWorlds(formula);
        setCopyWorldLabelLists(previousLabelLists);
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
    public ArrayList<ArrayList<Formula>> getCopyWorldLabelLists() {
        ArrayList<ArrayList<Formula>> previousLabelLists = new ArrayList<ArrayList<Formula>>();
        for (World world : worlds) {
            ArrayList<Formula> label = world.getLabels();
            ArrayList<Formula> copyLabel = new ArrayList<Formula>();
            copyLabel.addAll(label);
            previousLabelLists.add(copyLabel);
        }
        return previousLabelLists;
    }
    public void setCopyWorldLabelLists(ArrayList<ArrayList<Formula>> previousLabelLists) {
        for (int i = 0; i < worlds.size(); i++) {
            worlds.get(i).setLabelList(previousLabelLists.get(i));
        }
    }
}
