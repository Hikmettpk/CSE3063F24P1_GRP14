from Staff import Staff
from JsonMethods import JsonMethods
from Course import Course

class DepartmentHead(Staff):
    def __init__(self, username, name, surname, password):
        super().__init__(username, name, surname, password)
        self.json_methods = JsonMethods()

    def display_menu(self):
        """
        Displays the menu options for the DepartmentHead.
        """
        print("1. Add a Course")
        print("2. Remove a Course")
        print("3. Update Course Information")
        print("4. Display All Courses")
        print("5. Logout")

    def get_int_input(self, prompt, error_message="Invalid input. Please enter a number."):
        while True:
            try:
                return int(input(prompt).strip())
            except ValueError:
                print(error_message)

    def get_choice_input(self, prompt, valid_choices, error_message="Invalid input. Please enter a valid choice."):
        while True:
            choice = input(prompt).strip().lower()
            if choice in valid_choices:
                return choice
            print(error_message)

    def display_all_courses(self):
        """
        Displays all courses in a formatted table.
        """
        self.courses = self.json_methods.load_course_json()
        if not self.courses:
            print("No courses available.")
            return

        print(f"{'Course ID':<10} {'Course Name':<45} {'Credits':<10} {'Year':<10} {'Instructor':<25} {'Type':<10}")
        print("-" * 110)
        for course in self.courses:
            print(f"{course.courseId:<10} {course.courseName:<45} {course.credit:<10} {course.year:<10} {course.instructor:<25} {course.type:<10}")

    def add_course(self):
        """
        Adds a new course to the JSON file.
        """
        self.courses = self.json_methods.load_course_json()

        prerequisite = self.get_choice_input("Does the course have prerequisites? (yes/no): ", ["yes", "no"]) == 'yes'

        new_course = Course(
            courseId=input("Enter the Course ID: ").strip(),
            courseName=input("Enter the Course Name: ").strip(),
            credit=self.get_int_input("Enter the Credit Hours: "),
            prerequisite=prerequisite,
            prerequisiteLessonId=input("Enter Prerequisite Lesson ID (or None): ").strip() if prerequisite else "None",
            courseSection=[],
            weeklyCourseCount=self.get_int_input("Enter the Weekly Course Count: "),
            year=self.get_choice_input("Enter the Year of the Course (1, 2, 3, or 4): ", ["1", "2", "3", "4"]),
            instructor=input("Enter the Instructor Name: ").strip(),
            enrollmentCapacity=self.get_int_input("Enter the Enrollment Capacity: "),
            currentCapacity=0,
            type=self.get_choice_input("Enter the Course Type (Mandatory/Elective): ", ["mandatory", "elective"]),
            waitList=[]
        )

        self.courses.append(new_course)
        self.json_methods.update_course_json(self.courses)
        print(f"Course {new_course.courseName} added successfully.")

    def remove_course(self):
        """
        Removes a course from the JSON file.
        """
        self.courses = self.json_methods.load_course_json()
        course_id = input("Enter the Course ID to remove: ").strip()
        self.courses = [course for course in self.courses if course.courseId != course_id]
        self.json_methods.update_course_json(self.courses)
        print(f"Course with ID {course_id} removed successfully.")

    def update_course(self):
        """
        Updates all information of a specific course.
        """
        self.courses = self.json_methods.load_course_json()
        course_id = input("Enter the Course ID to update: ").strip()
        course = next((c for c in self.courses if c.courseId == course_id), None)
        if not course:
            print(f"Course with ID {course_id} not found.")
            return

        print(f"Updating course: {course.courseName}")

        while True:
            print("1. Update Course Name")
            print("2. Update Credit Hours")
            print("3. Update Prerequisites")
            print("4. Update Weekly Course Count")
            print("5. Update Year")
            print("6. Update Instructor Name")
            print("7. Update Enrollment Capacity")
            print("8. Update Type")
            print("9. Exit Update Menu")

            choice = input("Enter your choice: ").strip()

            if choice == "1":
                course.courseName = input("Enter the new Course Name: ").strip()
            elif choice == "2":
                course.credit = self.get_int_input("Enter the new Credit Hours: ")
            elif choice == "3":
                prerequisite = self.get_choice_input("Does the course have prerequisites? (yes/no): ", ["yes", "no"]) == 'yes'
                course.prerequisite = prerequisite
                course.prerequisiteLessonId = input("Enter Prerequisite Lesson ID (or None): ").strip() if prerequisite else "None"
            elif choice == "4":
                course.weeklyCourseCount = self.get_int_input("Enter the Weekly Course Count: ")
            elif choice == "5":
                course.year = self.get_choice_input("Enter the Year of the Course (1, 2, 3, or 4): ", ["1", "2", "3", "4"])
            elif choice == "6":
                course.instructor = input("Enter the new Instructor Name: ").strip()
            elif choice == "7":
                course.enrollmentCapacity = self.get_int_input("Enter the new Enrollment Capacity: ")
            elif choice == "8":
                course.type = self.get_choice_input("Enter the new Course Type (Mandatory/Elective): ", ["mandatory", "elective"])
            elif choice == "9":
                print("Exiting update menu.")
                break
            else:
                print("Invalid choice. Please try again.")

        self.json_methods.update_course_json(self.courses)
        print(f"Course with ID {course.courseId} updated successfully.")