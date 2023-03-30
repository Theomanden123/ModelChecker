import java.util.ArrayList;

public class Checker {

    public static void label(Frame frame, Formula formula) {

        if (formula instanceof BinaryOperator) {
            BinaryOperator f = (BinaryOperator) formula;
            label(frame, f.getLeft());
            label(frame, f.getRight());

        } else if (formula instanceof UnaryOperator) {
            UnaryOperator f = (UnaryOperator) formula;
            label(frame, f.getFormula());
        }

        if (formula instanceof Literal) {
            checkPropositions(frame, formula);
        }
        if (formula instanceof And)  {
            checkAnd(frame, formula);
        }
        if (formula instanceof Or) {
            checkOr(frame, formula);
        }
        if (formula instanceof Imp) {
            checkImp(frame, formula);
        }
        
        if (formula instanceof Equiv) {
            checkEquiv(frame, formula);
        }

        if (formula instanceof Not) {
            checkNot(frame, formula);
        }

        if (formula instanceof Diamond) {
            checkDiamond(frame, formula);
        }

        if (formula instanceof Box) {
            checkBox(frame, formula);
        }

        if (formula instanceof Knowledge) {
            checkKnowledge(frame, formula);
        }

        if (formula instanceof Everybody) {
            checkEverybody(frame, formula);
        }

        if (formula instanceof Common) {
            checkCommon(frame, formula);
        }

        if (formula instanceof Distributed) {
            checkDistributed(frame, formula);
        }
    }
    
    private static void checkPropositions(Frame frame, Formula formula) {
        for (World world : frame.getWorlds()) {
            if (world.getInterpretation().contains(formula)) {
                world.addLabel(formula);
            } else {
                Not not = new Not(formula);
                world.addLabel(not);
            }
        }
    }

    private static void checkAnd(Frame frame, Formula formula) {
        BinaryOperator f = (BinaryOperator) formula;
        
        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f.getLeft()) && labels.contains(f.getRight())) {
                labels.add(f);
            }
        }
    }

    private static void checkOr(Frame frame, Formula formula) {
        BinaryOperator f = (BinaryOperator) formula;
        
        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f.getLeft()) || labels.contains(f.getRight())) {
                labels.add(f);
            }
        }
    }

    private static void checkImp(Frame frame, Formula formula) {
        BinaryOperator f = (BinaryOperator) formula;

        Formula right;
        if (f.getRight() instanceof Not) {
            Not r = (Not) f.getRight();
            right = r.getFormula();
        } else {
            right = new Not(f.getRight());
        }

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (!(labels.contains(f.getLeft()) && labels.contains(right))) {
                labels.add(f);
            } 
        }
    }

    private static void checkEquiv(Frame frame, Formula formula) {
        BinaryOperator f = (BinaryOperator) formula;

        Formula left;
        if (f.getLeft() instanceof Not) {
            Not l = (Not) f.getLeft();
            left = l.getFormula();
        } else {
            left = new Not(f.getLeft());
        }

        Formula right;
        if (f.getRight() instanceof Not) {
            Not r = (Not) f.getRight();
            right = r.getFormula();
        } else {
            right = new Not(f.getRight());
        }

        for (World world : frame.getWorlds()) {

            ArrayList<Formula> labels = world.getLabels();

            if (labels.contains(f.getLeft()) && labels.contains(f.getRight()) ||
                labels.contains(left) && labels.contains(right)) {
                labels.add(f);
            }   
        }
    }

    private static void checkNot(Frame frame, Formula formula) {
        Not not = (Not) formula;
        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (!labels.contains(not.getFormula())) {
                labels.add(not);
            }
        }
    }

    private static void checkDiamond(Frame frame, Formula formula) {
        Diamond diamond = (Diamond) formula;

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(diamond.getFormula())) {
                for (Relation relation : world.getIngoingRelations()) {
                    World worldSrc = relation.getSrc();
                    if (!worldSrc.getLabels().contains(diamond)) { worldSrc.addLabel(diamond); }
                }
            } 
        }
    }

    private static void checkBox(Frame frame, Formula formula) {
        Box box = (Box) formula;

        for (World world : frame.getWorlds()) {
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (!relation.getDest().getLabels().contains(box.getFormula())) {
                    isPresent = false;
                    break;
                }
            }
            if (isPresent) { labels.add(box); }
        }
    }

    private static void checkKnowledge(Frame frame, Formula formula) {
        Knowledge knowledge = (Knowledge) formula;
        Agent agent = knowledge.getAgent();

        for (World world : frame.getWorlds()) {
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (relation.contains(agent)) {
                    if (!relation.getDest().getLabels().contains(knowledge.getFormula())) {
                        isPresent = false;
                        break;
                    }
                }
            }
            if (isPresent) { labels.add(knowledge); }
        }
    }

    private static void checkEverybody(Frame frame, Formula formula) {
        Everybody everybody = (Everybody) formula;
        ArrayList<Knowledge> knows = new ArrayList<Knowledge>();

        for (Agent agent : everybody.getGroup()) {
            Knowledge knowledge = new Knowledge(agent, everybody.getFormula());
            knows.add(knowledge);
            checkKnowledge(frame, knowledge);
        }

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            Boolean isPresent = true;
            for (Knowledge knowledge : knows) {
                if (!labels.contains(knowledge)) {
                    isPresent = false;
                    break;
                }
            }
            if (isPresent) { labels.add(everybody); }
        }
    }

    private static void checkCommon(Frame frame, Formula formula) {
        Common common = (Common) formula;

        Formula f = common.getFormula();

        for (int i = 0; i < common.getGroup().size(); i++) {
            Everybody everybody = new Everybody(common.getGroup(), f);
            checkEverybody(frame, everybody);
            f = everybody;
        }

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f)) { labels.add(common); }
        }
    }

    private static void checkDistributed(Frame frame, Formula formula) {
        Distributed distributed = (Distributed) formula;
        ArrayList<Agent> group = distributed.getGroup();

        for (World world : frame.getWorlds()) {
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (relation.containsAll(group)) {
                    if (!relation.getDest().getLabels().contains(distributed.getFormula())) {
                        isPresent = false;
                        break;
                    }
                }
            }
            if (isPresent) { labels.add(distributed); }
        }
    }

    public static String getSystem(ArrayList<String> axioms) {
        if (axioms.contains("T") &&
            axioms.contains("K")) {
                return "T";
            }
        return "K";
    }
   
    public static ArrayList<String> getAxioms(Frame frame) {
        ArrayList<String> axioms = new ArrayList<String>();

        axioms.add("K");

        if ( isSerial(frame) ) {axioms.add("D"); }
        if ( isReflexive(frame) ) { axioms.add("T"); }
        if ( isTransitive(frame) ) { axioms.add("4"); }
        if ( isSymmetric(frame) ) { axioms.add("B"); }
        if ( isEuclidean(frame) ) { axioms.add("5"); }

        return axioms;
    }

    private static boolean isSerial(Frame frame) {
        int numberOfAgents = frame.getAgents().size(); 
        int index = frame.getAgents().size();
        if (numberOfAgents == 0) {
            index = 1;
        }
        for (int i = 0; i < index; i++) {
            ArrayList<Agent> agents = frame.getAgents();
            for (World x : frame.getWorlds()) {
                ArrayList<Relation> relations = x.getOutgoingRelations();
                boolean represented = false;
                if (relations.size() == 0) { return false; }
                for (Relation relation : relations) {
                    if (numberOfAgents == 0 || relation.contains(agents.get(i))) { 
                        represented = true; break;
                    }
                }
                if (!represented) { return false;}
            }
        }
        return true;
    }

    private static boolean isReflexive(Frame frame) {
        int numberOfAgents = frame.getAgents().size(); 
        int index = frame.getAgents().size();
        if (numberOfAgents == 0) {
            index = 1;
        }
        for (int i = 0; i < index; i++) {
            ArrayList<Agent> agents = frame.getAgents();
            for (World x : frame.getWorlds()) {
                ArrayList<Relation> relations = x.getOutgoingRelations();
                boolean represented = false;
                if (relations.size() == 0) { return false; }
                for (Relation relation : relations) {
                    World y = relation.getDest(); 
                    if (numberOfAgents == 0 || relation.contains(agents.get(i))) { 
                        if (x == y) {
                            represented = true; break;
                        }
                    }
                }
                if (!represented) { return false;}
            }
        }
        return true;
    }

    public static boolean isTransitive(Frame frame) {
        int numberOfAgents = frame.getAgents().size(); 
        int index = frame.getAgents().size();
        if (numberOfAgents == 0) {
            index = 1;
        }
        for (int i = 0; i < index; i++) {
            ArrayList<Agent> agents = frame.getAgents();
            for (World x : frame.getWorlds()) {
                ArrayList<Relation> xRelations = x.getOutgoingRelations();
                for (Relation xEdges : xRelations) {
                    if (numberOfAgents == 0 || xEdges.contains(agents.get(i))) {
                        ArrayList<Relation> yRelations = xEdges.getDest().getOutgoingRelations();
                        for (Relation yEdges : yRelations) {
                            if (numberOfAgents == 0 || yEdges.contains(agents.get(i))) {
                                World z = yEdges.getDest();
                                if (!x.getExistingRelation(z)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isEuclidean(Frame frame) {
        int numberOfAgents = frame.getAgents().size();
        int index = numberOfAgents;
        if (numberOfAgents == 0) {
            index = 1;
        }
        for (int i = 0; i < index; i++) {
            ArrayList<Agent> agents = frame.getAgents();
            for (World x : frame.getWorlds()) {
                ArrayList<Relation> xRelations = x.getOutgoingRelations();
                for (Relation xEdge : xRelations) {
                    World y = xEdge.getDest();
                    if (numberOfAgents == 0 || xEdge.contains(agents.get(i))) {
                        for (Relation yEdge : xRelations) {
                            // Points to same node
                            if (xEdge == yEdge) { continue; }
                            if (numberOfAgents == 0 || yEdge.contains(agents.get(i))) {
                                World z = yEdge.getDest();
                                if (!y.getExistingRelation(z)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private static boolean isSymmetric(Frame frame) {
        int numberOfAgents = frame.getAgents().size();
        int index = numberOfAgents;
        if (numberOfAgents == 0) {
            index = 1;
        }
        for (int i = 0; i < index; i++) {
            ArrayList<Agent> agents = frame.getAgents();
            for (World x : frame.getWorlds()) {
                ArrayList<Relation> xRelations = x.getOutgoingRelations();
                for (Relation relation : xRelations) {
                    if (numberOfAgents == 0 || relation.contains(agents.get(i))) {
                        World y = relation.getDest(); 
                        if ( !y.getExistingRelation(x) ) { return false; }  
                    }
                } 
            }
        }
        return true;
    }
}
