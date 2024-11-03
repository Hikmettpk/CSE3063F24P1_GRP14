import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class Student {

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("studentID")
    private String studentID;

    @JsonProperty("enrolledCourses")
    private List<Course> enrolledCourses;

    @JsonProperty("requestedCourses")
    private List<Course> requestedCourses;

    @JsonProperty("transcript")
    private Transcript transcript;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }

    public void setRequestedCourses(List<Course> requestedCourses) {
        this.requestedCourses = requestedCourses;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    // toString() method
    @Override
    public String toString() {
        return "Student{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", studentID='" + studentID + '\'' +
                ", enrolledCourses=" + enrolledCourses +
                ", requestedCourses=" + requestedCourses +
                ", transcript=" + transcript +
                '}';
    }

    // Nested Transcript class
    public static class Transcript {
        @JsonProperty("grades")
        private List<Grade> grades;

        // Getters and setters
        public List<Grade> getGrades() {
            return grades;
        }

        public void setGrades(List<Grade> grades) {
            this.grades = grades;
        }

        @Override
        public String toString() {
            return "Transcript{" +
                    "grades=" + grades +
                    '}';
        }
    }

    // Nested Grade class
    public static class Grade {
        @JsonProperty("course")
        private Course course;

        @JsonProperty("gradeValue")
        private String gradeValue;

        // Getters and setters
        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public String getGradeValue() {
            return gradeValue;
        }

        public void setGradeValue(String gradeValue) {
            this.gradeValue = gradeValue;
        }

        @Override
        public String toString() {
            return "Grade{" +
                    "course=" + course +
                    ", gradeValue='" + gradeValue + '\'' +
                    '}';
        }
    }

    // Assuming Course and CourseSection classes are implemented similarly
}
