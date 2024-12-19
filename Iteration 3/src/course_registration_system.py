from typing import List
from student import Student
from course import Course

class CourseRegistrationSystem:
    def __init__(self, student: Student = None, courses: List[Course] = None):
        self._student = student if student is not None else Student()
        self._courses = courses if courses is not None else []

    def add_to_enroll_list(self, course: Course, student: Student):
        # Check if student is already enrolled in the course
        is_already_enrolled = any(enrolled_course.get_course_id() == course.get_course_id()
                                  for enrolled_course in student.get_enrolled_courses())

        if is_already_enrolled:
            print("Student is already enrolled in this course.")
            return

            # Add course to the student's enrolled courses
            student.get_enrolled_courses().append(course)

            # Save student data to file
            self.json_methods.save_student_to_file(student)

            print("Student enrolled in course successfully.")

    def remove_course_from_request_list(self, student: Student, course: Course) -> bool:
        # Check if course is in the student's requested courses
        requested_courses = student.get_requested_courses()
        if course in requested_courses:
            requested_courses.remove(course)
            self.json_methods.save_student_to_file(student)  # Save updated data
            return True  # Successfully removed
        else:
            print("Course is not in the request list.")
            return False  # Removal failed

    def remove_course_from_enrolled_list(self, student: Student, course: Course):
        # Check if course is in the student's enrolled courses
        enrolled_courses = student.get_enrolled_courses()
        if course in enrolled_courses:
            enrolled_courses.remove(course)
            self.json_methods.save_student_to_file(student)  # Save updated data
            print("Course removed from enrolled list.")
        else:
            print("Course is not in the enrolled list.")

    def list_available_courses(self, logged_in_student: Student) -> List[Course]:
        available_courses = []
        taken_courses = []
        failed_courses = []
        passed_courses = []
        passing_grades = {"AA", "BA", "BB", "CB", "CC"}

        # Process grades to categorize courses
        for grade in logged_in_student.get_transcript().all_grades():
            grade_value = grade.get_grade_value()
            course = grade.get_course()

            if grade_value in {"FF", "FD"}:
                failed_courses.append(course.get_course_id())
            elif grade_value in passing_grades:
                passed_courses.append(course.get_course_id())
            elif grade_value in {"DD", "DC"}:
                taken_courses.append(course)

        # List courses that are available to enroll
        for course in self.courses:
            if (course.get_course_id() in passed_courses or
                    course in taken_courses or
                    any(c.get_course_id() == course.get_course_id() for c in
                        logged_in_student.get_requested_courses()) or
                    any(c.get_course_id() == course.get_course_id() for c in logged_in_student.get_enrolled_courses())):
                continue

            # Check prerequisites
            if course.has_prerequisite() and course.get_prerequisite_lesson_id() not in passed_courses:
                continue

            available_courses.append(course)

        return available_courses

    def available_courses_to_string(available_courses):
        # Initialize a string builder (in Python, we can use a list and join it later)
        sb = []
        sb.append("{:<10} {:<40}".format("Course ID", "Course Name"))
        sb.append("-" * 60)  # Adding separator line

        # Loop through available courses and format their details
        for course in available_courses:
            sb.append("{:<10} {:<40}".format(course.get_course_id(), course._course_name))

        # Join the list into a single string and return it
        return "\n".join(sb)

    def check_schedule_conflict(new_course, student):
        all_courses = json_methods.load_all_courses()

        # Find the course with matching course_id
        full_course = next((course for course in all_courses if course.get_course_id() == new_course.get_course_id()),
                           None)

        if full_course is None:
            print("Full course data could not be found.")
            return False

        # Check for schedule conflicts with enrolled courses
        for enrolled_course in student.get_enrolled_courses():
            if new_course.is_schedule_conflict(enrolled_course, full_course):
                print(f"Schedule conflict with enrolled course: {enrolled_course.get_course_name()}")
                return True

        # Check for schedule conflicts with requested courses
        for requested_course in student.get_requested_courses():
            if new_course.is_schedule_conflict(requested_course, full_course):
                print(f"Schedule conflict with requested course: {requested_course.get_course_name()}")
                print(f"Please choose one course to keep:")
                print(f"1. {requested_course.get_course_name()}")
                print(f"2. {new_course.get_course_name()}")

                choice = new_course.get_user_choice()
                if choice == 1:
                    print(f"Keeping {requested_course.get_course_name()} and rejecting {full_course.get_course_name()}")
                    return True
                elif choice == 2:
                    student.get_requested_courses().remove(requested_course)
                    json_methods.save_student_to_file(student)
                    print(f"Removed {requested_course.get_course_name()} and added {full_course.get_course_name()}")
                    return False
                else:
                    print("Invalid choice. No action taken.")
                    return True

        return False

    def request_in_course(course, student):
        # Schedule conflict check
        if course.check_schedule_conflict(course, student):
            return  # Return if there's a schedule conflict

        # Capacity check
        if course.get_current_capacity() >= course.get_enrollment_capacity():
            print("This course is full and cannot accept more students.")
            return

        # Check if the course has already been requested
        if course in student.get_requested_courses():
            print("You have already requested this course.")
            return

        # Load all courses and find the full course data
        all_courses = json_methods.load_all_courses()
        full_course = next((c for c in all_courses if c.get_course_id() == course.get_course_id()), None)

        if full_course is None:
            print("Full course data could not be found.")
            return

        # Add the course to the student's requested courses list
        student.get_requested_courses().append(full_course)

        # Update the student's data in JSON
        json_methods.update_student_in_json(student)

        print(f"Successfully requested the course: {course.get_course_name()}")

    def count_requested_students(all_students, course):
        count = 0
        for student in all_students:
            for requested_course in student.get_requested_courses():
                if requested_course.get_course_id() == course.get_course_id():
                    count += 1
                    break
        return count

    def count_enrolled_students(all_students, course):
        count = 0
        for student in all_students:
            for enrolled_course in student.get_enrolled_courses():
                if enrolled_course.get_course_id() == course.get_course_id():
                    count += 1
                    break
        return count

    def is_schedule_conflict(course1, course2):
        for section1 in course1.get_course_section():
            for section2 in course2.get_course_section():
                if section1.get_day() == section2.get_day() and section1.get_hour() == section2.get_hour():
                    return True
        return False

    def get_user_choice(self):
        choice = int(input("Enter your choice (1 or 2): "))
        return choice








