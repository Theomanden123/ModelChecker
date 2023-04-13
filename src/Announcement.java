public class Announcement extends UnaryOperator {

    private Formula announcement;

    public Announcement(Formula formula, Formula announcement) {
        super(formula);
        this.announcement = announcement;
        operator = "Announcement";
    }

    public Formula getAnnouncement() {
        return announcement;
    }


}