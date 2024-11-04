import com.fasterxml.jackson.annotation.JsonProperty;

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
    private CourseSection courseSection;

    // Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public CourseSection getCourseSection() {
        return courseSection;
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
                '}';
    }

}


