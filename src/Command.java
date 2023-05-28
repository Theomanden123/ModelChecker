import java.util.ArrayList;
import java.util.Scanner;

// Command.java handle user input and control the output of the terminal.

public class Command {

    public static void run() {
        Scanner scanner = new Scanner(System.in); 
        
        String input;
        Frame frame = null;
        do {
            
            System.out.print("Choose frame: "); 
            input = scanner.nextLine();

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
                case "Kripke":
                    frame = Constructor.frameKripke();
                    break;
                case "New":
                    frame = queryUser(scanner);
                    break;
            } 

        } while(frame == null);

        ArrayList<String> axioms = Checker.getAxioms(frame);
        String system = Checker.getSystem(axioms);

        System.out.println("Found axioms: " + axioms.toString());
        System.out.println("Chosen frame is in the " + system + " class of frames");

        while (true) {
            Formula formula = null;
            boolean exitProgram = false;
            boolean inputSearch = true;
            do {
                System.out.print("Formula: ");
                input = scanner.nextLine();
                if (input.length() == 0) { continue; }
                if (input.toUpperCase().equals("EXIT")) { exitProgram = true; }
            
                formula = Parser.getFormulaFromString(frame, input);

                if (exitProgram || formula != null) {
                    inputSearch = false;
                }

            } while (inputSearch);

            if (exitProgram) { break; }
            
            Checker.label(frame, formula, new ArrayList<World>());
            ArrayList<World> worlds = frame.getModellingWorlds(formula);

            System.out.println(formula.toString() + " is true at " + worlds.toString());

        }
        scanner.close();
    }

    private static Frame queryUser(Scanner scanner) {

        ArrayList<World> worlds = new ArrayList<World>();
        ArrayList<Agent> agents = new ArrayList<Agent>();

        String[] list;
        do {
            System.out.println("What worlds exist?");
            list = scanner.nextLine().split(" ");
        } while(list.length == 1 && list[0].equals(""));
        
        for (int i = 0; i < list.length; i++) {
            worlds.add(new World(list[i]));
        }

        System.out.println("What number of agents exists?");
        int agentNumber = scanner.nextInt();
        if (agentNumber >= 2) {
            for (int i = 0; i < agentNumber; i++) {
                System.out.println("What is the name of agent number " + i + "?");
                Agent agent;
                do {
                    String name = scanner.next();
                    agent = new Agent(name);
                } while (agents.contains(agent));
                agents.add(agent);
            }
        }

        ArrayList<Formula> propositions = new ArrayList<Formula>();
        scanner.nextLine();

        for (World world : worlds) {
            System.out.println("Which propositions are true at " + world.toString());
            String[] input = scanner.nextLine().split(" ");
            if (!(input.length == 1 && input[0].equals(""))) {
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
            boolean wrongInput = false;
            do {
                System.out.println("What worlds should " + world.toString() + " point to?");
                
                String[] relations = scanner.nextLine().split(" ");
                if (relations.length == 1 && relations[0].equals("")) {
                    continue;
                }
                for (int i = 0; i < relations.length; i++) {

                    World src = world;
                    World dest = null;
                    
                    for (World w : worlds) {
                        String name = relations[i];
                        if (w.toString().equals(name)) { dest = w; };
                    }
                    
                    if (dest == null) { wrongInput = true ; break; }

                    Relation relation = new Relation(src, dest);
                    if (agentNumber >= 2) {
                        boolean wrongAgents;
                        do {
                            wrongAgents = false;
                            System.out.println("What agents is allowed on the edge: " + src.toString() + " to " + dest.toString() + " ?");
                            String[] agentsRelation = scanner.nextLine().split(" ");
                            int agentsAdded = 0;
                            for (int j = 0; j < agentsRelation.length; j++) {
                                for (Agent a : agents) {
                                    if (a.getName().equals(agentsRelation[j])) {
                                        relation.addAgent(a);
                                        agentsAdded++;
                                    }
                                }
                            }
                            if (agentsRelation.length > agentsAdded) { wrongAgents = true; }

                        } while (wrongAgents);
                    }
                    wrongInput = false;
                }
            } while (wrongInput);
        }
        Frame frame = new Frame(worlds);
        frame.addAgents(agents);
        return frame;
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
