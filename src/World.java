import java.util.ArrayList;

public class World {

    private String name;

    private ArrayList<Formula> interpretation;
    private ArrayList<Relation> relations;
    private ArrayList<Formula> labels;

    public World(String name) {
        interpretation = new ArrayList<Formula>();
        relations = new ArrayList<Relation>();
        labels = new ArrayList<Formula>();
        this.name = name;
    }

    public void addIntepretation(Formula formula) {
        interpretation.add(formula);
    }


    public void addRelation(Relation relation) {
        relations.add(relation);
    }

    public void addLabel(Formula formula) {
        labels.add(formula);
    }

    public ArrayList<Formula> getInterpretation() {
        return interpretation;
    }

    public ArrayList<Formula> getLabels() {
        return labels;
    }

    public String getWorldName() {
        return name;
    }

}
