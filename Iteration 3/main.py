from DepartmentHead import DepartmentHead

def main():
    # Initialize DepartmentHead
    head = DepartmentHead("headUser", "John", "Doe", "password123")

    while True:
        head.display_menu()
        choice = input("Enter your choice: ").strip()

        if choice == "1":
            # Add a course
            head.add_course()

        elif choice == "2":
            # Remove a course
            head.remove_course()

        elif choice == "3":
            # Update course information
            head.update_course()

        elif choice == "4":
            # Display all courses
            head.display_all_courses()

        elif choice == "5":
            # Logout
            print("Logging out...")
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
