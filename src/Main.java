public class Main {


    public static void main( String[] args ) {

        String test = "Diamond(Imp(p,q))";

        Formula f = Parser.getFormulaFromString(test);

        System.out.println(f.getClass());

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
