import java.util.ArrayList;

public class Main {


    public static void main( String[] args ) {

        Frame frame = Controller.buildExampleFrame();

        String test = "Equiv(Not(p),Not(q))";
        Formula f = Parser.getFormulaFromString(test);
        Checker.labelAlgorithm(frame, f);
        Parser.printAllWorldLabels(frame);

        //ArrayList<Formula> subf = new ArrayList<>();
        //Label.getSubformulas(f, subf);
        //Parser.subformulasToString(subf);



    }

    // Make Frame
    // How many states?
    // State 1: Where do I lead?
    // Cont. in for loop

    // Make Model (Frame > Interpretations + Accessibility)
    // For loop in Frame
    // State 1: What is true?
    // Relation X: Who can access me?
    

}
