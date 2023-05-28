
// Constructor.java is responsible for building every frame in the program.

public class Constructor {
    
    public static Frame frameSunshine() {

        Frame frame = new Frame();
        Agent a = new Agent("a");
        Agent b = new Agent("b");
        frame.addAgent(a);
        frame.addAgent(b);

        World s = new World("s");
        World t = new World("t");
        World u = new World("u");

        frame.addWorld(s);
        frame.addWorld(t);
        frame.addWorld(u);

        Relation r1 = new Relation(s, s);
        Relation r2 = new Relation(t, t);
        Relation r3 = new Relation(u, u);
        Relation r4 = new Relation(s, u);
        Relation r5 = new Relation(u, s);
        Relation r6 = new Relation(s, t);
        Relation r7 = new Relation(t, s);

        r1.addAgent(a);
        r2.addAgent(a);
        r3.addAgent(a);
        r6.addAgent(a);
        r7.addAgent(a);

        r1.addAgent(b);
        r2.addAgent(b);
        r3.addAgent(b);
        r4.addAgent(b);
        r5.addAgent(b);

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

        World w1 = new World("W1");
        World w2 = new World("W2");
        World w3 = new World("W3");
        World w4 = new World("W4");

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

    public static Frame framePoker() {
        Frame frame = new Frame();

        Agent a = new Agent("a");
        Agent b = new Agent("b");
        
        frame.addAgent(a);
        frame.addAgent(b);

        World JQ = new World("JQ");
        World AQ = new World("AQ");
        World AJ = new World("AJ");
        World QJ = new World("QJ");
        World QA = new World("QA");
        World JA = new World("JA");

        frame.addWorld(JQ);
        frame.addWorld(AQ);
        frame.addWorld(AJ);
        frame.addWorld(QJ);
        frame.addWorld(QA);
        frame.addWorld(JA);

        // Alice: Ace, Queen, Jack
        Literal aA = new Literal('A');
        Literal aQ = new Literal('Q');
        Literal aJ = new Literal('J');

        // Bob: Ace, Queen, Jack
        Literal bA = new Literal('a');
        Literal bQ = new Literal('q');
        Literal bJ = new Literal('j');

        // Negations
        Formula notaA = new Not(aA);
        Formula notaQ = new Not(aQ);
        Formula notaJ = new Not(aJ);
        Formula notbA = new Not(bA);
        Formula notbQ = new Not(bQ);
        Formula notbJ = new Not(bJ);

        // World interpretations
        JQ.addProposition(aJ);
        JQ.addProposition(bQ);
        JQ.addProposition(notaA);
        JQ.addProposition(notaQ);
        JQ.addProposition(notbA);
        JQ.addProposition(notbJ);

        AQ.addProposition(aA);
        AQ.addProposition(bQ);
        AQ.addProposition(notaQ);
        AQ.addProposition(notaJ);
        AQ.addProposition(notbA);
        AQ.addProposition(notbJ);

        AJ.addProposition(aA);
        AJ.addProposition(bJ);
        AJ.addProposition(notaQ);
        AJ.addProposition(notaJ);
        AJ.addProposition(notbA);
        AJ.addProposition(notbQ);

        QJ.addProposition(aQ);
        QJ.addProposition(bJ);
        QJ.addProposition(notaA);
        QJ.addProposition(notaJ);
        QJ.addProposition(notbA);
        QJ.addProposition(notbQ);

        QA.addProposition(aQ);
        QA.addProposition(bA);
        QA.addProposition(notaA);
        QA.addProposition(notaJ);
        QA.addProposition(notbQ);
        QA.addProposition(notbJ);

        JA.addProposition(aJ);
        JA.addProposition(bA);
        JA.addProposition(notaA);
        JA.addProposition(notaQ);
        JA.addProposition(notbQ);
        JA.addProposition(notbJ);

        Relation JQAQ = new Relation(JQ, AQ);
        Relation AQJQ = new Relation(AQ, JQ);

        JQAQ.addAgent(b);
        AQJQ.addAgent(b);

        Relation AQAJ = new Relation(AQ, AJ);
        Relation AJAQ = new Relation(AJ, AQ);

        AQAJ.addAgent(a);
        AJAQ.addAgent(a);

        Relation AJQJ = new Relation(AJ, QJ);
        Relation QJAJ = new Relation(QJ, AJ);

        AJQJ.addAgent(b);
        QJAJ.addAgent(b);

        Relation QJQA = new Relation(QJ, QA);
        Relation QAQJ = new Relation(QA, QJ);

        QJQA.addAgent(a);
        QAQJ.addAgent(a);

        Relation QAJA = new Relation(QA, JA);
        Relation JAQA = new Relation(JA, QA);

        QAJA.addAgent(b);
        JAQA.addAgent(b);

        Relation JAJQ = new Relation(JA, JQ);
        Relation JQJA = new Relation(JQ, JA);

        JAJQ.addAgent(a);
        JQJA.addAgent(a);

        // Reflexive relations
        Relation JQJQ = new Relation(JQ, JQ);
        Relation AQAQ = new Relation(AQ, AQ);
        Relation AJAJ = new Relation(AJ, AJ);
        Relation QJQJ = new Relation(QJ, QJ);
        Relation QAQA = new Relation(QA, QA);
        Relation JAJA = new Relation(JA, JA);

        JQJQ.addAgent(a);
        JQJQ.addAgent(b);

        AQAQ.addAgent(a);
        AQAQ.addAgent(b);

        AJAJ.addAgent(a);
        AJAJ.addAgent(b);

        QJQJ.addAgent(a);
        QJQJ.addAgent(b);

        QAQA.addAgent(a);
        QAQA.addAgent(b);

        JAJA.addAgent(a);
        JAJA.addAgent(b);

        return frame;
    }

    public static Frame frameMuddy(){
        Frame frame = new Frame();

        Agent a = new Agent("a"); frame.addAgent(a);
        Agent b = new Agent("b"); frame.addAgent(b);
        Agent c = new Agent("c"); frame.addAgent(c);

        World w1 = new World("W1"); frame.addWorld(w1);
        World w2 = new World("W2"); frame.addWorld(w2);
        World w3 = new World("W3"); frame.addWorld(w3);
        World w4 = new World("W4"); frame.addWorld(w4);
        World w5 = new World("W5"); frame.addWorld(w5);
        World w6 = new World("W6"); frame.addWorld(w6);
        World w7 = new World("W7"); frame.addWorld(w7);
        World w8 = new World("W8"); frame.addWorld(w8);

        Literal ma = new Literal('a'); Formula ca = new Not(ma);
        Literal mb = new Literal('b'); Formula cb = new Not(mb);
        Literal mc = new Literal('c'); Formula cc = new Not(mc);

        w1.addProposition(ma); w2.addProposition(ma); w3.addProposition(ma); w4.addProposition(ca);
        w1.addProposition(mb); w2.addProposition(cb); w3.addProposition(mb); w4.addProposition(mb);
        w1.addProposition(mc); w2.addProposition(mc); w3.addProposition(cc); w4.addProposition(mc);
        
        w5.addProposition(ma); w6.addProposition(ca); w7.addProposition(ca); w8.addProposition(ca);
        w5.addProposition(cb); w6.addProposition(cb); w7.addProposition(mb); w8.addProposition(cb);
        w5.addProposition(cc); w6.addProposition(mc); w7.addProposition(cc); w8.addProposition(cc);

        Relation w1w1 = new Relation(w1, w1); w1w1.addAgent(a); w1w1.addAgent(b); w1w1.addAgent(c);
        Relation w2w2 = new Relation(w2, w2); w2w2.addAgent(a); w2w2.addAgent(b); w2w2.addAgent(c);
        Relation w3w3 = new Relation(w3, w3); w3w3.addAgent(a); w3w3.addAgent(b); w3w3.addAgent(c);
        Relation w4w4 = new Relation(w4, w4); w4w4.addAgent(a); w4w4.addAgent(b); w4w4.addAgent(c);
        Relation w5w5 = new Relation(w5, w5); w5w5.addAgent(a); w5w5.addAgent(b); w5w5.addAgent(c);
        Relation w6w6 = new Relation(w6, w6); w6w6.addAgent(a); w6w6.addAgent(b); w6w6.addAgent(c);
        Relation w7w7 = new Relation(w7, w7); w7w7.addAgent(a); w7w7.addAgent(b); w7w7.addAgent(c);
        Relation w8w8 = new Relation(w8, w8); w8w8.addAgent(a); w8w8.addAgent(b); w8w8.addAgent(c);

        Relation w1w2 = new Relation(w1, w2); w1w2.addAgent(b); Relation w2w1 = new Relation(w2, w1); w2w1.addAgent(b);
        Relation w1w3 = new Relation(w1, w3); w1w3.addAgent(c); Relation w3w1 = new Relation(w3, w1); w3w1.addAgent(c);
        Relation w1w4 = new Relation(w1, w4); w1w4.addAgent(a); Relation w4w1 = new Relation(w4, w1); w4w1.addAgent(a);

        Relation w2w5 = new Relation(w2, w5); w2w5.addAgent(c); Relation w5w2 = new Relation(w5, w2); w5w2.addAgent(c);
        Relation w2w6 = new Relation(w2, w6); w2w6.addAgent(a); Relation w6w2 = new Relation(w6, w2); w6w2.addAgent(a);

        Relation w3w5 = new Relation(w3, w5); w3w5.addAgent(b); Relation w5w3 = new Relation(w5, w3); w5w3.addAgent(b);
        Relation w3w7 = new Relation(w3, w7); w3w7.addAgent(a); Relation w7w3 = new Relation(w7, w3); w7w3.addAgent(a);

        Relation w4w6 = new Relation(w4, w6); w4w6.addAgent(b); Relation w6w4 = new Relation(w6, w4); w6w4.addAgent(b);
        Relation w4w7 = new Relation(w4, w7); w4w7.addAgent(c); Relation w7w4 = new Relation(w7, w4); w7w4.addAgent(c);

        Relation w5w8 = new Relation(w5, w8); w5w8.addAgent(a); Relation w8w5 = new Relation(w8, w5); w8w5.addAgent(a);
        Relation w6w8 = new Relation(w6, w8); w6w8.addAgent(c); Relation w8w6 = new Relation(w8, w6); w8w6.addAgent(c);
        Relation w7w8 = new Relation(w7, w8); w7w8.addAgent(b); Relation w8w7 = new Relation(w8, w7); w8w7.addAgent(b);

        return frame;
    }

    public static Frame frameSerial() {
        Frame frame = new Frame();
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");
        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);
        new Relation(x, y);
        new Relation(y, z);
        new Relation(z, x);
        return frame;
    }

    public static Frame frameReflexive() {
        Frame frame = new Frame();
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");
        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);
        new Relation(x, x);
        new Relation(y, y);
        new Relation(z, z);
        new Relation(x, y);
        new Relation(y, z);
        new Relation(z, x);
        return frame;
    }

    public static Frame frameTransitive() {
        Frame frame = new Frame();
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");
        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);
        new Relation(x, y);
        new Relation(y, z);
        new Relation(x, z);
        return frame;
    }

    public static Frame frameSymmetric() {
        Frame frame = new Frame();
        World w = new World("w");
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");
        frame.addWorld(w);
        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);
        new Relation(w, x);
        new Relation(x, w);
        new Relation(x, y);
        new Relation(y, x);
        new Relation(y, z);
        new Relation(z, y);
        new Relation(z, w);
        new Relation(w, z);
        return frame;
    }

    public static Frame frameSystem4() {
        Frame frame = new Frame();
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");
        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);
        new Relation(x, x);
        new Relation(y, y);
        new Relation(z, z);
        new Relation(x, y);
        new Relation(y, z);
        new Relation(x, z);
        return frame;
    }

    public static Frame frameSystem5() {

        Frame frame = new Frame();
        World x = new World("x");
        World y = new World("y");
        World z = new World("z");

        frame.addWorld(x);
        frame.addWorld(y);
        frame.addWorld(z);

        new Relation(x, x);
        new Relation(y, y);
        new Relation(z, z);

        new Relation(x, y);
        new Relation(y, x);

        new Relation(y, z);
        new Relation(z, y);

        new Relation(x, z);
        new Relation(z, x);
        
        return frame;
    }

    public static Frame frameKripke() {
        Frame frame = new Frame();
        World w1 = new World("w1");
        World w2 = new World("w2");
        World w3 = new World("w3");
        World w4 = new World("w4");
        World w5 = new World("w5");
        World w6 = new World("w6");

        frame.addWorld(w1);
        frame.addWorld(w2);
        frame.addWorld(w3);
        frame.addWorld(w4);
        frame.addWorld(w5);
        frame.addWorld(w6);

        Literal p = new Literal('p');
        Literal q = new Literal('q');
        Literal r = new Literal('r');

        Not notP = new Not(p);
        Not notQ = new Not(q);
        Not notR = new Not(r);

        w1.addProposition(p);
        w1.addProposition(q);
        w1.addProposition(notR);

        w2.addProposition(r);
        w2.addProposition(notP);
        w2.addProposition(notQ);

        w3.addProposition(q);
        w3.addProposition(notP);
        w3.addProposition(notR);

        w4.addProposition(p);
        w4.addProposition(r);
        w4.addProposition(notQ);

        w5.addProposition(p);
        w5.addProposition(q);
        w5.addProposition(r);

        w6.addProposition(p);
        w6.addProposition(notQ);
        w6.addProposition(notR);

        new Relation(w1, w2);
        new Relation(w1, w3);
        new Relation(w1, w6);

        new Relation(w2, w1);
        new Relation(w2, w3);

        new Relation(w3, w1);
        new Relation(w3, w2);
        new Relation(w3, w4);
        new Relation(w3, w5);

        new Relation(w4, w3);

        new Relation(w5, w3);
        new Relation(w5, w6);

        new Relation(w6, w1);
        new Relation(w6, w5);


        return frame;

    }

}
