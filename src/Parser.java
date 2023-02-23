import java.util.ArrayList;

public class Parser {

    public static Formula getFormulaFromString(Frame frame, String input) {

        int index = input.indexOf("(");

        if (index == -1) {
            Literal literal = new Literal(input.charAt(0));
            return literal;
        }

        String operator = input.substring(0, index);
        String argument = input.substring(index + 1, input.length());
        String[] arguments = getArguments(argument);

        if (operator.charAt(0) == 'K') {

            ArrayList<Agent> agents = new ArrayList<Agent>();

            if (operator.charAt(1) == '<') {

                String rest = operator.substring(2, operator.length() - 1);

                String[] agentNames = rest.split(",");



            } else {

            }

            operator = "" + operator.charAt(0);

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
