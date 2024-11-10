import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade {
    @JsonProperty("course")
    private Course course;

    @JsonProperty("gradeValue")
    private String gradeValue;

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
        return String.format("%-10s %-30s %-10s\n", course.getCourseId(), course.getCourseName(),gradeValue);
    }


}