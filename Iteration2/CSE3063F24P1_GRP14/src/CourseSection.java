import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class CourseSection {

    @JsonProperty("day")
    private final String day;

    @JsonProperty("place")
    private final String place;

    @JsonProperty("hour")
    private final List<String> hour;

    @JsonProperty("year")
    private final int year;

    @JsonProperty("instructor")
    private final String instructor;

    @JsonProperty("enrollmentCapacity")
    private final int enrollmentCapacity;

    @JsonProperty("status")
    private final String status;

    // Constructor
    public CourseSection(String day, String place, List<String> hour, int year, String instructor, int enrollmentCapacity, String status) {
        this.day = day;
        this.place = place;
        this.hour = hour;
        this.year = year;
        this.instructor = instructor;
        this.enrollmentCapacity = enrollmentCapacity;
        this.status = status;
    }


    // Getters
    public String getDay() {
        return day;
    }

    public String getPlace() {
        return place;
    }

    public List<String> getHour() {
        return hour;
    }

    public int getYear() {
        return year;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    public String getStatus() {
        return status;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "CourseSection{" +
                "place='" + place + '\'' +
                ", day='" + day + '\'' +
                ", hour=" + hour +
                ", year=" + year +
                ", instructor='" + instructor + '\'' +
                ", enrollmentCapacity=" + enrollmentCapacity +
                ", status='" + status + '\'' +
            '}';
}
}