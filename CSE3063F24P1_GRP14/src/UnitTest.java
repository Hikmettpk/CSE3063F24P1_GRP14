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
    void testListAvailableCourses() {
        // Given: Student has passed Programming 1 with CB and Circuits with CC

        // When: listAvailableCourses() çağrılır
        List<Course> availableCourses = registrationSystem.listAvailableCourses();

        // Then: Data Structures dersi alınabilir olmalı çünkü prerequisite (Programming 1) geçilmiş
        assertTrue(availableCourses.contains(dataStructuresCourse));

        // Programming 1 ve Circuits dersleri zaten alındığı için tekrar alınamaz (CB ve CC ile geçilmiş)
        assertFalse(availableCourses.contains(programmingCourse));
        assertFalse(availableCourses.contains(circuitsCourse));
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
    void testAddToEnrollList() throws IOException {
        // Given: Öğrenci henüz hiç derse kayıtlı değil
        assertTrue(student.getEnrolledCourses().isEmpty());

        // When: Yeni bir derse kayıt yapılır
        registrationSystem.addToEnrollList(dataStructuresCourse, student);

        // Then: Kayıtlı dersler listesinde yeni ders olmalı
        assertTrue(student.getEnrolledCourses().contains(dataStructuresCourse));
        assertEquals(1, student.getEnrolledCourses().size());
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
    void testRemoveCourseFromEnrolledList() throws IOException {
        // Given: Öğrenci bir derse kayıtlı
        registrationSystem.addToEnrollList(dataStructuresCourse, student);
        assertTrue(student.getEnrolledCourses().contains(dataStructuresCourse));

        // When: Kayıtlı ders listeden çıkarılır
        registrationSystem.removeCourseFromEnrolledList(student, dataStructuresCourse);

        // Then: Ders kayıtlı dersler listesinden kaldırılmış olmalı
        assertFalse(student.getEnrolledCourses().contains(dataStructuresCourse));
        
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

}
