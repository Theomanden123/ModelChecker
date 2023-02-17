import java.util.ArrayList;

public class World {

    private String name;

    private ArrayList<Formula> interpretation;
    private ArrayList<Relation> outgoingRelations;
    private ArrayList<Relation> ingoingRelations;
    private ArrayList<Formula> labels;

    public World(String name) {
        interpretation = new ArrayList<Formula>();
        outgoingRelations = new ArrayList<Relation>();
        ingoingRelations = new ArrayList<Relation>();
        labels = new ArrayList<Formula>();
        this.name = name;
    }

    public void addIntepretation(Formula formula) {
        interpretation.add(formula);
    }


    public void addOutgoingRelation(Relation relation) {
        outgoingRelations.add(relation);
    }

    public void addIngoingRelation(Relation relation) {
        ingoingRelations.add(relation);
    }

    public ArrayList<Relation> getOutgoingRelations() {
        return outgoingRelations;
    }

    public ArrayList<Relation> getIngoingRelations() {
        return ingoingRelations;
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
