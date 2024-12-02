import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONMethods {

    private static final String SCHEDULER_JSON_PATH = "CSE3063F24P1_GRP14/src/resources/department_scheduler.json";

    public List<Course> loadAllCourses() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = JSONMethods.class.getClassLoader().getResourceAsStream("./resources/course.json");
            return List.of(objectMapper.readValue(inputStream, Course[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Return empty list in case of error
        }
    }

    public DepartmentScheduler loadDepartmentScheduler() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(SCHEDULER_JSON_PATH);

            // JSON dosyasını okuyarak DepartmentScheduler nesnesini döndürüyoruz
            return objectMapper.readValue(file, DepartmentScheduler.class);
        } catch (IOException e) {
            System.err.println("Error loading department scheduler from JSON: " + e.getMessage());
            return null;
        }
    }

    public Student loadStudent(String studentId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourcePath = "./resources/Students/" + studentId + ".json";
        InputStream inputStream = JSONMethods.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        return objectMapper.readValue(inputStream, Student.class);
    }


    public Advisor loadAdvisor(String advisorId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourcePath = "./resources/Advisors/" + advisorId + ".json";
        InputStream inputStream = JSONMethods.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        return objectMapper.readValue(inputStream, Advisor.class);
    }

    private final String STUDENT_JSON_PATH = "CSE3063F24P1_GRP14/src/resources/Students/";

    public List<Advisor> loadAllAdvisors() throws IOException {
        File advisorsDir = new File("CSE3063F24P1_GRP14/src/resources/Advisors/");
        List<Advisor> advisors = new ArrayList<Advisor>();

        // Check if directory exists and list files
        if (advisorsDir.exists() && advisorsDir.isDirectory()) {
            File[] advisorFiles = advisorsDir.listFiles();
            if (advisorFiles != null) {
                for (File file : advisorFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        String fileName = file.getName();
                        String advisorId = fileName.substring(0, fileName.indexOf('.'));
                        advisors.add(loadAdvisor(advisorId));
                    }
                }

            }
        } else {
            System.out.println("The directory does not exist or is not a directory.");

        }
        return advisors;

    }

    public List<Student> loadAllStudents() throws IOException {
        File studentsDir = new File("CSE3063F24P1_GRP14/src/resources/Students/");
        List<Student> students = new ArrayList<Student>();

        // Check if directory exists and list files
        if (studentsDir.exists() && studentsDir.isDirectory()) {
            File[] studentFiles = studentsDir.listFiles();
            if (studentFiles != null) {
                for (File file : studentFiles) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        String fileName = file.getName();
                        String studentId = fileName.substring(0, fileName.indexOf('.'));
                        students.add(loadStudent(studentId));
                    }
                }

            }
        } else {
            System.out.println("The directory does not exist or is not a directory.");

        }
        return students;

    }


    public void saveStudentToFile(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String studentFileName = STUDENT_JSON_PATH + student.getStudentID() + ".json";
        mapper.writeValue(new File(studentFileName), student);
    }

    public void updateStudentInJson(Student updatedStudent) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File studentFile = new File("CSE3063F24P1_GRP14/src/resources/Students/" + updatedStudent.getStudentID() + ".json");

            // JSON dosyasından mevcut öğrenciyi yükle
            Student existingStudent = null;
            if (studentFile.exists()) {
                existingStudent = objectMapper.readValue(studentFile, Student.class);
            }

            // Eğer mevcut öğrenci varsa, gerekli alanları güncelle
            if (existingStudent != null) {
                List<Course> updatedEnrolledCourses = new ArrayList<>(existingStudent.getEnrolledCourses());
                List<Course> updatedRequestedCourses = new ArrayList<>(existingStudent.getRequestedCourses());

                // EnrolledCourses'u güncelle
                for (Course course : updatedStudent.getEnrolledCourses()) {
                    if (!updatedEnrolledCourses.contains(course)) {
                        updatedEnrolledCourses.add(course);
                    }
                }

                // RequestedCourses'u güncelle
                for (Course course : updatedStudent.getRequestedCourses()) {
                    if (!updatedRequestedCourses.contains(course)) {
                        updatedRequestedCourses.add(course);
                    }
                }

                // Yeni listeyi mevcut öğrenci nesnesine yerleştir
                existingStudent.getEnrolledCourses().clear();
                existingStudent.getEnrolledCourses().addAll(updatedEnrolledCourses);

                existingStudent.getRequestedCourses().clear();
                existingStudent.getRequestedCourses().addAll(updatedRequestedCourses);
            } else {
                existingStudent = updatedStudent;
            }

            // JSON'a yaz
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(studentFile, existingStudent);
            System.out.println("Student JSON updated successfully!");

        } catch (IOException e) {
            System.err.println("Error updating student JSON: " + e.getMessage());
        }
    }


    public Course loadCourseFromJson(String courseId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File coursesFile = new File("CSE3063F24P1_GRP14/src/resources/course.json");
            List<Course> courses = Arrays.asList(objectMapper.readValue(coursesFile, Course[].class));
            for (Course course : courses) {
                if (course.getCourseId().equals(courseId)) {
                    return course;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading course from JSON: " + e.getMessage());
        }
        return null;
    }

    public void updateCourseInJson(Course updatedCourse) {
        try {
            // ObjectMapper ve dosya tanımlamaları
            ObjectMapper objectMapper = new ObjectMapper();
            File courseFile = new File("CSE3063F24P1_GRP14/src/resources/course.json");

            // Mevcut JSON dosyasını listeye çevir
            List<Course> courses = objectMapper.readValue(courseFile, new TypeReference<List<Course>>() {});

            // Güncellenen kursu bul ve değiştir
            boolean courseFound = false;
            for (int i = 0; i < courses.size(); i++) {
                Course currentCourse = courses.get(i);
                if (currentCourse.getCourseId().equals(updatedCourse.getCourseId())) {
                    // Bulunan kursu güncelle
                    courses.set(i, updatedCourse);
                    courseFound = true;
                    break;
                }
            }

            // Kurs bulunamadıysa hata mesajı yazdırabiliriz
            if (!courseFound) {
                System.err.println("Course with ID " + updatedCourse.getCourseId() + " not found!");
                return;
            }

            // Güncellenmiş listeyi JSON dosyasına yaz
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(courseFile, courses);
            System.out.println("Course JSON updated successfully!");

        } catch (IOException e) {
            System.err.println("Error updating course JSON: " + e.getMessage());
        }
    }

    /*public void updateCourseInJsonAndStudents(Course updatedCourse, List<Student> allStudents) {
        // Kursu güncellemeyi yapalım
        updateCourseInJson(updatedCourse); // Kurs güncellemesi

        // Öğrenciler üzerinde döngü kurarak, her öğrencinin ilgili requestedCourses listesini kontrol et
        for (Student student : allStudents) {
            List<Course> updatedRequestedCourses = new ArrayList<>(student.getRequestedCourses());

            // Eğer öğrenci requestedCourses listesinde güncellenmiş kursu bulursa, işlemi gerçekleştir
            for (Course course : updatedRequestedCourses) {
                if (course.getCourseId().equals(updatedCourse.getCourseId())) {
                    // Öğrenci bilgilerini güncelle
                    student.getRequestedCourses().remove(course);
                    student.getRequestedCourses().add(updatedCourse); // Kursu güncelle

                    // Öğrenci JSON'unu güncelle
                    updateStudentInJson(student);
                    break; // Bu öğrenci için işlemi bitir
                }
            }
        }

    }*/





}
