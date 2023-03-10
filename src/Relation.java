public class Relation {
    
    private World src;
    private World dest;

    public Relation(World src, World dest) {
        this.src = src;
        this.dest = dest;
        src.addOutgoingRelation(this);
        dest.addIngoingRelation(this);
    }

    public World getSrc() {
        return src;
    }

    public World getDest() {
        return dest;
    }

}