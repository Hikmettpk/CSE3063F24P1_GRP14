import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class Student extends User{
    @JsonProperty("studentID")
    private String studentID;

    @JsonProperty("enrolledCourses")
    private List<Course> enrolledCourses;

    @JsonProperty("requestedCourses")
    private List<Course> requestedCourses;

    @JsonProperty("transcript")
    private Transcript transcript;
    @JsonProperty("advisor")
    private Advisor advisor;

  // @JsonProperty("grade")
  // private Grade grade;

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

    public String getStudentID() {
        return studentID;
    }



    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }
    public Transcript getTranscript() {
        return transcript;
    }



    //listAvailableCourses metodu yazılacak
    // toString() method
    @Override
    public String toString() {
        return "Student{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", studentID='" + studentID + '\'' +
                ", enrolledCourses=" + enrolledCourses +
                ", requestedCourses=" + requestedCourses +
                ", transcript=" + transcript +
                '}';
    }



}
