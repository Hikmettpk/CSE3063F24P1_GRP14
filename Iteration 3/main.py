from json_methods import JsonMethods
from Advisor import Advisor
from DepartmentHead import DepartmentHead
from DepartmentScheduler import DepartmentScheduler
from Student import Student

def display_main_menu():
    """Displays the main menu for the program."""
    print("\nMain Menu:")
    print("1. Login as Advisor")
    print("2. Login as Department Head")
    print("3. Login as Department Scheduler")
    print("4. Exit")

def advisor_menu(advisor):
    """Handles the advisor menu."""
    while True:
        print("\nAdvisor Menu:")
        print("1. View Requests")
        print("2. Approve Requests")
        print("3. Logout")
        choice = input("Select an option: ").strip()

        if choice == "1":
            advisor.display_requests()
        elif choice == "2":
            advisor.approve_request()
        elif choice == "3":
            print("Logging out...")
            break
        else:
            print("Invalid choice. Please try again.")

def department_head_menu(department_head):
    """Handles the department head menu."""
    while True:
        department_head.display_menu()
        choice = input("Select an option: ").strip()

        if choice == "5":
            print("Logging out...")
            break

def department_scheduler_menu(department_scheduler):
    """Handles the department scheduler menu."""
    while True:
        department_scheduler.display_menu()
        choice = input("Select an option: ").strip()

        if choice == "5":
            print("Logging out...")
            break

def main():
    """Entry point for the application."""
    json_methods = JsonMethods()

    while True:
        display_main_menu()
        choice = input("Select an option: ").strip()

        if choice == "1":
            advisors = json_methods.load_advisor(120121047)
            if not advisors:
                print("No advisors found. Exiting...")
                continue

            print("\nAvailable Advisors:")
            for idx, advisor in enumerate(advisors):
                print(f"{idx}: {advisor.get_name()} {advisor.get_surname()} - ID: {advisor.get_advisor_id()}")

            try:
                advisor_index = int(input("Select an advisor by index: ").strip())
                advisor_menu(advisors[advisor_index])
            except (ValueError, IndexError):
                print("Invalid selection.")

        elif choice == "2":
            department_head = DepartmentHead("dh_username", "Dept", "Head", "password")
            department_head_menu(department_head)

        elif choice == "3":
            department_scheduler = DepartmentScheduler("ds_username", "Dept", "Scheduler", "password")
            department_scheduler_menu(department_scheduler)

        elif choice == "4":
            print("Exiting program. Goodbye!")
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
