public class Volunteer {
    //Priority types
    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_MEDIUM= "medium";
    public static final String PRIORITY_LOW = "low";

    //Fields - Attributes of a volunteer
    private String volunteerId;
    private String name;
    private String taskAssigned;
    private String priorityLevel;
    private Boolean assigned;

    //Overloaded constructor - used to take user inputs and assign them to the attributes
    public Volunteer(String volunteerId, String name, String taskAssigned, String priorityLevel) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.taskAssigned = taskAssigned;
        this.priorityLevel = priorityLevel;
    }

    //Getters
    public String getVolunteerId() {return volunteerId;}
    public String getName() {return name;}
    public  String getTaskAssigned() {return taskAssigned;}
    public String getPriorityLevel() {return priorityLevel;}

    //Setters
    public void setVolunteerId(String volunteerId) {this.volunteerId = volunteerId;}
    public void setName(String name) {this.name = name;}
    public void setTaskAssigned(String taskAssigned) {this.taskAssigned = taskAssigned;}
    public  void setPriorityLevel(String priorityLevel) {this.priorityLevel = priorityLevel;}
}
