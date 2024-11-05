import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Transcript extends Student {

    @JsonProperty("grades")
    private List<Grade> grades;

    public Transcript(){

    };

    public Transcript(List<Grade> grades) {
        super();
        this.grades = grades;
    }

    // Getters and setters
    public List<Grade> getGrades() {
        return grades;
    }

    public String getGradeOfCourse(Course course){
        for(int i = 0; i < grades.size(); i++){
            if(grades.get(i).getCourse() == course){
                return grades.get(i).getGradeValue();
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Transcript{" +
                "grades=" + grades +
                '}';
    }
}
