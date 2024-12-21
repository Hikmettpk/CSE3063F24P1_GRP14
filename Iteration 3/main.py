from DepartmentScheduler import DepartmentScheduler
from DepartmentHead import DepartmentHead
from CourseRegistrationSystem import CourseRegistrationSystem
from JsonMethods import JsonMethods
from Student import Student
from Advisor import Advisor
from Course import Course


def main():
    # Initialize objects
    json_methods = JsonMethods()
    scheduler = DepartmentScheduler("schedulerUser", "John", "Doe", "password123")
    department_head = DepartmentHead("headUser", "Alice", "Smith", "password123")

    # Load data dynamically
    advisors = json_methods.load_all_advisors()
    students = json_methods.load_all_students()
    courses = json_methods.load_course_json()

    while True:
        print("Main Menu:")
        print("1. Department Scheduler Menu")
        print("2. Department Head Menu")
        print("3. Student Menu")
        print("4. Advisor Menu")
        print("q. Quit")
        choice = input("Enter your choice: ").strip()

        if choice.lower() == 'q':
            print("Exiting system...")
            break

        # Department Scheduler Menu
        if choice == "1":
            while True:
                scheduler.display_menu()
                scheduler_choice = input("Enter your choice (or 'b' to go back): ").strip()

                if scheduler_choice.lower() == 'b':
                    break

                if scheduler_choice == "1":
                    scheduler.print_all_courses()
                elif scheduler_choice == "2":
                    scheduler.update_course_sections()
                elif scheduler_choice == "3":
                    scheduler.reset_course_section()
                elif scheduler_choice == "4":
                    scheduler.reset_all_course_sections()
                elif scheduler_choice == "5":
                    print("Logging out...")
                    break
                else:
                    print("Invalid choice. Please try again.")

        # Department Head Menu
        elif choice == "2":
            while True:
                department_head.display_menu()
                head_choice = input("Enter your choice (or 'b' to go back): ").strip()

                if head_choice.lower() == 'b':
                    break

                if head_choice == "1":
                    department_head.add_course()
                elif head_choice == "2":
                    department_head.remove_course()
                elif head_choice == "3":
                    department_head.update_course()
                elif head_choice == "4":
                    department_head.display_all_courses()
                elif head_choice == "5":
                    print("Logging out...")
                    break
                else:
                    print("Invalid choice. Please try again.")

        # Student Menu
        elif choice == "3":
            print("Student Login:")
            username = input("Enter student username: ").strip()
            password = input("Enter student password: ").strip()

            student = next((s for s in students if s.get_username() == username and s.get_password() == password), None)

            if not student:
                print("Invalid credentials. Try again.")
                continue

            crs = CourseRegistrationSystem(json_methods, student, courses)

            while True:
                print("\nStudent Menu:")
                print("1. View Available Courses")
                print("2. Request a Course")
                print("b. Back")
                student_choice = input("Enter your choice: ").strip()

                if student_choice.lower() == 'b':
                    break

                if student_choice == "1":
                    available_courses = crs.list_available_courses(student)
                    for course in available_courses:
                        print(f"{course.get_course_id()} - {course.get_course_name()}")
                elif student_choice == "2":
                    course_id = input("Enter the Course ID to request: ").strip()
                    course = next((c for c in courses if c.get_course_id() == course_id), None)

                    if not course:
                        print("Invalid Course ID.")
                        continue

                    crs.request_in_course(course, student)
                else:
                    print("Invalid choice. Please try again.")

        # Advisor Menu
        elif choice == "4":
            print("Advisor Login:")
            username = input("Enter advisor username: ").strip()
            password = input("Enter advisor password: ").strip()

            advisor = next((a for a in advisors if a.get_username() == username and a.get_password() == password), None)

            if advisor:
                print(f"Welcome, {advisor.get_name()}!")
                advisor.refresh_advised_students()

                while True:
                    print("\nAdvisor Menu:")
                    print("1. View Requests")
                    print("2. Approve a Request")
                    print("3. Reject a Request")
                    print("b. Back")
                    advisor_choice = input("Enter your choice: ").strip()

                    if advisor_choice.lower() == 'b':
                        break

                    if advisor_choice == "1":
                        requests_map = advisor.view_requests()
                    elif advisor_choice == "2":
                        if not requests_map:
                            print("No requests available to approve.")
                            continue
                        request_index = int(input("Enter the request number to approve: ").strip()) - 1
                        advisor.approve_request_by_index(requests_map, request_index)
                    elif advisor_choice == "3":
                        if not requests_map:
                            print("No requests available to reject.")
                            continue
                        request_index = int(input("Enter the request number to reject: ").strip()) - 1
                        advisor.reject_request_by_index(requests_map, request_index)
                    else:
                        print("Invalid choice. Please try again.")
            else:
                print("Invalid credentials. Try again.")

        else:
            print("Invalid choice. Please try again.")


if __name__ == "__main__":
    main()
