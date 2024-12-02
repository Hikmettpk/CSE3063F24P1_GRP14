import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseRegistrationSystem {
    private Student student;
    private List<Course> courses;
    private JSONMethods jsonMethods = new JSONMethods(); // JSON işlemleri için kullanılan sınıf

    // Constructor
    public CourseRegistrationSystem(Student student, List<Course> courses) {
        this.student = student;
        this.courses = courses;
    }

    // Öğrenciyi bir kursa ekleme
    public void addToEnrollList(Course course, Student student) throws IOException {
        // Öğrenci zaten bu kursa kayıtlı mı?
        if (student.getEnrolledCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            System.out.println("Student is already enrolled in this course.");
            return;
        }

        // Kursa kaydet
        student.getEnrolledCourses().add(course);
        jsonMethods.saveStudentToFile(student);
        System.out.println("Student enrolled in course successfully.");
    }

    // Öğrencinin talep listesinden bir kursu kaldırma
    public boolean removeCourseFromRequestList(Student student, Course course) throws IOException {
        if (student.getRequestedCourses().remove(course)) {
            jsonMethods.saveStudentToFile(student);
            System.out.println("Course removed from request list successfully.");
            return true;
        } else {
            System.out.println("You did not request this course.");
            return false;
        }
    }

    // Öğrencinin kayıtlı olduğu kurslardan birini kaldırma
    public void removeCourseFromEnrolledList(Student student, Course course) throws IOException {
        if (student.getEnrolledCourses().remove(course)) {
            jsonMethods.saveStudentToFile(student);
            System.out.println("Course removed from enrolled list successfully.");
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    // Uygun derslerin listesini döndürme
    public List<Course> listAvailableCourses() {
        List<Course> availableCourses = new ArrayList<>();
        List<String> passedCourses = new ArrayList<>();
        List<String> failedCourses = new ArrayList<>();
        List<Course> takenCourses = new ArrayList<>();

        List<String> passingGrades = Arrays.asList("AA", "BA", "BB", "CB", "CC");

        // Transcript'i analiz et
        for (Grade grade : student.getTranscript().allGrades()) {
            Course course = grade.getCourse();
            String gradeValue = grade.getGradeValue();

            if (gradeValue.equals("FF") || gradeValue.equals("FD")) {
                failedCourses.add(course.getCourseId());
            } else if (passingGrades.contains(gradeValue)) {
                passedCourses.add(course.getCourseId());
            } else if (gradeValue.equals("DD") || gradeValue.equals("DC")) {
                takenCourses.add(course);
            }
        }

        // Mevcut kursları kontrol et
        for (Course course : courses) {
            if (passedCourses.contains(course.getCourseId()) ||
                    student.getRequestedCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId())) ||
                    student.getEnrolledCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
                continue;
            }

            // Ön koşul kontrolü
            if (course.hasPrerequisite() && !passedCourses.contains(course.getPrerequisiteLessonId())) {
                continue;
            }

            availableCourses.add(course);
        }

        return availableCourses;
    }

    // Zamanlama çakışmasını kontrol etme
    public boolean checkScheduleConflict(Course newCourse, Student student) throws IOException {
        for (Course enrolledCourse : student.getEnrolledCourses()) {
            if (isScheduleConflict(enrolledCourse, newCourse)) {
                System.out.println("Schedule conflict with enrolled course: " + enrolledCourse.getCourseName());
                return true;
            }
        }

        for (Course requestedCourse : student.getRequestedCourses()) {
            if (isScheduleConflict(requestedCourse, newCourse)) {
                System.out.println("Schedule conflict with requested course: " + requestedCourse.getCourseName());
                System.out.println("Please choose one course to keep:");
                System.out.println("1. " + requestedCourse.getCourseName());
                System.out.println("2. " + newCourse.getCourseName());

                int choice = getUserChoice();
                if (choice == 1) {
                    System.out.println("Keeping " + requestedCourse.getCourseName() + " and rejecting " + newCourse.getCourseName());
                    return true;
                } else if (choice == 2) {
                    student.getRequestedCourses().remove(requestedCourse);
                    jsonMethods.saveStudentToFile(student);
                    System.out.println("Removed " + requestedCourse.getCourseName() + " and added " + newCourse.getCourseName());
                    return false;
                } else {
                    System.out.println("Invalid choice. No action taken.");
                    return true;
                }
            }
        }

        return false;
    }

    // Ders talep etme
    public void requestInCourse(Course course, Student student) throws IOException {
        if (checkScheduleConflict(course, student)) {
            return;
        }

        if (course.getCurrentCapacity() >= course.getEnrollmentCapacity()) {
            System.out.println("This course is full.");
            return;
        }

        if (student.getRequestedCourses().contains(course)) {
            System.out.println("You have already requested this course.");
            return;
        }

        student.getRequestedCourses().add(course);
        jsonMethods.updateStudentInJson(student);
        System.out.println("Successfully requested the course: " + course.getCourseName());
    }

    // Zamanlama çakışmasını kontrol etmek için yardımcı metot
    private boolean isScheduleConflict(Course course1, Course course2) {
        for (CourseSection section1 : course1.getCourseSection()) {
            for (CourseSection section2 : course2.getCourseSection()) {
                if (section1.getDay().equals(section2.getDay()) && section1.getHour().equals(section2.getHour())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Kullanıcıdan seçim almak için yardımcı metot
    private int getUserChoice() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        try {
            System.out.print("Enter your choice (1 or 2): ");
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Defaulting to choice 1.");
            return 1;
        }
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


}
