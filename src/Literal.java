public class Literal extends Formula {
    
    private char literal;

    public Literal(char literal) {
        this.literal = literal;
    }

    public String toString() {
        return "" + literal;
    }

}
