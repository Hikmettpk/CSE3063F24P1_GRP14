import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class SystemController {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods();
        List<Course> courses = jsonMethods.loadAllCourses();
        List<Student> students = jsonMethods.loadAllStudents();
        List<Advisor> advisors = jsonMethods.loadAllAdvisors();

        Scanner input = new Scanner(System.in);

        while (true) {
            String username;
            String password;
            String role = "";
            boolean isLoggedIn = false;
            Student loggedInStudent = null;
            Advisor loggedInAdvisor = null;

            //---------------Login-------------
            while (!isLoggedIn) {
                // get input for login
                System.out.print("Username (write 'q' to quit): ");
                username = input.nextLine();

                // Check for exit condition
                if (username.equalsIgnoreCase("q")) {
                    System.out.println("Exiting program...");
                    return; // Exit the program
                }

                System.out.print("Password: ");
                password = input.nextLine();

                // Check user role and credentials
                if (username.startsWith("o")) { // Student check
                    boolean found = false;
                    role = "Student";
                    for (Student student : students) {
                        if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                            found = true;
                            System.out.println("Login successful, welcome " + student.getName() + " (" + role + ")");
                            loggedInStudent = student;
                            isLoggedIn = true; // Set login status to true
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Wrong username or password."); //username starts with 'o' but wrong username or password
                    }
                } else if (username.startsWith("advisor")) { // Advisor check
                    boolean found = false;
                    role = "Advisor";
                    for (Advisor advisor : advisors) {
                        if (advisor.getUsername().equals(username) && advisor.getPassword().equals(password)) {
                            found = true;
                            System.out.println("Login successful, welcome " + advisor.getName() + " (" + role + ")");
                            loggedInAdvisor = advisor;
                            isLoggedIn = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Wrong username or password."); //username starts with "advisor" but wrong username or password
                    }
                } else {
                    System.out.println("Wrong username or password."); //totally wrong username
                }
            }


            // After successful login
            // -----------------------Advisor menu-----------------------------
            if (role.equals("Advisor")) {
                CourseRegistrationSystem crs = new CourseRegistrationSystem(null, courses);
                while (true) {
                    System.out.println("1. See requests\n2. Logout ");
                    System.out.print("Please choose an operation: ");
                    int choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            //Request approve case
                            System.out.println("Showing requests...");
                            List<Student> allStudentsWithRequests = new ArrayList<>();

                            for (Student student : students) {
                                if (!student.getRequestedCourses().isEmpty()) {
                                    allStudentsWithRequests.add(student);
                                }
                            }

                            if (allStudentsWithRequests.isEmpty()) {
                                System.out.println("There are no course requests at the moment.");
                            } else {
                                int requestNo = 1;
                                StringBuilder sb = new StringBuilder();
                                sb.append(String.format("%-5s %-20s %-20s %-10s %-40s\n", "No", "Student Name", "Surname", "Course ID", "Course Name"));
                                sb.append("---------------------------------------------------------------------------------------------\n");

                                for (Student student : allStudentsWithRequests) {
                                    for (Course course : student.getRequestedCourses()) {
                                        sb.append(String.format("%-5d %-20s %-20s %-10s %-40s\n",
                                                requestNo, student.getName(), student.getSurname(),
                                                course.getCourseId(), course.getCourseName()));
                                        requestNo++;
                                    }
                                }

                                System.out.println(sb.toString());

                                // Onaylamak için giriş al
                                System.out.print("Enter the request number to approve: ");
                                int requestIndex = input.nextInt();

                                // Geçerli bir sıra numarası kontrolü
                                if (requestIndex > 0 && requestIndex <= requestNo - 1) {
                                    int count = 1;
                                    Student selectedStudent = null;
                                    Course selectedCourse = null;

                                    outerLoop:
                                    for (Student student : allStudentsWithRequests) {
                                        for (Course course : student.getRequestedCourses()) {
                                            if (count == requestIndex) {
                                                selectedStudent = student;
                                                selectedCourse = course;
                                                break outerLoop;
                                            }
                                            count++;
                                        }
                                    }

                                    if (selectedStudent != null && selectedCourse != null) {
                                        loggedInAdvisor.approveRequestedCourse(crs, selectedStudent, selectedCourse);
                                        System.out.println("Request approved successfully.");
                                    } else {
                                        System.out.println("Invalid request number.");
                                    }
                                } else {
                                    System.out.println("Invalid request number. Please try again.");
                                }
                            }
                            break;


                        case 2: // Logout
                            System.out.println("Logging out...");
                            isLoggedIn = false; // Set login status to false to go back to login
                            role = ""; // Reset role
                            input.nextLine(); // Consume the leftover newline
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (!isLoggedIn) break; // Exit the loop if logged out
                }
            }


            // -----------------------Student menu-----------------------------
            if (role.equals("Student")) {
                CourseRegistrationSystem crs = new CourseRegistrationSystem(loggedInStudent, courses);
                while (true) {
                    System.out.println("1. View Transcript\n2. Request course:\n3. Logout ");
                    System.out.print("Please choose an operation: ");
                    int choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            // Show transcript
                            System.out.println("Showing transcript...");
                            System.out.println(loggedInStudent.getTranscript().toString());
                            break;

                        case 2:
                            //Request course
                            System.out.println("Requesting course...");

                            // List all available courses
                            System.out.println(crs.availableCoursesToString(crs.listAvailableCourses()));

                            String courseCode = "";
                            Course selectedCourse = null;
                            boolean validCourseCode = false;

                            //Loop until user enters a valid courseId
                            while (!validCourseCode) {
                                System.out.print("Enter the course ID you want to request: ");
                                courseCode = input.nextLine();

                                // find course from avaliableCoursesList
                                for (Course course : crs.listAvailableCourses()) {
                                    if (course.getCourseId().equals(courseCode)) {
                                        selectedCourse = course;
                                        validCourseCode = true;
                                        break;
                                    }
                                }

                                if (!validCourseCode) {
                                    System.out.println("Invalid course ID. Please try again.");
                                }
                            }

                            // Register when a valid courseCode is entered
                            if (selectedCourse != null) {
                                crs.requestInCourse(selectedCourse, loggedInStudent);

                                System.out.println("Course " + courseCode + " requested successfully.");
                            }
                            break;

                        case 3: // Logout
                            System.out.println("Logging out...");
                            isLoggedIn = false; // Set login status to false to go back to login
                            role = ""; // Reset role
                            input.nextLine();
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (!isLoggedIn) break; // Exit the loop if logged out
                }
            }
        }
    }
}