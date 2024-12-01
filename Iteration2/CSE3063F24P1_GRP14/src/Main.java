//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        DepartmentScheduler scheduler = new DepartmentScheduler();
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\nDepartment Scheduler Menu:");
//            System.out.println("1. Print all courses");
//            System.out.println("2. Update course sections");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    scheduler.printAllCourses();
//                    break;
//                case 2:
//                    System.out.print("Enter course ID to update: ");
//                    String courseId = scanner.next();
//                    scheduler.updateCourseSections(courseId);
//                    break;
//                case 3:
//                    System.out.println("Exiting...");
//                    return;
//                default:
//                    System.out.println("Invalid choice! Please try again.");
//            }
//        }
//    }
//}
