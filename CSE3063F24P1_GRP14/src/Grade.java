import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade extends Transcript {

    @JsonProperty("course")
    private Course course;

    @JsonProperty("gradeValue")
    private String gradeValue;

    public Grade(Course course, String gradeValue){
        this.gradeValue = gradeValue;
        this.course = course;
    }

    // Getters and setters
    public Course getCourse() {
        return course;
    }

    public String getGradeValue(){
        return gradeValue;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "course=" + course +
                ", gradeValue='" + gradeValue + '\'' +
                '}';
    }


}