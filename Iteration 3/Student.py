from User import User
from Transcript import Transcript

class Student(User):
    def __init__(self, username, name, surname, password, studentID, enrolledCourses, requestedCourses, transcript: Transcript, advisor):
        # Call parent class's __init__ method
        super().__init__(username, name, surname, password)
        
        # Initialize Student-specific attributes
        self.__studentID = studentID
        self.__enrolledCourses = enrolledCourses  # List of enrolled courses
        self.__requestedCourses = requestedCourses  # List of requested courses
        self.__transcript = transcript
        self.__advisor = advisor

    def display_menu(self):
        """
        Implementation of abstract method from User class.
        Displays the menu options specific to Student.
        """
        print("\nStudent Menu:")
        print("1. View Transcript")
        print("2. View Schedule")
        print("3. View Enrolled Courses")
        print("4. View Requested Courses")
        print("5. Request Course")
        print("6. Exit")

    # Getter methods - no need to include username, name, surname, password as they're inherited
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

    # Display schedule method
    def display_schedule(self):
        print("Displaying schedule for:", self.get_name())
        # Implementation logic for schedule display goes here

    # Helper methods for schedule management
    def get_day_index(self, day):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
        return days.index(day) if day in days else -1

    def get_hour_index(self, hour):
        return int(hour.split(":")[0]) if ":" in hour else -1

    def get_hour_label(self, index):
        return f"{index}:00 - {index + 1}:00"

    def __str__(self):
        return f"Student: {self.get_name()} {self.get_surname()} ({self.__studentID})"