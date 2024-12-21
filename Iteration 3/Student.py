from abc import ABC, abstractmethod
from Course import Course
from User import User

class CourseSection:
    def __init__(self, day, place, hour):
        self.day = day
        self.place = place
        self.hour = hour

    def get_day(self):
        return self.day

    def get_place(self):
        return self.place

    def get_hour(self):
        return self.hour

    def __str__(self):
        return f"CourseSection{{place='{self.place}', day='{self.day}', hour='{self.hour}'}}"

class Student(User):
    def __init__(self, username, name, surname, password, studentID, enrolledCourses, requestedCourses, transcript, advisor):
        super().__init__(username, name, surname, password)
        self.__studentID = studentID
        self.__enrolledCourses = enrolledCourses  # List of enrolled courses
        self.__requestedCourses = requestedCourses  # List of requested courses
        self.__transcript = transcript
        self.__advisor = advisor

    # Getter methods
    def get_studentID(self):
        return self.__studentID

    def get_transcript(self):
        return self.__transcript

    def get_advisor(self):
        return self.__advisor

    def get_enrolled_courses(self):
        return self.__enrolledCourses

    def get_requested_courses(self):
        return self.__requestedCourses

    # Display schedule
    def display_schedule(self, json_methods):
        # Check if json_methods is provided
        if json_methods:
            # Refresh enrolled courses from JSON
            student = json_methods.load_student(self.get_studentID())

            if not student:
                print("Error: Could not refresh student data.")
                return

            self.__enrolledCourses = student.get_enrolled_courses()

        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        hours = [
            "8:30-9:20", "9:30-10:20", "10:30-11:20", "11:30-12:20",
            "13:00-13:50", "14:00-14:50", "15:00-15:50", "16:00-16:50",
            "17:00-17:50", "18:00-18:50"
        ]

        # Print table header
        print(f"{'Hour':<15} {'Monday':<25} {'Tuesday':<25} {'Wednesday':<25} {'Thursday':<25} {'Friday':<25}")
        print("-" * 130)

        # Print table content
        for hour in hours:
            row = f"{hour:<15}"
            for day in days:
                course_info = ""
                for course in self.get_enrolled_courses():
                    for section in course.get_course_section():
                        if section["day"] == day and section["hour"] == hour:
                            course_info = f"{course.get_course_id()} ({section['place']})"
                row += f"{course_info:<25}"
            print(row)

    # Helper methods for schedule management (stub methods)
    def get_day_index(self, day):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
        return days.index(day) if day in days else -1

    def get_hour_index(self, hour):
        return int(hour.split(":"[0])) if ":" in hour else -1

    def get_hour_label(self, index):
        return f"{index}:00 - {index + 1}:00"

    # Method to display menu (stub method, implement as needed)
    def display_menu(self):
        print("Menu for Student:")
        # Implementation logic for displaying menu goes here

    def __str__(self):
        return f"Student: {self.name} {self.surname} ({self.__studentID})"
