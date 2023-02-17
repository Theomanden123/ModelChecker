import java.util.ArrayList;

public class Frame {

    private ArrayList<World> worlds;

    public Frame() {
        worlds = new ArrayList<World>();
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }
    
}
