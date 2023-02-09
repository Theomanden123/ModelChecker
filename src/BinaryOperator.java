public class BinaryOperator extends Formula {

    private Formula left, right;

    public BinaryOperator(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }

    public Formula getLeft() {
        return left;
    }

    public Formula getRight() {
        return right;
    }

    public String toString() {
        return operator + "(" + left + "," + right + ")";
    }
    
}
