from abc import ABC, abstractmethod
from dataclasses import dataclass, field
from typing import List, Optional

@dataclass
class User(ABC):
    username: str
    name: str
    surname: str
    password: str

    # Abstract methods for subclasses
    @abstractmethod
    def get_menu(self) -> None:
        pass

    @abstractmethod
    def get_username(self) -> str:
        pass

    @abstractmethod
    def get_name(self) -> str:
        pass

    @abstractmethod
    def get_surname(self) -> str:
        pass

    @abstractmethod
    def get_password(self) -> str:
        pass


@dataclass
class Student(User):
    student_id: str
    enrolled_courses: List['Course'] = field(default_factory=list)
    requested_courses: List['Course'] = field(default_factory=list)
    transcript: Optional['Transcript'] = None
    advisor: Optional['Advisor'] = None

    def get_menu(self) -> None:
        print("1. View Transcript")
        print("2. Request Course")
        print("3. View Enrolled Courses")
        print("4. Display Schedule")
        print("5. Logout")
        print("Please choose an operation (or 'q' to go back): ")

    def get_username(self) -> str:
        return self.username

    def get_name(self) -> str:
        return self.name

    def get_surname(self) -> str:
        return self.surname

    def get_password(self) -> str:
        return self.password

    def display_schedule(self) -> None:
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        hours = [
            "8:30-9:20", "9:30-10:20", "10:30-11:20", "11:30-12:20",
            "13:00-13:50", "14:00-14:50", "15:00-15:50", "16:00-16:50",
            "17:00-17:50", "18:00-18:50"
        ]

        print(f"{'Hour':<15} {'Monday':<25} {'Tuesday':<25} {'Wednesday':<25} {'Thursday':<25} {'Friday':<25}")
        print("-----------------------------------------------------------------------------------------------------------------------------")

        for hour in hours:
            row = f"{hour:<15}"
            for day in days:
                course_info = ""
                for course in self.enrolled_courses:
                    for section in course.course_section:
                        if section.day == day and section.hour == hour:
                            course_info = f"{course.course_id} ({section.place})"
                row += f"{course_info:<25}"
            print(row)

    def __str__(self) -> str:
        return (f"Student(username='{self.username}', name='{self.name}', surname='{self.surname}', "
                f"student_id='{self.student_id}', enrolled_courses={self.enrolled_courses}, "
                f"requested_courses={self.requested_courses}, transcript={self.transcript})")


# Example placeholder classes
@dataclass
class CourseSection:
    day: str
    hour: str
    place: str

@dataclass
class Course:
    course_id: str
    course_section: List[CourseSection]

@dataclass
class Transcript:
    pass

@dataclass
class Advisor:
    pass
