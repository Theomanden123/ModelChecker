import java.util.ArrayList;

// Parser.java handle String input conversation into logical objects.

public class Parser {

    public static Formula getFormulaFromString(Frame frame, String input) {

        int index1 = input.indexOf("(");
        int index2 = input.indexOf("[");

        String operator = "";
        String argument = "";
        String[] arguments = null;
        Agent agent = null;
        ArrayList<Agent> group = new ArrayList<Agent>();

        Formula announcementFormula = null;
        if (index2 != -1 && index2 < index1) {
            int index3 = input.indexOf("]");
            String announcementString = input.substring(index2 + 2, index3);
            announcementFormula = getFormulaFromString(frame, announcementString);
            argument = input.substring(index3+2, input.length());
            operator = "!";
        } else {
            if (index1 == -1) {
                Formula formula;
                if (input.contains("Verum")) {
                    formula = new Verum();
                } else if (input.contains("Falsum")) {
                    formula = new Falsum();
                } else {
                    formula = new Literal(input.charAt(0));
                }
                
                return formula;
            }

            operator = input.substring(0, index1);
            argument = input.substring(index1 + 1, input.length());
            arguments = getArguments(argument);

            if (operator.charAt(0) == 'K') {
                String name = operator.substring(1, operator.length());
                agent = frame.getAgent(name);
                operator = "" + operator.charAt(0);
            }

            if (operator.charAt(0) == 'E' && operator.charAt(1) == '{'|| 
                operator.charAt(0) == 'C' && operator.charAt(1) == '{'|| 
                operator.charAt(0) == 'D' && operator.charAt(1) == '{') {

                String rest = operator.substring(2, operator.length() - 1);
                String[] names = rest.split(",");

                for (String name : names) {
                    agent = frame.getAgent(name);
                    group.add(agent);
                }

                operator = "" + operator.charAt(0);
            }
        }

        Formula formula = null;

        switch (operator) {

        case "Not":
            Formula f = getFormulaFromString(frame, argument);
            if (f instanceof Not) {
                Not form = (Not) f;
                formula = form.getFormula();
            } else {
                formula = new Not(getFormulaFromString(frame, argument));
            }
            break;

        case "And":
            formula = new And(getFormulaFromString(frame, arguments[0]),
                              getFormulaFromString(frame, arguments[1]));
            break;

        case "Or":
            formula = new Or(getFormulaFromString(frame, arguments[0]),
                             getFormulaFromString(frame, arguments[1]));
            break;

        case "Imp":
            formula = new Imp(getFormulaFromString(frame, arguments[0]),
                              getFormulaFromString(frame, arguments[1]));
            break;

        case "Equiv":
            formula = new Equiv(getFormulaFromString(frame, arguments[0]),
                                getFormulaFromString(frame, arguments[1]));
            break;

        case "Diamond":
            formula = new Diamond(getFormulaFromString(frame, argument));
            break;

        case "Box":
            formula = new Box(getFormulaFromString(frame, argument));
            break;

        case "K":
            formula = new Knowledge(agent, getFormulaFromString(frame, argument));
            break;

        case "E":
            formula = new Everybody(group, getFormulaFromString(frame, argument));
            break;
    
        case "C":
            formula = new Common(group, getFormulaFromString(frame, argument));
            break;

        case "D":
            formula = new Distributed(group, getFormulaFromString(frame, argument));
            break;

        case "!":
            formula = new Announcement(getFormulaFromString(frame, argument), announcementFormula);
            break;
        }

        return formula;

    }

    private static String[] getArguments(String input) {

        String[] output = new String[2];
        int parenthesisCount = -1;

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == '(') {
                if (parenthesisCount == -1) {
                    parenthesisCount = 1;
                } else {
                    parenthesisCount++;
                }
            } else if (input.charAt(i) == ')') {
                parenthesisCount--;
            } else if (input.charAt(i) == ',' && parenthesisCount == -1) {
                output[0] = input.substring(0, i);
                output[1] = input.substring(i + 1, input.length());
                break;
            }

            if (parenthesisCount == 0) {
                output[0] = input.substring(0, i + 1);
                output[1] = input.substring(i + 2, input.length());
                break;
            }
        }

        return output;

    }

}
