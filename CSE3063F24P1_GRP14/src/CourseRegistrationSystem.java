import java.io.IOException;
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
    public void addToEnrollList(Course course, Student student) throws IOException {
        // Check if the student is already enrolled in the course by comparing course IDs
        boolean isAlreadyEnrolled = false;
        for (Course enrolledCourse : student.getEnrolledCourses()) {
            if (enrolledCourse.getCourseId().equals(course.getCourseId())) {
                isAlreadyEnrolled = true;
                break;
            }
        }

        if (isAlreadyEnrolled) {
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
        // section.setEnrollmentCapacity(section.getEnrollmentCapacity() - 1);

        // Save the updated student object to JSON file
        jsonMethods.saveStudentToFile(student);

        System.out.println("Student enrolled in course successfully.");
    }

    //Remove the course from the students request list
    public boolean removeCourseFromRequestList(Student student, Course course) throws IOException {
        if(student.getRequestedCourses().size() > 0 && student.getRequestedCourses().contains(course)){
            student.getRequestedCourses().remove(course);
            jsonMethods.saveStudentToFile(student);
            return true; //succesfully removed

        }
        else{
            System.out.println("You did not take any course. Firstly, please request at least one course.");
            return false; //removal failed
        }
    }

    public void removeCourseFromEnrolledList(Student student, Course course) throws IOException {
        if(student.getEnrolledCourses().size() > 0 && student.getEnrolledCourses().contains(course)){
            student.getEnrolledCourses().remove(course);
            jsonMethods.saveStudentToFile(student);

        }
        else{
            System.out.println("You are not enrolled to any course. Firstly, please request at least one course.");
        }
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
    public void requestInCourse(Course course, Student student) throws IOException {
        // Check if the student is already enrolled in the course by comparing course IDs
        boolean isAlreadyRequested = false;
        boolean hasPassedPrerequisite = course.hasPrerequisite() ? false : true;
        for (Course requestedCourse : student.getRequestedCourses()) {
            if (requestedCourse.getCourseId().equals(course.getCourseId())) { //zaten kayıtlı mı
                isAlreadyRequested = true;
                break;
            }
        }

        List<Grade> gradeList = student.getTranscript().getGrades();
        if (course.hasPrerequisite()){
            for (Grade grade:gradeList) {
                if ((course.getPrerequisiteLessonId().equals(grade.getCourse().getCourseId())) && grade.getGradeValue() != "FF") { //dersi daha önce almış ve notu ff değil
                    hasPassedPrerequisite=true;
                }
            }
        }


        if (isAlreadyRequested) {
            System.out.println("Student is already requested to this course.");
            return;
        }


        if (hasPassedPrerequisite){
            student.getRequestedCourses().add(course);
            System.out.println("Student requested in course successfully.");

        } else
            System.out.println("You have to pass the prerequisite course to enroll this lesson !");
        // Add the course to request list of that student in the course

        // Update the course's enrollment capacity
        // section.setEnrollmentCapacity(section.getEnrollmentCapacity() - 1);

        // Save the updated student object to JSON file
        jsonMethods.saveStudentToFile(student);

    }


}
