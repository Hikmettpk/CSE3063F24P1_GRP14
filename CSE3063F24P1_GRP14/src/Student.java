import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    //constructor
    public Student(String username, String name, String surname, String password, String studentID,
                   Transcript transcript, Advisor advisor) {
        super(username, name, surname, password);
        this.requestedCourses = new ArrayList<>();
        this.enrolledCourses = new ArrayList<>();
        this.advisor = advisor;
        this.transcript = transcript;
        this.studentID = studentID;
    }

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

    public Advisor getAdvisor(){
        return advisor;
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
