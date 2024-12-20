from department_scheduler import DepartmentScheduler
from student import Student
from typing import List
from advisor import Advisor

students: List[Student] = []

def system_controller():
    while True:
        username = ""
        password = ""
        role = ""
        is_logged_in = False
        logged_in_student = None
        logged_in_advisor = None

        while not is_logged_in:
            print("Username (write 'q' to quit): ")
            username = input()

            if username.lower() == "q":
                print("Exiting the program...")
                exit()

            print("Password (write 'q' to quit): ")
            password = input()

            # DepartmentScheduler login
            if (username == DepartmentScheduler.username) and (password == DepartmentScheduler.password):
                role = "DepartmentScheduler"
                print(f"Login successful, welcome dear {DepartmentScheduler.get_name()}!")
                is_logged_in = True

            # Student login
            elif username.startswith("o"):  # assuming 'o' is the prefix for students
                role = "Student"
                found = False
                for student in students:
                    if student.username == username and student.password == password:
                        found = True
                        logged_in_student = student
                        print(f"Login successful, welcome dear {student.get_name()}!")
                        is_logged_in = True
                        break

                if not found:
                    print("Wrong username or password.")

            # Advisor login
            elif username.startswith("advisor"):  # assuming 'advisor' is the prefix for advisors
                role = "Advisor"
                found = False
                for advisor in Advisor.get_all_advisors():  # Assuming a method to get all advisors
                    if advisor.username == username and advisor.password == password:
                        found = True
                        logged_in_advisor = advisor
                        print(f"Login successful, welcome dear {advisor.get_name()}!")
                        is_logged_in = True
                        break

                if not found:
                    print("Wrong username or password.")

            else:
                print("Wrong username or password.")



