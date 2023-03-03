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


        new Relation(s, s);
        new Relation(t, t);
        new Relation(u, u);

        new Relation(s, u);
        new Relation(u, s);
        new Relation(s, t);
        new Relation(t, s);

        Literal p = new Literal('p');
        Formula notp = new Not(p);

        s.addProposition(p);
        t.addProposition(notp);
        u.addProposition(p);

        return frame;
    }

    public static Frame frameAlphabet() {
        Frame frame = new Frame();

        World a = new World("a");
        World b = new World("b");
        World c = new World("c");
        World d = new World("d");
        World e = new World("e");

        frame.addWorld(a);
        frame.addWorld(b);
        frame.addWorld(c);
        frame.addWorld(d);
        frame.addWorld(e);

        new Relation(a, b);
        new Relation(a, c);

        new Relation(b, a);

        new Relation(c, c);
        new Relation(c, d);
        new Relation(c, e);

        new Relation(d, d);
        new Relation(d, b);
        
        Literal p = new Literal('p');
        Literal q = new Literal('q');
        Literal r = new Literal('r');

        Formula notp = new Not(p);
        Formula notq = new Not(q);
        Formula notr = new Not(r);

        a.addProposition(p);
        a.addProposition(notq);
        a.addProposition(notr);

        b.addProposition(notp);
        b.addProposition(q);
        b.addProposition(notr);
        
        c.addProposition(notp);
        c.addProposition(q);
        c.addProposition(notr);

        d.addProposition(notp);
        d.addProposition(q);
        d.addProposition(r);

        e.addProposition(p);
        e.addProposition(q);
        e.addProposition(notr);

        return frame;
    }


    public static Frame frameDistinguishing() {
        Frame frame = new Frame();

        World w1 = new World("w1");
        World w2 = new World("w2");
        World w3 = new World("w3");
        World w4 = new World("w4");

        frame.addWorld(w1);
        frame.addWorld(w2);
        frame.addWorld(w3);
        frame.addWorld(w4);

        new Relation(w1, w2);
        new Relation(w1, w3);

        new Relation(w2, w2);
        new Relation(w2, w4);
        
        new Relation(w3, w4);

        return frame;
    }

    public static Frame frameMadeByUser(ArrayList<World> worlds) {
        Frame frame = new Frame(worlds);
        return frame;
    }

}
