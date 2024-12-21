from typing import List
from Student import Student
from Course import Course
from JsonMethods import JsonMethods

class CourseRegistrationSystem:
    def __init__(self, json_methods, student=None, courses=None):
        """
        Initializes the CourseRegistrationSystem.

        Args:
            json_methods (JsonMethods): The instance of JsonMethods.
            student (Student, optional): The student using the system. Defaults to None.
            courses (list, optional): List of courses. Defaults to None.
        """
        self.json_methods = json_methods
        self._student = student  # Allow None for advisor operations
        self._courses = courses if courses is not None else []


    def add_to_enroll_list(self, course: Course, student: Student):
        """
        Enrolls the student in the course if not already enrolled.
        """
        # Reload student data
        student = self.json_methods.load_student(student.get_studentID())

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
        # Reload student data
        student = self.json_methods.load_student(student.get_student_id())

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
        # Reload student data
        student = self.json_methods.load_student(student.get_student_id())

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
        # Reload student data
        logged_in_student = self.json_methods.load_student(logged_in_student.get_studentID())

        available_courses = []
        taken_courses = []
        failed_courses = []
        passed_courses = []
        passing_grades = {"AA", "BA", "BB", "CB", "CC"}

        # Ensure transcript is a proper object
        transcript = logged_in_student.get_transcript()
        if isinstance(transcript, dict):
            from Transcript import Transcript
            transcript = Transcript(**transcript)

        # Process grades to categorize courses
        for grade in transcript.all_grades():
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
            if course.get_prerequisite() and course.get_prerequisite_lesson_id() not in passed_courses:
                continue

            available_courses.append(course)

        return available_courses

    

    def request_in_course(self, course: Course, student: Student):
        """
        Adds a course to the student's requested courses if eligible.
        """
        # Reload student data
        student = self.json_methods.load_student(student.get_studentID())

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

        # Update the `_student` attribute to reflect the changes
        self._student = self.json_methods.load_student(student.get_studentID())

        print(f"Successfully requested the course: {course.get_course_name()}")


    
