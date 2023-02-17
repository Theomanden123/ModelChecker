import java.awt.Label;
import java.util.ArrayList;

public class Checker {

    public static void labelAlgorithm(Frame frame, Formula formula) {

        if (formula instanceof BinaryOperator) {
            BinaryOperator f = (BinaryOperator) formula;
            labelAlgorithm(frame, f.getLeft());
            labelAlgorithm(frame, f.getRight());

        } else if (formula instanceof UnaryOperator) {
            UnaryOperator f = (UnaryOperator) formula;
            labelAlgorithm(frame, f.getFormula());
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

        if (formula instanceof Diamond) {
            checkDiamond(frame, formula);
        }

        if (formula instanceof Box) {

        }

    }

    public static void getSubformulas(Formula formula, ArrayList<Formula> subformulas) {

        subformulas.add(formula);
        
        if (formula instanceof BinaryOperator) {
            BinaryOperator f = (BinaryOperator) formula;
            getSubformulas(f.getLeft(), subformulas);
            getSubformulas(f.getRight(), subformulas);

        } else if (formula instanceof UnaryOperator) {
            UnaryOperator f = (UnaryOperator) formula;
            getSubformulas(f.getFormula(), subformulas);
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

    private static void checkDiamond(Frame frame, Formula formula) {
        Diamond diamond = (Diamond) formula;
        
        //Not negative = new Not(diamond.getFormula());
        //Diamond negativeDiamond = new Diamond(negative);

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            if (labels.contains(diamond.getFormula())) {
                System.out.println(world.getWorldName() + " has formula");
                for (Relation relation : world.getIngoingRelations()) {
                    World worldSrc = relation.getSrc();
                    System.out.println(worldSrc.getWorldName() + " will have diamond");
                    if (!worldSrc.getLabels().contains(diamond)) { worldSrc.addLabel(diamond); }
                }
            } /*else {
                for (Relation relation : world.getIngoingRelations()) {
                    World worldSrc = relation.getSrc();
                    System.out.println(worldSrc.getWorldName() + " will have not-diamond");
                    if (!worldSrc.getLabels().contains(negativeDiamond)) { worldSrc.addLabel(negativeDiamond); }
                }
            }*/
        }
    }

    private static void checkBox(Frame frame, Formula formula) {
        Box box = (Box) formula;
        Not negative = new Not(box.getFormula());
        Diamond negativeDiamond = new Diamond(negative);

        for (World world : frame.getWorlds()) {
            ArrayList<Formula> labels = world.getLabels();
            for (Relation relation : world.getOutgoingRelations()) {

            }
            if (labels.contains(negativeDiamond)) {

            }
        }

    }

}
