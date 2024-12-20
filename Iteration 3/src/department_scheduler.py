class DepartmentScheduler:
    def __init__(self, username="", password="",name = ""):
        self.username = username
        self.password = password
        self.name = name


    def get_username(self):
        return self.username

    def get_password(self):
        return self.password

    def get_name(self):
        return self.name


    def get_menu(self):
        print("\nDepartment Scheduler Menu:")
        print("1. View All Courses")
        print("2. Update Course Sections")
        print("3. Reset All Course Sections")
        print("4. Logout")

    def print_all_courses(self):
        print("Displaying all courses...")

    def update_course_sections(self, course_id):
        print(f"Updating sections for course: {course_id}")
        return True

    def reset_all_course_sections(self):
        print("Resetting all course sections...")