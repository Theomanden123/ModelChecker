public class Not extends UnaryOperator {
    
    public Not(Formula formula) {
        super(formula);
        if (formula instanceof Not) {
            System.out.println("HIT! Special not!");
            Not f = (Not) formula;
            this.setFormula(f.getFormula());
        }
        operator = "Not";
    }


}
