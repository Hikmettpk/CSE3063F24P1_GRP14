import java.io.IOException;
import java.util.*;

public class SystemController {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods();
        List<Course> courses = jsonMethods.loadAllCourses();
        List<Student> students = jsonMethods.loadAllStudents();
        List<Advisor> advisors = jsonMethods.loadAllAdvisors();

        // DepartmentScheduler'ı JSON'dan yükleme
        DepartmentScheduler departmentScheduler = jsonMethods.loadDepartmentScheduler();
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
                System.out.print("Username (write 'q' to quit): ");
                username = input.nextLine();

                if (username.equalsIgnoreCase("q")) {
                    System.out.println("Exiting program...");
                    return;
                }

                System.out.print("Password: ");
                password = input.nextLine();

                if (username.equals(departmentScheduler.getUsername()) && password.equals(departmentScheduler.getPassword())) {
                    role = "DepartmentScheduler";
                    System.out.println("Login successful, welcome Department Scheduler!");
                    isLoggedIn = true;
                    break;
                } else if (username.startsWith("o")) { // Student check
                    boolean found = false;
                    role = "Student";
                    for (Student student : students) {
                        if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                            found = true;
                            System.out.println("Login successful, welcome " + student.getName() + " (" + role + ")");
                            loggedInStudent = student;
                            isLoggedIn = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Wrong username or password.");
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
                        System.out.println("Wrong username or password.");
                    }
                } else {
                    System.out.println("Wrong username or password.");
                }
            }

            // -----------------------Department Scheduler menu-----------------------------
            if (role.equals("DepartmentScheduler")) {
                while (true) {
                    System.out.println("1. View All Courses");
                    System.out.println("2. Update Course Sections");
                    System.out.println("3. Reset All Course Sections");
                    System.out.println("4. Logout");
                    System.out.print("Please choose an operation (or 'q' to go back): ");
                    String choiceInput = input.nextLine();

                    if (choiceInput.equalsIgnoreCase("q")) {
                        isLoggedIn = false;
                        break;
                    }

                    int choice;
                    try {
                        choice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                        continue;
                    }

                    switch (choice) {
                        case 1: // View All Courses
                            departmentScheduler.printAllCourses();
                            break;

                        case 2: // Update Course Sections
                            System.out.println("Enter the course ID to update sections:");
                            String courseId = input.nextLine();
                            boolean success = departmentScheduler.updateCourseSections(courseId);
                            if (!success) {
                                System.out.println("Failed to update sections. Please try again.");
                            }
                            break;

                        case 3: // Reset All Course Sections
                            System.out.print("Are you sure you want to reset all course sections? (yes/no): ");
                            String confirmation = input.nextLine();
                            if (confirmation.equalsIgnoreCase("yes")) {
                                departmentScheduler.resetAllCourseSections();
                                System.out.println("All course sections have been reset.");
                            } else {
                                System.out.println("Reset operation canceled.");
                            }
                            break;

                        case 4: // Logout
                            System.out.println("Logging out...");
                            isLoggedIn = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (!isLoggedIn) break;
                }
            }

            // -----------------------Advisor menu-----------------------------
            if (role.equals("Advisor")) {
                CourseRegistrationSystem crs = new CourseRegistrationSystem(null, courses);
                while (true) {
                    System.out.println("1. See requests");
                    System.out.println("2. Approve request");
                    System.out.println("3. Reject request");
                    System.out.println("4. Logout");
                    System.out.print("Please choose an operation (or 'q' to go back): ");
                    String choiceInput = input.nextLine();

                    if (choiceInput.equalsIgnoreCase("q")) {
                        isLoggedIn = false;
                        break;
                    }

                    int choice;
                    try {
                        choice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                        continue;
                    }

                    switch (choice) {
                        case 1: // See Requests
                            List<Student> allStudentsWithRequests = new ArrayList<>();
                            System.out.println("Your advised student list:");

                            for (Student student : students) {
                                if (!student.getRequestedCourses().isEmpty()) {
                                    for (Student advisedStudent : loggedInAdvisor.getAdvisedStudents()) {
                                        if (student.getStudentID().equals(advisedStudent.getStudentID())) {
                                            allStudentsWithRequests.add(student);
                                        }
                                    }
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
                            }
                            break;

                        case 2: // Approve Request
                            System.out.print("Enter the request number to approve: ");
                            int approveRequestIndex = input.nextInt();
                            input.nextLine(); // Consume newline

                            int count = 1;
                            Student selectedStudent = null;
                            Course selectedCourse = null;

                            outerLoop:
                            for (Student student : students) {
                                for (Course course : student.getRequestedCourses()) {
                                    if (count == approveRequestIndex) {
                                        selectedStudent = student;
                                        selectedCourse = course;
                                        break outerLoop;
                                    }
                                    count++;
                                }
                            }

                            if (selectedStudent != null && selectedCourse != null) {
                                loggedInAdvisor.approveRequestedCourse(selectedStudent, selectedCourse);
                            } else {
                                System.out.println("Invalid request number.");
                            }
                            break;

                        case 3: // Reject Request
                            System.out.print("Enter the request number to reject: ");
                            int rejectRequestIndex = input.nextInt();
                            input.nextLine(); // Consume newline

                            count = 1;
                            selectedStudent = null;
                            selectedCourse = null;

                            outerLoop:
                            for (Student student : students) {
                                for (Course course : student.getRequestedCourses()) {
                                    if (count == rejectRequestIndex) {
                                        selectedStudent = student;
                                        selectedCourse = course;
                                        break outerLoop;
                                    }
                                    count++;
                                }
                            }

                            if (selectedStudent != null && selectedCourse != null) {
                                loggedInAdvisor.rejectRequestedCourse(selectedStudent, selectedCourse);
                            } else {
                                System.out.println("Invalid request number.");
                            }
                            break;

                        case 4: // Logout
                            System.out.println("Logging out...");
                            isLoggedIn = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (!isLoggedIn) break;
                }
            }


            // -----------------------Student menu-----------------------------
            if (role.equals("Student")) {
                CourseRegistrationSystem crs = new CourseRegistrationSystem(loggedInStudent, courses);
                while (true) {
                    System.out.println("1. View Transcript");
                    System.out.println("2. Request course");
                    System.out.println("3. Enrolled Courses");
                    System.out.println("4. Display Schedule");
                    System.out.println("5. Logout");
                    System.out.print("Please choose an operation (or 'q' to go back): ");
                    String choiceInput = input.nextLine();

                    if (choiceInput.equalsIgnoreCase("q")) {
                        isLoggedIn = false;
                        break;
                    }

                    int choice;
                    try {
                        choice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number or 'q' to go back.");
                        continue;
                    }

                    switch (choice) {
                        case 1: // View Transcript
                            System.out.println("Showing transcript...");
                            System.out.println(loggedInStudent.getTranscript().toString());
                            break;

                        case 2: // Request Course
                            System.out.println("Requesting course...");
                            System.out.println(crs.availableCoursesToString(crs.listAvailableCourses()));

                            String courseCode;
                            Course selectedCourse = null;
                            boolean validCourseCode = false;

                            while (!validCourseCode) {
                                System.out.print("Enter the course ID you want to request (or 'q' to go back): ");
                                courseCode = input.nextLine();

                                if (courseCode.equalsIgnoreCase("q")) {
                                    break;
                                }

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

                            if (selectedCourse != null) {
                                crs.requestInCourse(selectedCourse, loggedInStudent);
                            }
                            break;

                        case 3: // View Enrolled Courses
                            System.out.println("Enrolled courses:");
                            List<Course> enrolledCourses = loggedInStudent.getEnrolledCourses();
                            StringBuilder sb = new StringBuilder();
                            sb.append(String.format("%-10s %-40s\n", "Course ID", "Course Name"));
                            sb.append("------------------------------------------------------------\n");

                            for (Course course : enrolledCourses) {
                                sb.append(String.format("%-10s %-40s\n",
                                        course.getCourseId(),
                                        course.getCourseName()));
                            }

                            System.out.println(sb.toString());
                            break;

                        case 4: //Display schedule
                            System.out.println("Displaying your schedule...");
                            loggedInStudent.displaySchedule(loggedInStudent);

                            break;
                        case 5:
                            System.out.println("Logging out...");
                            isLoggedIn = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }

                    if (!isLoggedIn) break;
                }
            }
        }
    }
}
