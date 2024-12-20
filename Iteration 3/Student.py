class Student:
    def __init__(self, username, name, surname, password, studentID, enrolledCourses, requestedCourses, transcript, advisor):
        self.__username = username
        self.__name = name
        self.__surname = surname
        self.__password = password
        self.__studentID = studentID
        self.__enrolledCourses = enrolledCourses  # List of enrolled courses
        self.__requestedCourses = requestedCourses  # List of requested courses
        self.__transcript = transcript
        self.__advisor = advisor

    # Getter methods
    def get_username(self):
        return self.__username

    def get_name(self):
        return self.__name

    def get_surname(self):
        return self.__surname

    def get_password(self):
        return self.__password

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

    # Display schedule (stub method, implement as needed)
    def display_schedule(self):
        print("Displaying schedule for:", self.__name)
        # Implementation logic for schedule display goes here

    # Helper methods for schedule management (stub methods)
    def get_day_index(self, day):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
        return days.index(day) if day in days else -1

    def get_hour_index(self, hour):
        return int(hour.split(":")[0]) if ":" in hour else -1

    def get_hour_label(self, index):
        return f"{index}:00 - {index + 1}:00"

    # Method to display menu (stub method, implement as needed)
    def get_menu(self):
        print("Menu for Student:")
        # Implementation logic for displaying menu goes here
    

    def __str__(self):
        return f"Student: {self.__name} {self.__surname} ({self.__studentID})"
