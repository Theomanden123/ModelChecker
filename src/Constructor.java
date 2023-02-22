import java.util.ArrayList;

public class Constructor {
    
    public static Frame frameSunshine() {

        Frame frame = new Frame();

        World s = new World("s");
        World t = new World("t");
        World u = new World("u");

        frame.addWorld(s);
        frame.addWorld(t);
        frame.addWorld(u);

        new Relation(s, u);
        new Relation(s, t);

        Literal p = new Literal('p');
        Literal q = new Literal('q');

        Formula notp = new Not(p);
        Formula notq = new Not(q);

        s.addLiteral(p);
        s.addLiteral(q);

        t.addLiteral(p);
        t.addLiteral(notq);

        u.addLiteral(q);
        u.addLiteral(notp);

        return frame;
    }

    public static Frame frameMadeByUser(ArrayList<World> worlds) {
        Frame frame = new Frame(worlds);
        return frame;
    }

}
