import java.util.ArrayList;

public class World {

    private String name;

    private ArrayList<Formula> interpretation;
    private ArrayList<Relation> relations;

    public World(String name) {
        interpretation = new ArrayList<Formula>();
        relations = new ArrayList<Relation>();
        this.name = name;
    }

    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public void addTruth(Formula formula) {
        interpretation.add(formula);
    }

}
