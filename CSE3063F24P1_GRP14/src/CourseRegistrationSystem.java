import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationSystem {
    private Student student;
    //private List<Student> students;
    private List<Course> courses;
    private JSONMethods jsonMethods = new JSONMethods(); // Assuming JSONMethods class handles JSON operations

    // Constructor
    public CourseRegistrationSystem(Student student, List<Course> courses) {
        this.student = student;
        //this.students = students;
        this.courses = courses;
    }


    // Method to add a student to a course
    public void addStudentToCourse(Course course, Student student) throws IOException {
        // Check if the student is already enrolled in the course
        if (student.getEnrolledCourses().contains(course)) {
            System.out.println("Student is already enrolled in this course.");
            return;
        }

        // Check if the course has enough capacity
        CourseSection section = course.getCourseSection(); // Assumes each course has a single section for simplicity
        if (section.getEnrollmentCapacity() <= 0) {
            System.out.println("Course enrollment capacity reached.");
            return;
        }

        // Enroll the student in the course
        student.getEnrolledCourses().add(course);

        // Update the course's enrollment capacity
        //section.setEnrollmentCapacity(section.getEnrollmentCapacity() - 1);

        // Save the updated student object to JSON file
        jsonMethods.saveStudentToFile(student);

        System.out.println("Student enrolled in course successfully.");
    }


    // Method to list available course sections for a given student
    /*
    public List<CourseSection> listAvailableCourses(Student student) {
        List<CourseSection> availableSections = new ArrayList<> ();
        for (Course course : courses) {
            for (CourseSection section : course.getCourseSections()) {
                if (!student.getEnrolledCourses().contains(course)) { // Check if student is not already enrolled
                    availableSections.add(section);
                }
            }
        }
        return availableSections;
    }

    */

    // Method to request a course for a student
    public void requestInCourse(Course course, Student student) {
        if (!student.getRequestedCourses().contains(course)) { // Avoid duplicate requests
            student.getRequestedCourses().add(course);
            System.out.println("Course requested successfully.");
        } else {
            System.out.println("Course already requested.");
        }
    }
}
