from typing import List
from course_section import CourseSection

class Course:
    def __init__(self,
                 course_id: str = "",
                 course_name: str = "",
                 credit: int = 0,
                 prerequisite: bool = False,
                 prerequisite_lesson_id: str = "",
                 course_section: List['CourseSection'] = None,
                 weekly_course_count: int = 0,
                 year: int = 0,
                 instructor: str = "",
                 enrollment_capacity: int = 0,
                 current_capacity: int = 0,
                 status: str = "",
                 wait_list: List[str] = None):

        self._course_id = course_id
        self._course_name = course_name
        self._credit = credit
        self._prerequisite = prerequisite
        self._prerequisite_lesson_id = prerequisite_lesson_id
        self._course_section = course_section if course_section is not None else []
        self._weekly_course_count = weekly_course_count
        self._year = year
        self._instructor = instructor
        self._enrollment_capacity = enrollment_capacity
        self._current_capacity = current_capacity
        self._status = status
        self._wait_list = wait_list if wait_list is not None else []

    # Getter methods
    def get_course_name(self) -> str:
        return self._course_name

    def get_course_id(self) -> str:
        return self._course_id

    def get_credit(self) -> int:
        return self._credit

    def get_course_section(self) -> List['CourseSection']:
        return self._course_section

    def get_prerequisite_lesson_id(self) -> str:
        return self._prerequisite_lesson_id

    def has_prerequisite(self) -> bool:
        return self._prerequisite

    def get_weekly_course_count(self) -> int:
        return self._weekly_course_count

    def get_year(self) -> int:
        return self._year

    def get_instructor(self) -> str:
        return self._instructor

    def get_enrollment_capacity(self) -> int:
        return self._enrollment_capacity

    def get_current_capacity(self) -> int:
        return self._current_capacity

    def get_status(self) -> str:
        return self._status

    def get_wait_list(self) -> List[str]:
        return self._wait_list

    # Utility methods
    def __eq__(self, other) -> bool:
        if not isinstance(other, Course):
            return False
        return self._course_id == other._course_id

    def __hash__(self) -> int:
        return hash(self._course_id)

    def __str__(self) -> str:
        return ("Course{" +
                "course_id='" + self._course_id + '\'' +
                ", course_name='" + self._course_name + '\'' +
                ", credit=" + str(self._credit) +
                ", prerequisite=" + str(self._prerequisite) +
                ", prerequisite_lesson_id='" + self._prerequisite_lesson_id + '\'' +
                ", course_section=" + str([str(section) for section in self._course_section]) +
                ", weekly_course_count=" + str(self._weekly_course_count) +
                ", year=" + str(self._year) +
                ", instructor='" + self._instructor + '\'' +
                ", enrollment_capacity=" + str(self._enrollment_capacity) +
                ", current_capacity=" + str(self._current_capacity) +
                ", status='" + self._status + '\'' +
                ", wait_list=" + str(self._wait_list) +
                '}')
