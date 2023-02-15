import java.util.ArrayList;

public class Label {

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
    
}
