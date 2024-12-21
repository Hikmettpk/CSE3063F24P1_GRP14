from Staff import Staff
from JsonMethods import JsonMethods
from Course import Course

class DepartmentScheduler(Staff):
    def __init__(self, username, name, surname, password):
        super().__init__(username, name, surname, password)
        self.json_methods = JsonMethods()
        self.courses = []

    DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
    HOURS = [
        "8:30-9:20", "9:30-10:20", "10:30-11:20", "11:30-12:20",
        "13:00-13:50", "14:00-14:50", "15:00-15:50", "16:00-16:50",
        "17:00-17:50", "18:00-18:50"
    ]
    PLACES = ["M1-Z01", "M1-Z06", "M1-110", "M2-Z11", "M2-Z06", "M2-110", "M2-103"]

    def display_menu(self):
        """
        Displays the menu options for the DepartmentScheduler.
        """
        print("1. View All Courses")
        print("2. Update Course Sections")
        print("3. Reset One Course Section")
        print("4. Reset All Course Sections")
        print("5. Logout")
        print("Please choose an operation (or 'q' to go back): ")

    def print_all_courses(self):
        """
        Prints all courses in a formatted table.
        """
        self.courses = self.json_methods.load_course_json()
        if not self.courses:
            print("No courses available.")
            return

        print(f"{'Course Code':<10} {'Course Name':<30}")
        for course in self.courses:
            print(f"{course.courseId:<10} {course.courseName:<30}")

    def find_course_by_id(self, course_id):
        """
        Finds a course by its ID.

        Args:
            course_id (str): The ID of the course to find.

        Returns:
            Course: The course object if found, None otherwise.
        """
        self.courses = self.json_methods.load_course_json()
        return next((course for course in self.courses if course.courseId == course_id), None)

    def reset_course_section(self):
        """
        Resets the sections of a specific course.
        """
        self.courses = self.json_methods.load_course_json()
        course_id = input("Enter the course ID to reset sections: ").strip()
        course = self.find_course_by_id(course_id)
        if not course:
            print(f"Course with ID {course_id} not found.")
            return

        course.courseSection = []
        self.json_methods.update_course_json(self.courses)
        print(f"Sections reset for course {course.courseId}.")

    def reset_all_course_sections(self):
        """
        Resets all sections for all courses.
        """
        self.courses = self.json_methods.load_course_json()
        for course in self.courses:
            course.courseSection = []
        self.json_methods.update_course_json(self.courses)
        print("All course sections have been reset.")

    def update_course_sections(self):
        """
        Updates the sections for a specific course based on user input.
        """
        self.courses = self.json_methods.load_course_json()
        course_id = input("Enter the course ID to update sections: ").strip()
        course = self.find_course_by_id(course_id)
        if not course:
            print(f"Course with ID {course_id} not found.")
            return

        sections = []
        print(f"Updating sections for course: {course.courseName}")

        for i in range(course.weeklyCourseCount):
            print(f"Section {i + 1}:")
            day = self.select_option(self.DAYS, "Select a day:")
            hour = self.select_available_hour(day, sections, course)
            if not hour:
                print("No available hours for this day!")
                return

            place = self.select_available_place(day, hour)
            if not place:
                print("No available classrooms for this time slot!")
                return

            sections.append({"day": day, "hour": hour, "place": place})

        course.courseSection = sections
        self.json_methods.update_course_json(self.courses)
        print(f"Sections updated successfully for course {course.courseName}.")

    def select_available_hour(self, day, current_sections, current_course):
        occupied_hours = set()
        current_year = current_course.year

        for section in current_sections:
            if section['day'] == day:
                occupied_hours.add(section['hour'])

        for course in self.courses:
            if course.year == current_year:
                if course.type == "Mandatory" or current_course.type == "Mandatory":
                    for section in course.courseSection:
                        if section['day'] == day:
                            occupied_hours.add(section['hour'])

        available_hours = [hour for hour in self.HOURS if hour not in occupied_hours]
        if not available_hours:
            return None

        return self.select_option(available_hours, "Available hours (conflicts depend on course type):")

    def select_available_place(self, day, hour):
        occupied_places = set()
        for course in self.courses:
            for section in course.courseSection:
                if section['day'] == day and section['hour'] == hour:
                    occupied_places.add(section['place'])

        available_places = [place for place in self.PLACES if place not in occupied_places]
        if not available_places:
            return None

        return self.select_option(available_places, "Available places:")

    def select_option(self, options, prompt):
        """
        Displays a list of options for the user to select from.

        Args:
            options (list): List of options to display.
            prompt (str): Prompt message for the user.

        Returns:
            str: The selected option.
        """
        while True:
            print(prompt)
            for idx, option in enumerate(options, start=1):
                print(f"{idx}. {option}")
            try:
                choice = int(input("Enter your choice: ").strip())
                if 1 <= choice <= len(options):
                    return options[choice - 1]
                else:
                    print("Invalid choice. Please select a valid option.")
            except ValueError:
                print("Invalid input. Please enter a number.")
