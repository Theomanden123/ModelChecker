import java.util.ArrayList;

public class Constructor {
    
    public static Frame frameSunshine() {

        Frame frame = new Frame();
        Agent a = new Agent("1");
        Agent b = new Agent("2");
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

    public static Frame frameMadeByUser(ArrayList<World> worlds) {
        Frame frame = new Frame(worlds);
        return frame;
    }

}
