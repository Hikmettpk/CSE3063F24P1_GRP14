from User import User
from typing import List
from Student import Student
from Course import Course
from CourseRegistrationSystem import CourseRegistrationSystem

class Advisor(User):
    def __init__(self, username, name, surname, password, advisorID):
        super().__init__(username, name, surname, password)
        self.__advisorID = advisorID
        self.__advisedStudents = []  # List of advised students
        self.__requestedStudents = []  # List of students with course requests

    def get_advisorID(self):
        return self.__advisorID

    def get_advised_students(self):
        return self.__advisedStudents

    def approve_requested_course(self, student: Student, course: Course):
        """
        Approves the requested course for a student.
        """
        if student in self.__requestedStudents:
            student.get_requested_courses().remove(course)
            student.get_enrolled_courses().append(course)
            print(f"Course {course} approved for {student.get_name()} {student.get_surname()}.")
        else:
            print(f"{student.get_name()} {student.get_surname()} has no such course request.")


    def reject_requested_course(self, allStudents: List[Student], crs: CourseRegistrationSystem, student: Student, course: Course):
        """
        Rejects a requested course for a student and removes them from waitlist.
        """
        if student in self.__requestedStudents:
            student.get_requested_courses().remove(course)
            self.remove_from_waitlist(student, course)
            print(f"Course {course} rejected for {student.get_name()} {student.get_surname()}.")
        else:
            print(f"{student.get_name()} {student.get_surname()} has no such course request.")


    def remove_from_waitlist(self, student: Student, course: Course):
        """
        Removes a student from the waitlist of a course.
        """
        course.get_waitlist().remove(student)
        print(f"{student.get_name()} {student.get_surname()} removed from waitlist for course {course}.")

    def __str__(self):
        return f"Advisor: {self.get_name()} {self.get_surname()} (ID: {self.__advisorID})"

    def display_menu(self):
        """
        Displays the menu options specific to the advisor.
        """
        print("\nAdvisor Menu:")
        print("1. View Advised Students")
        print("2. Approve Requested Courses")
        print("3. Reject Requested Courses")
        print("4. Exit")
