import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Student extends User{
    @JsonProperty("studentID")
    private String studentID;

    @JsonProperty("enrolledCourses")
    private List<Course> enrolledCourses;

    @JsonProperty("requestedCourses")
    private List<Course> requestedCourses;

    @JsonProperty("transcript")
    private Transcript transcript;
    @JsonProperty("advisor")
    private Advisor advisor;


    public Student(@JsonProperty("username") String username,@JsonProperty("name") String name,@JsonProperty("surname") String surname,@JsonProperty("password") String password,@JsonProperty("studentID") String studentID,
                   @JsonProperty("transcript") Transcript transcript,@JsonProperty("advisor") Advisor advisor) {
        super(username, name, surname, password);
        this.requestedCourses = new ArrayList<>();
        this.enrolledCourses = new ArrayList<>();
        this.advisor = advisor;
        this.transcript = transcript;
        this.studentID = studentID;
    }
  // private Grade grade;

    // Getters and setters
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    public String getStudentID() {
        return studentID;
    }


    public Advisor getAdvisor() {
        return advisor;
    }


    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    public List<Course> getRequestedCourses() {
        return requestedCourses;
    }
    public Transcript getTranscript() {
        return transcript;
    }



    //listAvailableCourses metodu yazılacak

    // toString() method
    @Override
    public String toString() {
        return "Student{" +
                "username='" + getUsername() + '\'' +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", studentID='" + studentID + '\'' +
                ", enrolledCourses=" + enrolledCourses +
                ", requestedCourses=" + requestedCourses +
                ", transcript=" + transcript +
                '}';
    }


    public void displaySchedule(Student student) {
        // Öğrenci bilgilerini güncel JSON'dan yükle
        student = new JSONMethods().loadStudent(student.getStudentID());

        if (student.getEnrolledCourses() == null || student.getEnrolledCourses().isEmpty()) {
            System.out.println("No enrolled courses to display.");
            return;
        }

        // Ders programı için matris oluştur
        String[][] schedule = new String[10][5]; // 10 saatlik dilim, 5 gün

        // Tüm hücreleri boş bırak
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[i].length; j++) {
                schedule[i][j] = "               "; // Boş hücre için sabit genişlik
            }
        }

        // Derslerin gün ve saat bilgilerini tabloya yerleştir
        for (Course course : student.getEnrolledCourses()) {
            if (course.getCourseSection() != null) {
                for (CourseSection section : course.getCourseSection()) {
                    int dayIndex = getDayIndex(section.getDay());
                    int hourIndex = getHourIndex(section.getHour());

                    if (dayIndex != -1 && hourIndex != -1) {
                        schedule[hourIndex][dayIndex] = course.getCourseId() + " (" + section.getPlace() + ")";
                    }
                }
            }
        }

        // Tablo başlıklarını yazdır
        System.out.println("              Monday                Tuesday               Wednesday             Thursday              Friday");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Tabloyu saatlere göre yazdır
        for (int i = 0; i < schedule.length; i++) {
            String hour = getHourLabel(i);
            System.out.print(hour + " | "); // Saat etiketi
            for (int j = 0; j < schedule[i].length; j++) {
                System.out.print(schedule[i][j] + "   "); // Ders bilgisi veya boş hücre
            }
            System.out.println();
        }
    }

    // Gün indeks dönüşümü
    private int getDayIndex(String day) {
        switch (day) {
            case "Monday": return 0;
            case "Tuesday": return 1;
            case "Wednesday": return 2;
            case "Thursday": return 3;
            case "Friday": return 4;
            default: return -1;
        }
    }

    // Saat indeks dönüşümü
    private int getHourIndex(String hour) {
        switch (hour) {
            case "8:30-9:20": return 0;
            case "9:30-10:20": return 1;
            case "10:30-11:20": return 2;
            case "11:30-12:20": return 3;
            case "13:00-13:50": return 4;
            case "14:00-14:50": return 5;
            case "15:00-15:50": return 6;
            case "16:00-16:50": return 7;
            case "17:00-17:50": return 8;
            case "18:00-18:50": return 9;
            default: return -1;
        }
    }

    // Saat etiketi dönüşümü
    private String getHourLabel(int index) {
        String[] hourLabels = {
                "8:30-9:20 ", "9:30-10:20 ", "10:30-11:20 ", "11:30-12:20 ",
                "13:00-13:50", "14:00-14:50", "15:00-15:50", "16:00-16:50",
                "17:00-17:50", "18:00-18:50"
        };
        return hourLabels[index];
    }

}
