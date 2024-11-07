import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods(); // Create an instance of JSONMethods to use without static
        List<Course> courses = jsonMethods.loadAllCourses();

        Student stu = jsonMethods.loadStudent("150121003");
        //System.out.println(stu.toString());

        Advisor advisor = jsonMethods.loadAdvisor("120121074");
        //System.out.println(advisor.toString());
        CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem(stu,courses);

        Course newCourse = courses.get(1);
        //System.out.println(newCourse);
        for (Course course:
             stu.getEnrolledCourses()) {
            System.out.println(course.getCourseName());
        }

        System.out.println("Kursu içeriyor mu : "+stu.getEnrolledCourses().contains(newCourse));


        //courseRegistrationSystem.addStudentToCourse(newCourse,stu);
        //courseRegistrationSystem.requsetInCourse(newCourse,stu);
        //addCourseToEnrolledCourses(newCourse, stu);
        System.out.println("stu id "+stu.getStudentID());
        System.out.println("Grades:");

        for (Grade grade : stu.getTranscript().getGrades()) {
            System.out.println(grade.getCourse().getCourseName() + ": " + grade.getGradeValue());
        }
        //jsonMethods.saveToFile(stu.getStudentID());
        //System.out.println("done");


        //stu.setName("Zort"); //anında jsona yaz
        //System.out.println(advisor.toString());
        //System.out.println(stu.toString());

    }
}
