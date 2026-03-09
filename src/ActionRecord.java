public class ActionRecord {
    //Fields - Attributes
    private String actionType;
    private Volunteer volunteer;

    //Constructor
    public ActionRecord(String actionType, Volunteer volunteer) {
        this.actionType = actionType;
        this.volunteer = volunteer;
    }

    //getter
    public String getActionType() {return this.actionType;}
    public Volunteer getVolunteer() {return this.volunteer;}

}
