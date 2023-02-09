public class UnaryOperator extends Formula {

    private Formula formula;

    public UnaryOperator(Formula formula) {
        this.formula = formula;
    }

    public Formula getFormula() {
        return formula;
    }

    public String toString() {
        return operator + "(" + formula + ")";
    }

}