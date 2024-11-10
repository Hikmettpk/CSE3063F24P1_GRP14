import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnitTest {
    private CourseRegistrationSystem registrationSystem;
    private Student student;
    private Advisor advisor;
    private List<Course> courses;
    private Course programmingCourse;
    private Course circuitsCourse;
    private Course dataStructuresCourse;
    private Transcript transcript;
    private JSONMethods jsonMethods;

    @BeforeEach
    void setUp() {
        // 1. Önce test verileri oluşturuyoruz
        // Programming 1 dersi
        CourseSection programmingSection = new CourseSection("fall", "Sanem Arslan", 5, "Active");
        programmingCourse = new Course("CSE1241", "Programming 1", 6, false, "None", programmingSection);

        // Electric Circuits dersi
        CourseSection circuitsSection = new CourseSection("fall", "Borahan Tümer", 5, "Active");
        circuitsCourse = new Course("EE2031", "Electric Circuits", 5, false, "None", circuitsSection);

        // Data Structures dersi (Programming 1 prerequisite)
        CourseSection dsSection = new CourseSection("spring", "Ali Veli", 5, "Active");
        dataStructuresCourse = new Course("CSE2225", "Data Structures", 6, true, "CSE1241", dsSection);

        // Transcript oluşturma
        List<Grade> grades = new ArrayList<>();
        grades.add(new Grade(programmingCourse, "CB"));
        grades.add(new Grade(circuitsCourse, "CC"));
        transcript = new Transcript(grades);

        // Student oluşturma
        student = new Student("o150121065", "Azra", "ÇetinTürk", "mar21065", "150121065", transcript, null);

        // Advisor nesnesi oluşturuluyor
        advisor = new Advisor("advisor123", "Ali", "Veli", "password123", "A123");

        // Courses listesi oluşturma
        courses = new ArrayList<>();
        courses.add(programmingCourse);
        courses.add(circuitsCourse);
        courses.add(dataStructuresCourse);

        // Registration system oluşturma
        registrationSystem = new CourseRegistrationSystem(student, courses);
    }



    @Test
    void testRequestInCourse() throws IOException {
        // Given: Öğrenci henüz hiç ders talep etmemiş
        assertTrue(student.getRequestedCourses().isEmpty());

        // When: Yeni bir ders talebi yapılır
        registrationSystem.requestInCourse(dataStructuresCourse, student);

        // Then: Talep edilen dersler listesinde yeni ders olmalı
        assertTrue(student.getRequestedCourses().contains(dataStructuresCourse));
        assertEquals(1, student.getRequestedCourses().size());
    }



    @Test
    void testRemoveCourseFromRequestList() throws IOException {
        // Given: Öğrenci bir ders talep etmiş durumda
        registrationSystem.requestInCourse(dataStructuresCourse, student);
        assertTrue(student.getRequestedCourses().contains(dataStructuresCourse));

        // When: Talep edilen ders listeden çıkarılır
        boolean result = registrationSystem.removeCourseFromRequestList(student, dataStructuresCourse);

        // Then: İşlem başarılı olmalı ve ders listeden kaldırılmış olmalı
        assertTrue(result);
        assertFalse(student.getRequestedCourses().contains(dataStructuresCourse));
    }



    @Test
    void testTranscriptToString() {
        // Expected çıktı manuel olarak yazılıyor
        String expected = String.format("%-10s %-30s %-10s\n", "Course ID", "Course Name", "Grade Value")
                + "CSE1241    Programming 1                  CB        \n"  // Programming 1 dersi
                + "EE2031     Electric Circuits              CC        \n"; // Electric Circuits dersi

        // Gerçek toString çıktısını alıyoruz
        String actual = transcript.toString();

        // Expected ve actual değerlerinin eşit olduğunu kontrol ediyoruz
        assertEquals(expected, actual);
    }

    @Test
    void testAdvisorToString() {

        // Advisor'ın advisedStudents listesine öğrenci ekleniyor
        advisor.getAdvisedStudents().add(student);

        // Beklenen toString çıktısını oluşturuyoruz
        String expected = "Advisor{" +
                "username='advisor123', " +
                "name='Ali', " +
                "surname='Veli', " +
                "advisorID='A123', " +
                "advisedStudents=[" +
                "Student{username='o150121065', name='Azra', surname='ÇetinTürk', " +
                "studentID='150121065', " +
                "enrolledCourses=[], " +
                "requestedCourses=[], " +
                "transcript=Course ID  Course Name                    Grade Value\n" +
                "CSE1241    Programming 1                  CB        \n" +
                "EE2031     Electric Circuits              CC        \n" +
                "}]}";

        // Gerçek toString çıktısını alıyoruz
        String actual = advisor.toString();

        // Beklenen ve gerçek çıktıyı karşılaştırıyoruz
        assertEquals(expected, actual);
    }



    @Test
    void testCourseToString() {
        // 1. Test verilerini hazırlama
        CourseSection courseSection = new CourseSection("fall", "Sanem Arslan", 5, "Active");
        Course course = new Course("CSE1241", "Programming 1", 6, false, "None", courseSection);

        // 2. Beklenen çıktıyı tanımlama
        String expected = "Course{" +
                "courseId='CSE1241'" +
                ", courseName='Programming 1'" +
                ", credit=6" +
                ", prerequisite=false" +
                ", prerequisiteLessonId='None'" +
                ", courseSection=" + courseSection +
                '}';

        // 3. Metodu çağırma ve sonucu beklenen ile karşılaştırma
        assertEquals(expected, course.toString());
    }

    @Test
    void testToString() {
        // Beklenen çıktıyı tanımlayın.
        String expected = "Student{" +
                "username='o150121065', " +
                "name='Azra', " +
                "surname='ÇetinTürk', " +
                "studentID='150121065', " +
                "enrolledCourses=" + student.getEnrolledCourses() +
                ", requestedCourses=" + student.getRequestedCourses() +
                ", transcript=" + transcript +
                '}';

        // toString metodunu test edin.
        assertEquals(expected, student.toString());
    }


}