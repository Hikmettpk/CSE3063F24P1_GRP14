import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class Advisor extends User {

    @JsonProperty("advisorID")
    private String advisorID;

    @JsonProperty("advisedStudents")
    private List<String> advisedStudents;

    @JsonProperty("requestedStudents")
    private List<String> requestedStudents;

    // Getters and setters
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }



    @Override
    public String getPassword() {
        return super.getPassword();
    }



    public String getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(String advisorID) {
        this.advisorID = advisorID;
    }

    public List<String> getAdvisedStudents() {
        return advisedStudents;
    }

    public void setAdvisedStudents(List<String> advisedStudents) {
        this.advisedStudents = advisedStudents;
    }

    // toString() method
    @Override
    public String toString() {
        return "Advisor{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", advisorID='" + advisorID + '\'' +
                ", advisedStudents=" + advisedStudents +
                '}';
    }
}
