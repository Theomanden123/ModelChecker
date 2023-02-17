public class Formula {

    protected String operator;

    public Formula() {

    }

    @Override
    public boolean equals(Object obj) {
        Formula f = (Formula) obj;
        if (this.toString().equals(f.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    
}
