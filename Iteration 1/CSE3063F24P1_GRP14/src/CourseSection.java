import com.fasterxml.jackson.annotation.JsonProperty;

class CourseSection {

    @JsonProperty("day")
    private String day;

    @JsonProperty("place")
    private String place;

    @JsonProperty("hour")
    private String hour;

    @JsonProperty("year")
    private int year;

    @JsonProperty("instructor")
    private String instructor;

    @JsonProperty("enrollmentCapacity")
    private int enrollmentCapacity;

    @JsonProperty("status")
    private String status;

    // Getters
    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public CourseSection() {}


    public CourseSection(String sanemArslan, int i, String active) {
        day = sanemArslan;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "CourseSection{" +
                "place='" + place + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", year=" + year +
                ", instructor='" + instructor + '\'' +
                ", enrollmentCapacity=" + enrollmentCapacity +
                ", status='" + status + '\'' +
                '}';
    }

}