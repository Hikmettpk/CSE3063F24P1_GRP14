import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Advisor extends User {

    JSONMethods jsonMethods = new JSONMethods();
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

    public void approveRequestedCourse(CourseRegistrationSystem crs, Student student, Course course) {
        if (!student.getRequestedCourses().contains(course)) {
            System.out.println("Course not requested by the student.");
            return;
        }

        try {
            // Öğrenci JSON'dan yükleniyor
            Student updatedStudent = jsonMethods.loadStudent(student.getStudentID());
            if (updatedStudent == null) {
                System.err.println("Failed to load student data from JSON.");
                return;
            }

            // Ders JSON'dan yükleniyor
            List<Course> allCourses = jsonMethods.loadAllCourses();
            Course fullCourse = allCourses.stream()
                    .filter(c -> c.getCourseId().equals(course.getCourseId()))
                    .findFirst()
                    .orElse(null);

            if (fullCourse == null) {
                System.err.println("Failed to load course data from JSON.");
                return;
            }

            // Enrolled Courses'a kurs ekleniyor (section bilgileri dahil)
            updatedStudent.getEnrolledCourses().add(fullCourse);

            // Requested Courses listesinden kurs kaldırılıyor
            updatedStudent.getRequestedCourses().removeIf(c -> c.getCourseId().equals(course.getCourseId()));

            // Öğrenci JSON'u güncelleniyor
            jsonMethods.updateStudentInJson(updatedStudent);

            // Console çıktısı
            System.out.println("Course '" + fullCourse.getCourseName() + "' approved successfully for " + updatedStudent.getName());

            // Öğrenci nesnesini güncel tut
            student.getEnrolledCourses().clear();
            student.getEnrolledCourses().addAll(updatedStudent.getEnrolledCourses());
            student.getRequestedCourses().clear();
            student.getRequestedCourses().addAll(updatedStudent.getRequestedCourses());

        } catch (IOException e) {
            System.err.println("Error during course approval: " + e.getMessage());
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
