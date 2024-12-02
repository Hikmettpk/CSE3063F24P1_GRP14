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


    public void approveRequestedCourse(Student student, Course course) {
        try {
            // Requested Courses listesinden kursu kaldır
            boolean removed = student.getRequestedCourses().removeIf(c -> c.getCourseId().equals(course.getCourseId()));

            if (!removed) {
                System.out.println("The course was not found in the requested list.");
                return;
            }

            // Enrolled Courses listesinde kursun zaten mevcut olup olmadığını kontrol et

            // Tüm kursları JSON'dan yükle ve tam kurs bilgilerini bul
            List<Course> allCourses = jsonMethods.loadAllCourses();
            Course fullCourse = allCourses.stream()
                    .filter(c -> c.getCourseId().equals(course.getCourseId()))
                    .findFirst()
                    .orElse(null);

            if (fullCourse == null) {
                System.err.println("Full course data could not be found.");
                return;
            }

            // Enrolled Courses listesine tam kurs bilgilerini ekle
            student.getEnrolledCourses().add(fullCourse);

            // Öğrenci bilgilerini JSON dosyasına güncelle
            jsonMethods.updateStudentInJson(student);

            System.out.println("Course approved successfully for " + student.getName());
        } catch (Exception e) {
            System.err.println("Error approving the course: " + e.getMessage());
        }
    }










    public void rejectRequestedCourse(Student student, Course course) {
        if (student.getRequestedCourses().remove(course)) {
            System.out.println("The course " + course.getCourseName() + " has been rejected for student " + student.getName());
        } else {
            System.out.println("Failed to reject the course. Course might not exist in the requested list.");
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
