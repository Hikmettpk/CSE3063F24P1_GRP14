import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Course> listAvailableCourses() {
        List<Course> availableCourses = new ArrayList<>();
        List<Course> takenCourses = new ArrayList<>();
        List<String> failedCourses = new ArrayList<>();
        List<String> passedCourses = new ArrayList<>();

        // Geçerli notlar
        List<String> passingGrades = Arrays.asList("AA", "BA", "BB", "CB", "CC");

        // Transcript'teki dersleri kategorize et
        for (Grade grade : student.getTranscript().allGrades()) {
            String gradeValue = grade.getGradeValue();
            Course course = grade.getCourse();

            // Başarısız dersler
            if (gradeValue.equals("FF") || gradeValue.equals("FD")) {
                failedCourses.add(course.getCourseId());
            }
            // Başarılı dersler
            else if (passingGrades.contains(gradeValue)) {
                passedCourses.add(course.getCourseId());
            }
            // Düşük notlar
            else if (gradeValue.equals("DD") || gradeValue.equals("DC")) {
                takenCourses.add(course);
            }
        }

        // Diğer dersleri kontrol et
        for (Course course : courses) {
            // Halihazırda alınan, başarıyla tamamlanan veya requested/enrolled listesinde olan dersleri atla
            if (passedCourses.contains(course.getCourseId()) ||
                    takenCourses.contains(course) ||
                    student.getRequestedCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId())) ||
                    student.getEnrolledCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
                continue;
            }

            // Prerequisite kontrolü
            if (course.hasPrerequisite()) {
                String prerequisiteId = course.getPrerequisiteLessonId();
                if (!passedCourses.contains(prerequisiteId)) {
                    continue; // Prerequisite geçilmemiş, eklemeyi atla
                }
            }

            // Tüm kontrolleri geçen dersleri listeye ekle
            availableCourses.add(course);
        }

        return availableCourses;
    }







    public String availableCoursesToString(List<Course> availableCourses) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-40s\n", "Course ID", "Course Name"));
        sb.append("------------------------------------------------------------\n");

        for (Course course : availableCourses) {
            sb.append(String.format("%-10s %-40s\n",
                    course.getCourseId(),
                    course.getCourseName()));
        }

        return sb.toString();
    }



    public void requestInCourse(Course course, Student student) throws IOException {
        // Öğrencinin kayıtlı kurslarını kontrol et
        if (student.getEnrolledCourses().contains(course)) {
            System.out.println("You are already enrolled in this course.");
            return;
        }

        // Öğrencinin daha önce talep ettiği kursları kontrol et
        if (student.getRequestedCourses().contains(course)) {
            System.out.println("You have already requested this course.");
            return;
        }

        System.out.println( " counted :" +countRequestedStudents(jsonMethods.loadAllStudents(), course));
        // Kursun kapasitesini kontrol et
        if (countRequestedStudents(jsonMethods.loadAllStudents(), course) >= course.getEnrollmentCapacity()) {
            System.out.println("hopp waitlistte giriyosun");
            addToWaitList(student, course);
            System.out.println("waitlisttesin");
            jsonMethods.updateCourseInJson(course);
            jsonMethods.updateStudentInJson(student);
            System.out.println("This course is full and cannot accept more students. You are added to wait list of course "
                    + course.getCourseId() + ".");
            return;
        }

        // Öğrencinin requestedCourses listesine ekle
        student.getRequestedCourses().add(course);

        // JSON'da güncelleme
        jsonMethods.updateStudentInJson(student);
        System.out.println( " updated counted :" +countRequestedStudents(jsonMethods.loadAllStudents(), course));

        System.out.println("Successfully requested the course: " + course.getCourseName());
    }
    private void addToWaitList(Student student, Course course){
        course.getWaitList().add(student.getStudentID());
    }

    private int countRequestedStudents(List<Student> allStudents, Course course) {
        int count = 0;
        for (Student student : allStudents) {
            for (Course requestedCourse : student.getRequestedCourses()) {
                if (requestedCourse.getCourseId().equals(course.getCourseId())) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }


}
