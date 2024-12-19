from typing import List
from course_section import CourseSection

class Course:
    def __init__(self,
                 courseId: str = "",
                 courseName: str = "",
                 credit: int = 0,
                 prerequisite: bool = False,
                 prerequisiteLessonId: str = "",
                 courseSection: List[CourseSection] = None,
                 weeklyCourseCount: int = 0,
                 year: int = 0,
                 instructor: str = "",
                 enrollmentCapacity: int = 0,
                 currentCapacity: int = 0,
                 status: str = "",
                 waitList: List[str] = None):

        self._courseId = courseId
        self._courseName = courseName
        self._credit = credit
        self._prerequisite = prerequisite
        self._prerequisiteLessonId = prerequisiteLessonId
        self._courseSection = courseSection if courseSection is not None else []
        self._weeklyCourseCount = weeklyCourseCount
        self._year = year
        self._instructor = instructor
        self._enrollmentCapacity = enrollmentCapacity
        self._currentCapacity = currentCapacity
        self._status = status
        self._waitList = waitList if waitList is not None else []

    # Getter methods
    def getCourseName(self) -> str:
        return self.courseName

    def getCourseId(self) -> str:
        return self.courseId

    def getCredit(self) -> int:
        return self.credit

    def getCourseSection(self) -> List[CourseSection]:
        return self.courseSection

    def getPrerequisiteLessonId(self) -> str:
        return self.prerequisiteLessonId

    def hasPrerequisite(self) -> bool:
        return self.prerequisite

    def getWeeklyCourseCount(self) -> int:
        return self.weeklyCourseCount

    def getYear(self) -> int:
        return self.year

    def getInstructor(self) -> str:
        return self.instructor

    def getEnrollmentCapacity(self) -> int:
        return self.enrollmentCapacity

    def getCurrentCapacity(self) -> int:
        return self.currentCapacity

    def getStatus(self) -> str:
        return self.status

    def getWaitList(self) -> List[str]:
        return self.waitList

    # Utility methods
    def __eq__(self, other) -> bool:
        if not isinstance(other, Course):
            return False
        return self.courseId == other.courseId

    def __hash__(self) -> int:
        return hash(self.courseId)

    def __str__(self) -> str:
        return ("Course{" +
                "courseId='" + self.courseId + '\'' +
                ", courseName='" + self.courseName + '\'' +
                ", credit=" + str(self.credit) +
                ", prerequisite=" + str(self.prerequisite) +
                ", prerequisiteLessonId='" + self.prerequisiteLessonId + '\'' +
                ", courseSection=" + str([str(section) for section in self.courseSection]) +
                ", weeklyCourseCount=" + str(self.weeklyCourseCount) +
                ", year=" + str(self.year) +
                ", instructor='" + self.instructor + '\'' +
                ", enrollmentCapacity=" + str(self.enrollmentCapacity) +
                ", currentCapacity=" + str(self.currentCapacity) +
                ", status='" + self.status + '\'' +
                ", waitList=" + str(self.waitList) +
                '}')
