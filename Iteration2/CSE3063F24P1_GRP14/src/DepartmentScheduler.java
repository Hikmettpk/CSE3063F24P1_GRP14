import java.io.File;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DepartmentScheduler extends User {
    private List<Course> courses; // Tüm derslerin listesi

    private static final String COURSE_JSON_PATH = "CSE3063F24P1_GRP14/src/resources/course.json";
    private static final String SCHEDULER_JSON_PATH = "CSE3063F24P1_GRP14/src/resources/department_scheduler.json";

    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final String[] HOURS = {
            "8:30-9:20", "9:30-10:20", "10:30-11:20", "11:30-12:20",
            "13:00-13:50", "14:00-14:50", "15:00-15:50", "16:00-16:50",
            "17:00-17:50", "18:00-18:50"
    };
    private static final String[] PLACES = {"M1-Z01", "M1-Z06", "M1-110", "M2-Z11", "M2-Z06", "M2-110", "M2-103"};

    public DepartmentScheduler() {
        super(null, null, null, null);
        this.courses = new ArrayList<>();
        this.courses = loadCoursesFromJson();
    }

    public DepartmentScheduler(String username, String name, String surname, String password) {
        super(username, name, surname, password);
        this.courses = loadCoursesFromJson();
    }

    public void printAllCourses() {
        System.out.printf("%-10s %-30s\n", "Course Code", "Course Name");
        for (Course course : courses) {
            System.out.printf("%-10s %-30s\n", course.getCourseId(), course.getCourseName());
        }
    }

    public void resetAllCourseSections() {
        System.out.println("Resetting all course sections...");
        try {
            for (Course course : courses) {
                updateCourseSectionsInJson(course.getCourseId(), new ArrayList<>());
            }
            this.courses = loadCoursesFromJson(); // JSON dosyasını yeniden yükle
            System.out.println("All course sections have been reset.");
        } catch (Exception e) {
            System.err.println("Error while resetting course sections: " + e.getMessage());
        }
    }

    public boolean updateCourseSections(String courseId) {
        Course selectedCourse = findCourseById(courseId);
        if (selectedCourse == null) {
            System.out.println("Course not found!");
            return false;
        }

        System.out.println("Updating sections for course: " + selectedCourse.getCourseName());
        List<CourseSection> sections = new ArrayList<>();

        // Mevcut bölümleri temizleme
        System.out.println("Clearing all existing sections...");
        updateCourseSectionsInJson(courseId, new ArrayList<>());

        // Yeni bölümleri ekleme
        for (int i = 0; i < selectedCourse.getWeeklyCourseCount(); i++) {
            System.out.println("Section " + (i + 1) + ":");
            System.out.println("Select day:");
            String day = selectOption(DAYS, "Select a day:");
            System.out.println("Select hour:");
            String hour = selectAvailableHour(day, sections, selectedCourse); // Aynı sınıf kontrolü ekleniyor
            if (hour == null) {
                System.out.println("No available hours for this day!");
                return false;
            }

            System.out.println("Select place:");
            String place = selectAvailablePlace(day, hour);
            if (place == null) {
                System.out.println("No available classrooms for this time slot!");
                return false;
            }
            sections.add(new CourseSection(day, place, hour));
        }

        // Yeni bölümleri JSON'a yaz ve listeyi güncelle
        updateCourseSectionsInJson(courseId, sections);
        this.courses = loadCoursesFromJson(); // JSON dosyasını yeniden yükle
        System.out.println("Sections updated successfully for course " + selectedCourse.getCourseName());
        return true;
    }

    public String selectAvailablePlace(String day, String hour) {
        Set<String> occupiedPlaces = new HashSet<>();

        for (Course course : courses) {
            for (CourseSection section : course.getCourseSection()) {
                if (section.getDay().equals(day) && section.getHour().equals(hour)) {
                    occupiedPlaces.add(section.getPlace());
                }
            }
        }

        List<String> availablePlaces = new ArrayList<>();
        for (String place : PLACES) {
            if (!occupiedPlaces.contains(place)) {
                availablePlaces.add(place);
            }
        }

        if (availablePlaces.isEmpty()) {
            return null;
        }

        return selectOption(availablePlaces.toArray(new String[0]), "Available places:");
    }

    private String selectAvailableHour(String day, List<CourseSection> currentSections, Course currentCourse) {
        // Şimdiden seçilmiş saatleri kontrol et
        Set<String> occupiedHours = new HashSet<>();
        int currentCourseYear = currentCourse.getYear(); // Mevcut dersin sınıf seviyesi

        // Mevcut seçili bölümleri kontrol et
        for (CourseSection section : currentSections) {
            if (section.getDay().equals(day)) {
                occupiedHours.add(section.getHour());
            }
        }

        // Diğer kursların bölümlerini kontrol et
        for (Course course : courses) {
            if (course.getYear() == currentCourseYear) { // Aynı sınıf seviyesindeki dersleri kontrol et
                for (CourseSection section : course.getCourseSection()) {
                    if (section.getDay().equals(day)) {
                        occupiedHours.add(section.getHour());
                    }
                }
            }
        }

        // Kullanılabilir saatleri oluştur
        List<String> availableHours = new ArrayList<>();
        for (String hour : HOURS) {
            if (!occupiedHours.contains(hour)) {
                availableHours.add(hour);
            }
        }

        if (availableHours.isEmpty()) {
            return null;
        }

        // Kullanıcıdan seçim yapmasını iste
        System.out.println("Available hours (no conflict with same year courses):");
        for (int i = 0; i < availableHours.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, availableHours.get(i));
        }

        System.out.print("Select an hour: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return availableHours.get(choice - 1);
    }


    private Course findCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    private String selectOption(String[] options, String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s\n", i + 1, options[i]);
            }
            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= options.length) {
                    return options[choice - 1];
                } else {
                    System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    private List<Course> loadCoursesFromJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(COURSE_JSON_PATH);
            Course[] coursesArray = objectMapper.readValue(file, Course[].class);
            return Arrays.asList(coursesArray);
        } catch (IOException e) {
            System.err.println("Error loading courses from JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateCourseSectionsInJson(String courseId, List<CourseSection> updatedSections) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(COURSE_JSON_PATH);
            List<Course> currentCourses = Arrays.asList(objectMapper.readValue(file, Course[].class));

            List<Course> updatedCourses = new ArrayList<>();
            for (Course course : currentCourses) {
                if (course.getCourseId().equals(courseId)) {
                    updatedCourses.add(new Course(
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getCredit(),
                            course.hasPrerequisite(),
                            course.getPrerequisiteLessonId(),
                            updatedSections,
                            course.getWeeklyCourseCount(),
                            course.getYear(),
                            course.getInstructor(),
                            course.getEnrollmentCapacity(),
                            course.getCurrentCapacity(),
                            course.getStatus()
                    ));
                } else {
                    updatedCourses.add(course);
                }
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, updatedCourses);
            System.out.println("Course JSON updated successfully!");
        } catch (IOException e) {
            System.err.println("Error updating course JSON: " + e.getMessage());
        }
    }
}
