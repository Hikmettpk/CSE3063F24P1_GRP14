import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemController {
    public static void main(String[] args) throws IOException {
        JSONMethods jsonMethods = new JSONMethods();
        List<Course> courses = jsonMethods.loadAllCourses();
        List<Student> students = new ArrayList<Student>();
        List<Advisor> advisors = new ArrayList<Advisor>();

        String[] studentIds = {
                "150121003",
                "150121010",
                "150121011",
                "150121017",
                "150121047",
                "150121056",
                "150121060",
                "150121065",
                "150121074",
                "150122515"
        };

        String[] advisorIds = {
                "120121047",
                "120121074"
        };

        for (String studentId : studentIds) {
            students.add(jsonMethods.loadStudent(studentId));
        }

        for (String advisorId : advisorIds) {
            advisors.add(jsonMethods.loadAdvisor(advisorId));
        }


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
            //-----------------------------


            // After successful login
            if (role.equals("Advisor")) {
                // Advisor menu
                while (true) {
                    System.out.println("1. See requests\n2. Logout ");
                    System.out.print("Please choose an operation: ");
                    int choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            // Show requests method here
                            System.out.println("Showing requests...");
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

            if (role.equals("Student")) {

                // Student menu
                while (true) {
                    System.out.println("1. View Transcript\n2. Request course:\n3. Logout ");
                    System.out.print("Please choose an operation: ");
                    int choice = input.nextInt();

                    switch (choice) {
                        case 1:
                            System.out.println("Showing transcript...");
                            // Show transcript method here
                            System.out.println(loggedInStudent.getTranscript().toString());
                            break;

                        case 2:
                            System.out.println("Requesting course...");
                            //List all courses
                            //choose list by switch case


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