from DepartmentScheduler import DepartmentScheduler

def main():
    # Initialize DepartmentScheduler
    scheduler = DepartmentScheduler("schedulerUser", "John", "Doe", "password123")

    while True:
        scheduler.display_menu()
        choice = input("Enter your choice: ").strip()

        if choice == "1":
            # View all courses
            scheduler.print_all_courses()

        elif choice == "2":
            # Update course sections
            scheduler.update_course_sections()

        elif choice == "3":
            # Reset one course section
            scheduler.reset_course_section()

        elif choice == "4":
            # Reset all course sections
            scheduler.reset_all_course_sections()

        elif choice == "5":
            # Logout
            print("Logging out...")
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
