from typing import List
from Student import Student
from Course import Course
from JsonMethods import JsonMethods

class CourseRegistrationSystem:
    def __init__(self, json_methods: JsonMethods, student: Student = None, courses: List[Course] = None):
        self.json_methods = json_methods  # Instance of JsonMethods
        self._student = student if student is not None else Student()
        self._courses = courses if courses is not None else []

    def add_to_enroll_list(self, course: Course, student: Student):
        """
        Enrolls the student in the course if not already enrolled.
        """
        is_already_enrolled = any(enrolled_course.get_course_id() == course.get_course_id()
                                  for enrolled_course in student.get_enrolled_courses())

        if is_already_enrolled:
            print("Student is already enrolled in this course.")
            return

        # Add course to the student's enrolled courses
        student.get_enrolled_courses().append(course)

        # Save student data to JSON
        self.json_methods.save_student_to_file(student)

        print("Student enrolled in course successfully.")

    def remove_course_from_request_list(self, student: Student, course: Course) -> bool:
        """
        Removes the course from the student's requested courses.
        """
        requested_courses = student.get_requested_courses()
        if course in requested_courses:
            requested_courses.remove(course)
            self.json_methods.save_student_to_file(student)  # Save updated data
            print("Course removed from requested list.")
            return True
        else:
            print("Course is not in the request list.")
            return False

    def remove_course_from_enrolled_list(self, student: Student, course: Course):
        """
        Removes the course from the student's enrolled courses.
        """
        enrolled_courses = student.get_enrolled_courses()
        if course in enrolled_courses:
            enrolled_courses.remove(course)
            self.json_methods.save_student_to_file(student)  # Save updated data
            print("Course removed from enrolled list.")
        else:
            print("Course is not in the enrolled list.")

    def list_available_courses(self, logged_in_student: Student) -> List[Course]:
        """
        Returns a list of courses available for the student to enroll in.
        """
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
        for course in self._courses:
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

    def check_schedule_conflict(self, new_course: Course, student: Student) -> bool:
        """
        Checks for schedule conflicts for the student with the given course.
        """
        all_courses = self.json_methods.load_course_json()

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
                return True

        return False

    def request_in_course(self, course: Course, student: Student):
        """
        Adds a course to the student's requested courses if eligible.
        """
        # Schedule conflict check
        if self.check_schedule_conflict(course, student):
            return

        # Capacity check
        if course.get_current_capacity() >= course.get_enrollment_capacity():
            print("This course is full and cannot accept more students.")
            return

        # Check if the course has already been requested
        if course in student.get_requested_courses():
            print("You have already requested this course.")
            return

        # Add the course to the student's requested courses list
        student.get_requested_courses().append(course)

        # Save updated student data
        self.json_methods.save_student_to_file(student)

        print(f"Successfully requested the course: {course.get_course_name()}")