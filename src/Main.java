public class Main {


    public static void main( String[] args ) {

        System.out.println("FUCK DEN KÆRTE");

        // Frame

        World s = new World("s");
        World t = new World("t");
        World u = new World("u");

        Relation r1 = new Relation(s, u);
        Relation r2 = new Relation(s, t);
        Relation r3 = new Relation(t, s);
        Relation r4 = new Relation(u, s);

        // Model

        Literal p = new Literal('p');
        Literal q = new Literal('q');

        Formula notp = new Not(p);
        Formula notq = new Not(q);

        s.addTruth(p);
        s.addTruth(q);

        t.addTruth(q);
        t.addTruth(notp);

        u.addTruth(p);
        u.addTruth(notq);




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
