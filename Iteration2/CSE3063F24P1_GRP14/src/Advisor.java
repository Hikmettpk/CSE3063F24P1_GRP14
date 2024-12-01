import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Advisor extends User {

    @JsonProperty("advisorID")
    private String advisorID;

    @JsonProperty("advisedStudents")
    private List<Student> advisedStudents;

    @JsonProperty("requestedStudents")
    private List<Student> requestedStudents;

    public Advisor(@JsonProperty("username") String username, @JsonProperty("name") String name, @JsonProperty("surname") String surname, @JsonProperty("password") String password, @JsonProperty("advisorID") String advisorID) {
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


    public void rejectRequestedCourse(Student student, Course course) {
        if (student.getRequestedCourses().remove(course)) {
            course.getWaitList().get(1).getRequestedCourses().add(course);
            course.getWaitList().remove(1);

            System.out.println("The course " + course.getCourseName() + " has been rejected for student " + student.getName());
        } else {
            System.out.println("Failed to reject the course. Course might not exist in the requested list.");
        }
    }

    public void approveRequestedCourse(CourseRegistrationSystem courseRegistrationSystem, Student student, Course course) throws IOException {
        if (student.getEnrolledCourses().size() < 5) {
            if (courseRegistrationSystem.removeCourseFromRequestList(student, course)) {  // Proceed only if removal is successful
                courseRegistrationSystem.addToEnrollList(course, student);
                //course.getCurrentCapacity()++; saveToCourseJson() gibi bir metot lazım buraya

                //eğer kapasite dolmuşsa tüm öğrenciler o kursun waitlist'inden kaldırılır.
                if(course.getCurrentCapacity() == course.getEnrollmentCapacity()){
                    for(int i = 0; i < course.getWaitList().size(); i++){
                        course.getWaitList().remove(i);
                    }
                }
            } else {
                System.out.println("Course approval failed as the course was not removed from the request list.");
            }
        }
        else{
            System.out.println("Course approval failed because the enrolled list has its limit.");
        }
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
