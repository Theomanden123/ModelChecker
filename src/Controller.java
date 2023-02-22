import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

public class Controller {
    
    public static Frame buildFrame() {
        Scanner scanner = new Scanner(System.in);
        
        Frame frame = new Frame();

        // Make worlds
        System.out.println("Enter worlds of model: ");
        String[] worldList = scanner.nextLine().split(" ");
        for (int i = 0; i < worldList.length; i++) {
            World world = new World(worldList[i]); 
            frame.addWorld(world);
        }
        
        // State interpretations for each world
        ArrayList<World> worlds = frame.getWorlds();
        ArrayList<Formula> propositionsAdded = new ArrayList<Formula>();
        for (World world : worlds) {
            System.out.println("What is true at: " + world.getWorldName());
            String[] proposition = scanner.nextLine().split(" ");
            for (int i = 0; i < proposition.length; i++) {
                Literal l = new Literal(proposition[i].charAt(0));
                world.addIntepretation(l);
                if (!propositionsAdded.contains(l)) {
                    propositionsAdded.add(l);
                }
            }

        }
        // Add all negations to each world
        for (World world : worlds) {
            ArrayList<Formula> interpretation = world.getInterpretation();
            for (Formula proposition : propositionsAdded) {
                if (!interpretation.contains(proposition)) {
                    Not not = new Not(proposition);
                    world.addIntepretation(not);
                }
            }
        }

        // Make relations
        for (World world : worlds) {
            System.out.println("Where does " + world.getWorldName() + " go? ");
            String[] relations = scanner.nextLine().split(" ");

            for (int i = 0; i < relations.length; i++) {
                World dest = frame.getWorldFromName(relations[i]);
                if (dest == null) { continue; }
                new Relation(world, dest);
            }

        }

        scanner.close();

        return frame;
    }

    public static void runModelChecker(Frame frame) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter formula: ");

        String formulaInput = scanner.nextLine();
        Formula formula = Parser.getFormulaFromString(formulaInput);
        Checker.labelAlgorithm(frame, formula);
        ArrayList<World> worlds = Checker.getValidWorlds(frame, formula);
        for (World world : worlds) {
            System.out.println(formulaInput + " is true at " + world.getWorldName());
        }
        scanner.close();
    }
 
    public static Frame buildExampleFrame() {
        
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

        // Model

        Literal p = new Literal('p');
        Literal q = new Literal('q');

        Formula notp = new Not(p);
        Formula notq = new Not(q);

        s.addIntepretation(p);
        s.addIntepretation(q);

        t.addIntepretation(p);
        t.addIntepretation(notq);

        u.addIntepretation(p);
        u.addIntepretation(notq);

        return frame;

    }

}
