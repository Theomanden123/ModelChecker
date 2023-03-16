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
}
