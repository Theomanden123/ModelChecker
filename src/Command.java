import java.util.ArrayList;
import java.util.Scanner;

public class Command {

    public static void run() {
        Scanner scanner = new Scanner(System.in); 
        
        String input;
        
        System.out.print("Choose frame: "); input = scanner.nextLine();

        Frame frame = null;

        switch(input) {
            case "Alphabet":
                frame = Constructor.frameAlphabet();
                break;
            case "Distinct":
                frame = Constructor.frameDistinguishing();
                break;
            case "Muddy":
                frame = Constructor.frameMuddy();
                break;
            case "Poker":
                frame = Constructor.framePoker();
                break;
            case "Sunshine":
                frame = Constructor.frameSunshine();
                break;
            case "Serial":
                frame = Constructor.frameSerial();
                break;
            case "Reflexive":
                frame = Constructor.frameReflexive();
                break;
            case "Transitive":
                frame = Constructor.frameTransitive();
                break;
            case "Symmetric":
                frame = Constructor.frameSymmetric();
                break;
            case "System4":
                frame = Constructor.frameSystem4();
                break;
            case "System5":
                frame = Constructor.frameSystem5();
                break;
            case "New":
                ArrayList<World> worlds = queryUser(scanner);
                frame = Constructor.frameMadeByUser(worlds);
                break;
        } 

        ArrayList<String> axioms = Checker.getAxioms(frame);
        String system = Checker.getSystem(axioms);

        System.out.println("Found axioms: " + axioms.toString());
        System.out.println("Chosen frame is in the " + system + " class of frames");

        while (true) {

            System.out.print("Formula: "); input = scanner.nextLine();

            if (input.toUpperCase().equals("END")) { break; }

            Formula formula = Parser.getFormulaFromString(frame, input);
            Checker.label(frame, formula, new ArrayList<World>());
            ArrayList<World> worlds = frame.getModellingWorlds(formula);

            System.out.println(formula.toString() + " is true at " + worlds.toString());

        }

        scanner.close();
    }

    private static ArrayList<World> queryUser(Scanner scanner) {

        ArrayList<World> worlds = new ArrayList<World>();

        System.out.println("What worlds exist?");
        String[] list = scanner.nextLine().split(" ");

        for (int i = 0; i < list.length; i++) {
            worlds.add(new World(list[i]));
        }

        ArrayList<Formula> propositions = new ArrayList<Formula>();

        for (World world : worlds) {
            System.out.println("Which propositions are true at " + world.toString());
            String[] input = scanner.nextLine().split(" ");
            if (!(input.length == 1 && input[0].equals(" "))) {
                for (int i = 0; i < input.length; i++) {
                    Literal literal = new Literal(input[i].charAt(0));
                    world.addProposition(literal);
                    if (!propositions.contains(literal)) { propositions.add(literal); }
                }
            }

        }

        for (World world : worlds) {
            ArrayList<Formula> interpretation = world.getInterpretation();
            for (Formula proposition : propositions) {
                if (!interpretation.contains(proposition)) { 
                    Not not = new Not(proposition);
                    world.addProposition(not); 
                }
            }
        }

        for (World world : worlds) {
            System.out.println("What worlds should " + world.toString() + " point to?");
            String[] relations = scanner.nextLine().split(" ");

            for (int i = 0; i < relations.length; i++) {

                World src = world;
                World dest = null;
                
                for (World w : worlds) {
                    String name = relations[i];
                    if (w.toString().equals(name)) { dest = w; };
                }
                
                if (dest == null) { continue; }

                new Relation(src, dest);
            }
        }
        return worlds;
    }

    public static void printAllWorldLabels(Frame frame) {
        ArrayList<World> worlds = frame.getWorlds();
        for (World world : worlds) {
            ArrayList<Formula> labels = world.getLabels();
            System.out.println(world.toString());
            for (Formula formula : labels) {
                System.out.println(formula);
            }
        }
    }
    
}
