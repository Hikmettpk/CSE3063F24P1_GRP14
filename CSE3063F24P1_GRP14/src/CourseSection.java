import com.fasterxml.jackson.annotation.JsonProperty;

class CourseSection {
    @JsonProperty("term")
    private String term;

    @JsonProperty("day")
    private String day;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("semester")
    private int semester;

    @JsonProperty("instructor")
    private String instructor;

    @JsonProperty("enrollmentCapacity")
    private int enrollmentCapacity;

    @JsonProperty("status")
    private boolean status;

    public CourseSection(String term, String instructor, int enrollmentCapacity, boolean status) {
        this.term = term;
        this.instructor = instructor;
        this.enrollmentCapacity = enrollmentCapacity;
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public String getInstructor() {
        return instructor;
    }

    public boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CourseSection{" +
                "term='" + term + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", semester=" + semester +
                ", instructor='" + instructor + '\'' +
                ", enrollmentCapacity=" + enrollmentCapacity +
                ", status='" + status + '\'' +
                '}';
    }



}