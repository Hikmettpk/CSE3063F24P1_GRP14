import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods(); // Create an instance of JSONMethods to use without static
        List<Course> courses = jsonMethods.loadAllCourses();

        Student stu = jsonMethods.loadStudent("150121056");
        //System.out.println(stu.toString());

        Advisor advisor = jsonMethods.loadAdvisor("120121074");
        //System.out.println(advisor.toString());
        CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem(stu,courses);

        Course newCourse = courses.get(2);


        //System.out.println("Kursu içeriyor mu : "+stu.getEnrolledCourses().contains(newCourse));


        //courseRegistrationSystem.addStudentToCourse(newCourse,stu);
        //courseRegistrationSystem.requsetInCourse(newCourse,stu);
        //addCourseToEnrolledCourses(newCourse, stu);
        System.out.println("stu id "+stu.getStudentID());


        //System.out.println("Grades:");
        /*
        for (Grade grade : stu.getTranscript().getGrades()) {
            System.out.println(grade.getCourse().getCourseName() + ": " + grade.getGradeValue());
        }

         */
        //System.out.println(stu.getAdvisor().getName());
        courseRegistrationSystem.requestInCourse(newCourse,stu);
        for (Course course: courseRegistrationSystem.listAvailableCourses()) {
            System.out.println(course.getCourseName());
        }


        courseRegistrationSystem.removeCourseFromRequestList(stu,newCourse);
        courseRegistrationSystem.removeCourseFromEnrolledList(stu,newCourse);

        //advisor.approveRequestedCourse(courseRegistrationSystem,stu,newCourse); //kurs requestte olmasa bile enrolled'a ekliyor??

        System.out.println("İstenen kurslar: ");
        for (Course course:
                stu.getRequestedCourses()) {
            System.out.println(course.getCourseName());
        }

        System.out.println("Alınan kurslar: ");
        for (Course course:
                stu.getEnrolledCourses()) {
            System.out.println(course.getCourseName());
        }

        //jsonMethods.saveToFile(stu.getStudentID());
        //System.out.println("done");


        //stu.setName("Zort"); //anında jsona yaz
        //System.out.println(advisor.toString());
        //System.out.println(stu.toString());

    }
}
