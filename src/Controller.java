public class Controller {
    
    public Frame buildExampleFrame() {
        
        // Frame

        Frame frame = new Frame();

        World s = new World("s");
        World t = new World("t");
        World u = new World("u");

        frame.addWorld(s);
        frame.addWorld(t);
        frame.addWorld(u);

        new Relation(s, u);
        new Relation(s, t);
        new Relation(t, s);
        new Relation(u, s);

        // Model

        Literal p = new Literal('p');
        Literal q = new Literal('q');

        Formula notp = new Not(p);
        Formula notq = new Not(q);

        s.addIntepretation(p);
        s.addIntepretation(q);

        t.addIntepretation(q);
        t.addIntepretation(notp);

        u.addIntepretation(p);
        u.addIntepretation(notq);

        return frame;

    }

}
