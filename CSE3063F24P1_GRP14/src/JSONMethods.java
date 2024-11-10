import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONMethods {

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
        System.out.println(resourcePath);
        InputStream inputStream = JSONMethods.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        return objectMapper.readValue(inputStream, Advisor.class);
    }


    private static final String STUDENT_JSON_PATH = "CSE3063F24P1_GRP14/src/resources/Students/";

    public void saveStudentToFile(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String studentFileName = STUDENT_JSON_PATH + student.getStudentID() + ".json";
        System.out.println("Saving to: " + studentFileName);
        mapper.writeValue(new File(studentFileName), student);
    }


}
