import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class Advisor extends User {

    @JsonProperty("advisorID")
    private String advisorID;

    @JsonProperty("advisedStudents")
    private List<Student> advisedStudents;

    @JsonProperty("requestedStudents")
    private List<Student> requestedStudents;

    //constructor
    public Advisor(String username, String name, String surname, String password, String advisorID) {
        super(username, name, surname, password);
        this.advisedStudents = new ArrayList<>();
        this.requestedStudents = new ArrayList<>();
        this.advisorID = advisorID;
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

    public String getAdvisorID() {
        return advisorID;
    }

    public List<Student> getAdvisedStudents() {
        return advisedStudents;
    }

    public List<Student> getRequestedStudents(){
        return requestedStudents;
    }

    private void addCoursetoEnrollList(Student student, Course course){
        if(student.getEnrolledCourses().size() < 5){
            student.getEnrolledCourses().add(course);
        }
        else{
            System.out.println("You cannot take more course. You have your enroll limit.");
        }
    }

    private void removeCourseFromRequestList(Student student, Course course){
        if(student.getRequestedCourses().size() > 0 && student.getRequestedCourses().contains(course)){
            student.getRequestedCourses().remove(course);
        }
        else{ //düzeltin burayı ammmk
            System.out.println("You did not take any course. Firstly, please request at least one course.");
        }
    }

    public void approveRequestedCourse(Student student, Course course){
        addCoursetoEnrollList(student, course);
        removeCourseFromRequestList(student, course);
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
