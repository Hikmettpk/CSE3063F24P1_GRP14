import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        InputStream inputStream = JSONMethods.class.getClassLoader().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        return objectMapper.readValue(inputStream, Advisor.class);
    }
    /*
    public void saveToFile(String userId) { //?
        ObjectMapper mapper = new ObjectMapper();
        String resourcePath = "./resources/Students/" + userId + ".json";
        try {
            mapper.writeValue(new File(resourcePath), this);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

     */

}
