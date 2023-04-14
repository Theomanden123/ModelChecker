import java.util.ArrayList;

public class Checker {

    public static void label(Frame frame, Formula formula, ArrayList<World> blacklist) {

        if (formula instanceof BinaryOperator) {
            BinaryOperator f = (BinaryOperator) formula;
            label(frame, f.getLeft(), blacklist);
            label(frame, f.getRight(), blacklist);

        } else if (formula instanceof Announcement) {
            Announcement announcement = (Announcement) formula;
            Not announceNot = new Not(announcement.getAnnouncement());
            ArrayList<World> blackListNew = frame.getBlacklist(announceNot);
            blacklist.addAll(blackListNew);
            System.out.println(blacklist);
            label(frame, announcement.getFormula(), blacklist);
            checkAnnouncement(frame, formula, blacklist);
        } 
        else if (formula instanceof UnaryOperator) {
            UnaryOperator f = (UnaryOperator) formula;
            label(frame, f.getFormula(), blacklist);
        }

        if (formula instanceof Literal) {
            checkPropositions(frame, formula, blacklist);
        }
        if (formula instanceof And)  {
            checkAnd(frame, formula, blacklist);
        }
        if (formula instanceof Or) {
            checkOr(frame, formula, blacklist);
        }
        if (formula instanceof Imp) {
            checkImp(frame, formula, blacklist);
        }
        
        if (formula instanceof Equiv) {
            checkEquiv(frame, formula, blacklist);
        }

        if (formula instanceof Not) {
            checkNot(frame, formula, blacklist);
        }

        if (formula instanceof Diamond) {
            checkDiamond(frame, formula, blacklist);
        }

        if (formula instanceof Box) {
            checkBox(frame, formula, blacklist);
        }

        if (formula instanceof Knowledge) {
            checkKnowledge(frame, formula, blacklist);
        }

        if (formula instanceof Everybody) {
            checkEverybody(frame, formula, blacklist);
        }

        if (formula instanceof Common) {
            checkCommon(frame, formula, blacklist);
        }

        if (formula instanceof Distributed) {
            checkDistributed(frame, formula, blacklist);
        }
    }
    
    private static void checkPropositions(Frame frame, Formula formula, ArrayList<World> blacklist) {
        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            if (world.getInterpretation().contains(formula)) {
                world.addLabel(formula);
            } else {
                Not not = new Not(formula);
                world.addLabel(not);
            }
        }
    }

    private static void checkAnd(Frame frame, Formula formula, ArrayList<World> blacklist) {
        BinaryOperator f = (BinaryOperator) formula;
        
        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f.getLeft()) && labels.contains(f.getRight())) {
                labels.add(f);
            }
        }
    }

    private static void checkOr(Frame frame, Formula formula, ArrayList<World> blacklist) {
        BinaryOperator f = (BinaryOperator) formula;
        
        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f.getLeft()) || labels.contains(f.getRight())) {
                labels.add(f);
            }
        }
    }

    private static void checkImp(Frame frame, Formula formula, ArrayList<World> blacklist) {
        BinaryOperator f = (BinaryOperator) formula;

        Formula right;
        if (f.getRight() instanceof Not) {
            Not r = (Not) f.getRight();
            right = r.getFormula();
        } else {
            right = new Not(f.getRight());
        }

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (!(labels.contains(f.getLeft()) && labels.contains(right))) {
                labels.add(f);
            } 
        }
    }

    private static void checkEquiv(Frame frame, Formula formula, ArrayList<World> blacklist) {
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

            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();

            if (labels.contains(f.getLeft()) && labels.contains(f.getRight()) ||
                labels.contains(left) && labels.contains(right)) {
                labels.add(f);
            }   
        }
    }

    private static void checkNot(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Not not = (Not) formula;
        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (!labels.contains(not.getFormula())) {
                labels.add(not);
            }
        }
    }

    private static void checkDiamond(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Diamond diamond = (Diamond) formula;

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(diamond.getFormula())) {
                for (Relation relation : world.getIngoingRelations()) {
                    if (blacklist.contains(relation.getDest())) { continue; }
                    World worldSrc = relation.getSrc();
                    if (!worldSrc.getLabels().contains(diamond)) { worldSrc.addLabel(diamond); }
                }
            } 
        }
    }

    private static void checkBox(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Box box = (Box) formula;

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (blacklist.contains(relation.getDest())) { continue; }
                if (!relation.getDest().getLabels().contains(box.getFormula())) {
                    isPresent = false;
                    break;
                }
            }
            if (isPresent) { labels.add(box); }
        }
    }

    private static void checkKnowledge(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Knowledge knowledge = (Knowledge) formula;
        Agent agent = knowledge.getAgent();

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (blacklist.contains(relation.getDest())) { continue; }
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

    private static void checkEverybody(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Everybody everybody = (Everybody) formula;
        ArrayList<Knowledge> knows = new ArrayList<Knowledge>();

        for (Agent agent : everybody.getGroup()) {
            Knowledge knowledge = new Knowledge(agent, everybody.getFormula());
            knows.add(knowledge);
            checkKnowledge(frame, knowledge, blacklist);
        }

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
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

    private static void checkCommon(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Common common = (Common) formula;

        Formula f = common.getFormula();

        for (int i = 0; i < common.getGroup().size(); i++) {
            Everybody everybody = new Everybody(common.getGroup(), f);
            checkEverybody(frame, everybody, blacklist);
            f = everybody;
        }

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(f)) { labels.add(common); }
        }
    }

    private static void checkDistributed(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Distributed distributed = (Distributed) formula;
        ArrayList<Agent> group = distributed.getGroup();

        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            Boolean isPresent = true;
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {
                if (blacklist.contains(relation.getDest())) { continue; }
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

    private static void checkAnnouncement(Frame frame, Formula formula, ArrayList<World> blacklist) {
        Announcement announcement = (Announcement) formula;
        for (World world : frame.getWorlds()) {
            if (blacklist.contains(world)) { continue; }
            if (world.getLabels().contains(announcement.getFormula())) {
                world.addLabel(announcement);
            }
        }
    }

    public static String getSystem(ArrayList<String> axioms) {
        
        if (axioms.contains("T") || axioms.contains("B")) {
            if (axioms.contains("D")) { axioms.remove("D"); }
        }

        if (axioms.contains("K") &&
            axioms.contains("T") &&
            axioms.contains("4") &&
            axioms.contains("B") ) {
            return "S5";
        } else if (axioms.contains("K") &&
                   axioms.contains("T") &&
                   axioms.contains("4") ) {
            return "S4";
        } else if (axioms.contains("K") &&
                   axioms.contains("4") ) {
            return "K4"; 
        } else if (axioms.contains("K") &&
                   axioms.contains("T") ) {
            return "T";
        } else if (axioms.contains("K") &&
                   axioms.contains("B") ) {
            return "B";
        } else if (axioms.contains("K") &&
                   axioms.contains("D") ) {
            return "D";
        } else {
            return "K";
        }
        
    }
   
    public static ArrayList<String> getAxioms(Frame frame) {
        ArrayList<String> axioms = new ArrayList<String>();

        axioms.add("K");

        if ( isSerial(frame) ) { axioms.add("D"); }
        if ( isReflexive(frame) ) { axioms.add("T"); }
        if ( isTransitive(frame) ) { axioms.add("4"); }
        if ( isSymmetric(frame) ) { axioms.add("B"); }

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
