public class Agent {

    private String name;

    public Agent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        Agent a = (Agent) obj;
        if (this.toString().equals(a.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
