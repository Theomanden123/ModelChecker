import java.util.ArrayList;

public class Parser {

    public static Formula getFormulaFromString(String input) {

        int index = input.indexOf("(");

        if (index == -1) {
            Literal literal = new Literal(input.charAt(0));
            return literal;
        }

        String operator = input.substring(0, index);
        String argument = input.substring(index + 1, input.length());
        String[] arguments = getArguments(argument);

        Formula formula = null;

        switch (operator) {

        case "Not":
            Formula f = getFormulaFromString(argument);
            if (f instanceof Not) {
                Not form = (Not) f;
                formula = form.getFormula();
            } else {
                formula = new Not(getFormulaFromString(argument));
            }
            break;

        case "And":
            formula = new And(getFormulaFromString(arguments[0]),
                              getFormulaFromString(arguments[1]));
            break;

        case "Or":
            formula = new Or(getFormulaFromString(arguments[0]),
                             getFormulaFromString(arguments[1]));
            break;

        case "Imp":
            formula = new Imp(getFormulaFromString(arguments[0]),
                              getFormulaFromString(arguments[1]));
            break;

        case "Equiv":
            formula = new Equiv(getFormulaFromString(arguments[0]),
                                getFormulaFromString(arguments[1]));
            break;

        case "Diamond":
            formula = new Diamond(getFormulaFromString(argument));
            break;

        case "Box":
            formula = new Box(getFormulaFromString(argument));
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
