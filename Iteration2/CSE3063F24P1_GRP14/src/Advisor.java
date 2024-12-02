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
            // JSON'dan öğrenci ve kurs yükleme
            Student updatedStudent = jsonMethods.loadStudent(student.getStudentID());
            Course updatedCourse = jsonMethods.loadCourseFromJson(course.getCourseId());

            if (updatedStudent == null || updatedCourse == null) {
                System.err.println("Failed to load student or course data from JSON.");
                return;
            }

            // Yeni Course nesnesi (kapasite artırımı dahil)
            Course newCourse = new Course(
                    updatedCourse.getCourseId(),
                    updatedCourse.getCourseName(),
                    updatedCourse.getCredit(),
                    updatedCourse.hasPrerequisite(),
                    updatedCourse.getPrerequisiteLessonId(),
                    updatedCourse.getCourseSection(),
                    updatedCourse.getWeeklyCourseCount(),
                    updatedCourse.getYear(),
                    updatedCourse.getInstructor(),
                    updatedCourse.getEnrollmentCapacity(),
                    updatedCourse.getCurrentCapacity() + 1, // Kapasite artırılıyor
                    updatedCourse.getStatus(),
                    updatedCourse.getWaitList()
            );

            // Yeni Student nesnesi oluşturma
            Student newStudent = new Student(
                    updatedStudent.getUsername(),
                    updatedStudent.getName(),
                    updatedStudent.getSurname(),
                    updatedStudent.getPassword(),
                    updatedStudent.getStudentID(),
                    updatedStudent.getTranscript(),
                    updatedStudent.getAdvisor()
            );

            // Yeni öğrencinin enrolled ve requested kurs listelerini doldurma
            newStudent.getEnrolledCourses().addAll(updatedStudent.getEnrolledCourses());
            newStudent.getEnrolledCourses().add(newCourse);

            newStudent.getRequestedCourses().addAll(updatedStudent.getRequestedCourses());

            // RequestedCourses'dan kursu doğru şekilde kaldır
            crs.removeCourseFromRequestList(newStudent, course);

            // JSON dosyalarını güncelle
            jsonMethods.updateCourseInJson(newCourse);
            jsonMethods.updateStudentInJson(newStudent);

            // Bellekteki öğrenci nesnesini güncel tut
            student.getEnrolledCourses().clear();
            student.getEnrolledCourses().addAll(newStudent.getEnrolledCourses());
            student.getRequestedCourses().clear();
            student.getRequestedCourses().addAll(newStudent.getRequestedCourses());

            System.out.println("Course '" + newCourse.getCourseName() + "' approved successfully for " + updatedStudent.getName());

        } catch (IOException e) {
            System.err.println("Error during course approval: " + e.getMessage());
        }
    }





    public void rejectRequestedCourse(Student student, Course course) throws IOException {
        // Öğrencinin talep ettiği kursu listeden çıkarıyoruz
        if (student.getRequestedCourses().remove(course)) {
            // Eğer waitList boş değilse, waitList'teki ilk öğrenciye kursu veriyoruz
            if (!course.getWaitList().get(1).isEmpty()) { //?
                // waitList'teki ilk öğrencinin ID'sini alıyoruz
                //1 olmasının sebebi 0. index boş "".
                String firstStudentId = course.getWaitList().get(1);

                // Öğrenciyi ID ile JSON'dan yüklüyoruz
                Student waitListStudent = jsonMethods.loadStudent(firstStudentId);
                if (waitListStudent != null) {
                    // waitList'teki öğrenciye course'u ekliyoruz
                    waitListStudent.getRequestedCourses().add(course);

                    // Öğrenci nesnesini güncelliyoruz
                    jsonMethods.updateStudentInJson(waitListStudent);

                    // waitList'ten ilk öğrenciyi çıkarıyoruz
                    course.getWaitList().remove(1);

                    // Güncellenen kursu JSON'a yazıyoruz
                    jsonMethods.updateCourseInJson(course);
                }
            }
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
