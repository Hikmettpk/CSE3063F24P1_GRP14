import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Transcript {
    @JsonProperty("grades")
    private List<Grade> grades;

    // Getters and setters
    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "grades=" + grades +
                '}';
    }
}
