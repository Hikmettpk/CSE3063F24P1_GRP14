import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade {
    @JsonProperty("course")
    private Course course;

    @JsonProperty("gradeValue")
    private String gradeValue;

    public Grade(Course course, String gradeValue) {
        this.course = course;
        this.gradeValue = gradeValue;
    }
    public Grade() {}
    // Getters and setters
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "course=" + course +
                ", gradeValue='" + gradeValue + '\'' +
                '}';
    }


}