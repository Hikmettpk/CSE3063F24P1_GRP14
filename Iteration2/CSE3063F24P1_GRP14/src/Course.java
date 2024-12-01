import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class Course {

    @JsonProperty("courseId")
    private final String courseId;

    @JsonProperty("currentCapacity")
    private int currentCapacity;

    @JsonProperty("courseName")
    private final String courseName;

    @JsonProperty("credit")
    private final int credit;

    @JsonProperty("prerequisite")
    private final boolean prerequisite;

    @JsonProperty("prerequisiteLessonId")
    private final String prerequisiteLessonId;

    @JsonProperty("courseSection")
    private final List<CourseSection> courseSection;

    @JsonProperty("weeklyCourseCount")
    private final int weeklyCourseCount;

    @JsonProperty("year")
    private final int year;

    @JsonProperty("instructor")
    private final String instructor;

    @JsonProperty("enrollmentCapacity")
    private final int enrollmentCapacity;

    @JsonProperty("status")
    private final String status;

    private List<Student> waitList = new ArrayList<>();

    // Constructor
    public Course(String courseId, String courseName, int credit, boolean prerequisite, String prerequisiteLessonId,
                  List<CourseSection> courseSection, int weeklyCourseCount, int year, String instructor, int enrollmentCapacity, String status
                    , int currentCapacity) {
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
        this.status = status;
        this.currentCapacity = currentCapacity;
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

    public String getStatus() {
        return status;
    }

    public int getCurrentCapacity(){return currentCapacity;}

    public List<Student> getWaitList(){return waitList;}

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
                ", current capacity='" + currentCapacity + '\'' +
           '}';
}
}