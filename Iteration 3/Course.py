class Course:
    def __init__(self, courseId, courseName, credit, prerequisite, prerequisiteLessonId, courseSection,
                 weeklyCourseCount, year, instructor, enrollmentCapacity, currentCapacity, type, waitList=None):
        self.courseId = courseId
        self.courseName = courseName
        self.credit = credit
        self.prerequisite = prerequisite
        self.prerequisiteLessonId = prerequisiteLessonId
        self.courseSection = courseSection
        self.weeklyCourseCount = weeklyCourseCount
        self.year = year
        self.instructor = instructor
        self.enrollmentCapacity = enrollmentCapacity
        self.currentCapacity = currentCapacity
        self.type = type
        self.waitList = waitList if waitList is not None else []

    def to_dict(self):
        """
        Converts the Course object into a dictionary.
        """
        return {
            "courseId": self.courseId,
            "courseName": self.courseName,
            "credit": self.credit,
            "prerequisite": self.prerequisite,
            "prerequisiteLessonId": self.prerequisiteLessonId,
            "courseSection": self.courseSection,
            "weeklyCourseCount": self.weeklyCourseCount,
            "year": self.year,
            "instructor": self.instructor,
            "enrollmentCapacity": self.enrollmentCapacity,
            "currentCapacity": self.currentCapacity,
            "type": self.type,
            "waitList": self.waitList
        }
