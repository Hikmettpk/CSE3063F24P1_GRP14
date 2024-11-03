import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods(); // Create an instance of JSONMethods to use without static

        List<Course> courses = jsonMethods.loadAllCourses();
        for (Course course:courses)
        {
            System.out.println(course.toString());
        }
        Student stu = jsonMethods.loadStudent("150121003");
        System.out.println(stu.toString());
        Advisor advisor = jsonMethods.loadAdvisor("120121047");
        System.out.println(advisor.toString());
        advisor.setName("Porahan");
        stu.setName("Aptal"); //anında jsona yaz
        System.out.println(advisor.toString());
        System.out.println(stu.toString());

    }
}
