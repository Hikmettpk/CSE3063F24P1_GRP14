import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class Course {

    @JsonProperty("courseId")
    private String courseId;

    @JsonProperty("courseName")
    private String courseName;

    @JsonProperty("credit")
    private int credit;

    @JsonProperty("prerequisite")
    private boolean prerequisite;

    @JsonProperty("prerequisiteLessonId")
    private String prerequisiteLessonId;

    @JsonProperty("courseSection")
    private List<CourseSection> courseSection;

    @JsonProperty("weeklyCourseCount")
    private int weeklyCourseCount;

    @JsonProperty("year")
    private int year;

    @JsonProperty("instructor")
    private String instructor;

    @JsonProperty("enrollmentCapacity")
    private int enrollmentCapacity;

    @JsonProperty("currentCapacity")
    private int currentCapacity;

    @JsonProperty("status")
    private String status;

    public Course() {

    }
    // Constructor
    public Course(String courseId, String courseName, int credit, boolean prerequisite, String prerequisiteLessonId,
                  List<CourseSection> courseSection, int weeklyCourseCount, int year, String instructor, int enrollmentCapacity, int currentCapacity, String status) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLessonId = prerequisiteLessonId;
        this.courseSection = courseSection != null ? courseSection : new ArrayList<>();
        this.weeklyCourseCount = weeklyCourseCount;
        this.year = year;
        this.instructor = instructor;
        this.enrollmentCapacity = enrollmentCapacity;
        this.currentCapacity = currentCapacity;
        this.status = status;
    }

    // Getters
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public boolean hasPrerequisite() {
        return prerequisite;
    }

    public String getPrerequisiteLessonId() {
        return prerequisiteLessonId;
    }

    public List<CourseSection> getCourseSection() {
        return courseSection;
    }

    public int getWeeklyCourseCount() {
        return weeklyCourseCount;
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

    public int getCurrentCapacity() {return currentCapacity;}

    public String getStatus() {
        return status;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", prerequisite=" + prerequisite +
                ", prerequisiteLessonId='" + prerequisiteLessonId + '\'' +
                ", courseSection=" + courseSection +
                ", weeklyCourseCount=" + weeklyCourseCount +
                ", year=" + year +
                ", instructor='" + instructor + '\'' +
                ", enrollmentCapacity=" + enrollmentCapacity +
                ", status='" + status + '\'' +
           '}';
}


}