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
    public Course(String courseId, String courseName, int credit, boolean prerequisite,
                  String prerequisiteLessonId, CourseSection courseSection) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.prerequisite = prerequisite;
        this.prerequisiteLessonId = prerequisiteLessonId;
        this.courseSection = courseSection;
    }


    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean hasPrerequisite() {
        return prerequisite;
    }

    public int getCredit() {
        return credit;
    }

    public CourseSection getCourseSection() {
        return courseSection;
    }

    public String getPrerequisiteLessonId() {
        return prerequisiteLessonId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", prerequisite=" + prerequisite +
                ", prerequisiteLessonID='" + prerequisiteLessonId + '\'' +
                '}';
    }

}


