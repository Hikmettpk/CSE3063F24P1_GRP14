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
        Student stu = jsonMethods.loadStudent("150121074");
        System.out.println(stu.toString());

        Advisor advisor = jsonMethods.loadAdvisor("120121047");
        System.out.println(advisor.toString());

        Course newCourse = courses.get(0);
        //addCourseToEnrolledCourses(newCourse, stu);
        System.out.println("stu id "+stu.getStudentID());
        //jsonMethods.saveToFile(stu.getStudentID());
        //System.out.println("done");


        //stu.setName("Zort"); //anında jsona yaz
        //System.out.println(advisor.toString());
        //System.out.println(stu.toString());

    }
    /*
    public static void addCourseToEnrolledCourses(Course course, Student student) {
        if (student.getEnrolledCourses() != null) {
            student.getEnrolledCourses().add(course);
        }
    }

     */
}
