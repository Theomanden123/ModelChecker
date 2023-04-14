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
        Verum t = new Verum();
        labels.add(t);
        this.name = name;
    }

    public void addProposition(Formula formula) {
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

    @Override
    public String toString() {
        return name;
    }

    public boolean getExistingRelation(World world) {
        for (Relation relation : outgoingRelations) {
            if (relation.getDest().name.equals(world.name)) {
                return true;
            }
        }
        return false;
    }

    public void setLabelList(ArrayList<Formula> labelCopy) {
        labels = labelCopy;
        System.out.println(labels);
    }

}
