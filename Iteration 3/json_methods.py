import os
import json
from Course import Course
from Student import Student


class JsonMethods:
    def __init__(self, courses_file="./resources/course.json", students_folder="./resources/Students", advisors_folder="./resources/Advisors"):
        self.courses_file = courses_file
        self.students_folder = students_folder
        self.advisors_folder = advisors_folder

    def load_course_json(self):
        """
        Loads the courses from the JSON file and converts them into Course objects.

        Returns:
            list: A list of Course objects.
        """
        try:
            with open(self.courses_file, "r", encoding="utf-8") as file:
                courses_data = json.load(file)
            return [Course(**course) for course in courses_data]
        except FileNotFoundError:
            print("Error: course.json file not found.")
            return []
        except json.JSONDecodeError:
            print("Error: Failed to parse course.json.")
            return []

    def update_course_json(self, courses):
        """
        Updates the courses JSON file with the provided list of Course objects.

        Args:
            courses (list): A list of Course objects to save back to the JSON file.
        """
        try:
            courses_data = [course.to_dict() for course in courses]
            with open(self.courses_file, "w", encoding="utf-8") as file:
                json.dump(courses_data, file, indent=4, ensure_ascii=False)
            print("Courses updated successfully in JSON file.")
        except Exception as e:
            print(f"Error while updating course.json: {e}")

    def load_student(self, student_id):
        """
        Loads a student from the corresponding JSON file based on their student ID.

        Args:
            student_id (str): The ID of the student to load.

        Returns:
            Student: A Student object if the file exists and loads successfully, otherwise None.
        """
        student_file = os.path.join(self.students_folder, f"{student_id}.json")

        try:
            with open(student_file, "r", encoding="utf-8") as file:
                student_data = json.load(file)

            # Parsing nested objects (e.g., Transcript and Courses)
            transcript_data = student_data.get("transcript", {})
            grades = transcript_data.get("grades", [])

            # Convert grades into Course objects
            parsed_grades = [
                {"course": Course(**grade["course"]), "gradeValue": grade["gradeValue"]}
                for grade in grades
            ]

            student_data["transcript"]["grades"] = parsed_grades

            # Convert enrolledCourses and requestedCourses into Course objects
            student_data["enrolledCourses"] = [Course(**course) for course in student_data.get("enrolledCourses", [])]
            student_data["requestedCourses"] = [Course(**course) for course in student_data.get("requestedCourses", [])]

            # Create and return the Student object
            return Student(**student_data)
        except FileNotFoundError:
            print(f"Error: Student file for ID {student_id} not found.")
            return None
        except json.JSONDecodeError:
            print(f"Error: Failed to parse the JSON file for student ID {student_id}.")
            return None
        except Exception as e:
            print(f"Unexpected error while loading student ID {student_id}: {e}")
            return None

    def load_all_students(self):
        """
        Loads all students from the student folder and converts them into Student objects.

        Returns:
            list: A list of Student objects.
        """
        students = []
        try:
            for student_file in os.listdir(self.students_folder):
                if student_file.endswith(".json"):
                    student_id = os.path.splitext(student_file)[0]
                    student = self.load_student(student_id)
                    if student:
                        students.append(student)
            return students
        except Exception as e:
            print(f"Error while loading all students: {e}")
            return []

    # Remove this import from the top and add it locally where needed:
import os
import json
from Advisor import Advisor
from Course import Course
from Student import Student

class JsonMethods:
    def __init__(self, courses_file="./resources/course.json", students_folder="./resources/Students", advisors_folder="./resources/Advisors"):
        self.courses_file = courses_file
        self.students_folder = students_folder
        self.advisors_folder = advisors_folder

    def load_advisor(self, advisor_id):
        """
        Loads an advisor from the corresponding JSON file based on their advisor ID.

        Args:
            advisor_id (str): The ID of the advisor to load.

        Returns:
            Advisor: An Advisor object if the file exists and loads successfully, otherwise None.
        """
        advisor_file = os.path.join(self.advisors_folder, f"{advisor_id}.json")

        try:
            with open(advisor_file, "r", encoding="utf-8") as file:
                advisor_data = json.load(file)

            # Parse advised and requested students
            advised_students = [
                self.load_student_from_folder(student_id) for student_id in advisor_data.get("advisedStudents", [])
                if self.load_student_from_folder(student_id) is not None
            ]

            requested_students = [
                self.load_student_from_folder(student_id) for student_id in advisor_data.get("requestedStudents", [])
                if self.load_student_from_folder(student_id) is not None
            ]

            # Create Advisor object
            advisor = Advisor(
                username=advisor_data.get("username"),
                name=advisor_data.get("name"),
                surname=advisor_data.get("surname"),
                password=advisor_data.get("password"),
                advisor_id=advisor_id
            )

            advisor._advised_students = advised_students
            advisor._requested_students = requested_students

            return advisor
        except FileNotFoundError:
            print(f"Error: Advisor file for ID {advisor_id} not found.")
            return None
        except json.JSONDecodeError:
            print(f"Error: Failed to parse the JSON file for advisor ID {advisor_id}.")
            return None
        except Exception as e:
            print(f"Unexpected error while loading advisor ID {advisor_id}: {e}")
            return None

    def load_student_from_folder(self, student_id):
        """
        Loads a student from the students folder based on their student ID.

        Args:
            student_id (str): The ID of the student to load.

        Returns:
            Student: A Student object if the file exists and loads successfully, otherwise None.
        """
        student_file = os.path.join(self.students_folder, f"{student_id}.json")

        try:
            with open(student_file, "r", encoding="utf-8") as file:
                student_data = json.load(file)

            # Parse enrolled and requested courses
            enrolled_courses = [Course(**course) for course in student_data.get("enrolledCourses", [])]
            requested_courses = [Course(**course) for course in student_data.get("requestedCourses", [])]

            # Create Student object
            return Student(
                username=student_data.get("username"),
                name=student_data.get("name"),
                surname=student_data.get("surname"),
                password=student_data.get("password"),
                studentID=student_data.get("studentID"),
                enrolledCourses=enrolled_courses,
                requestedCourses=requested_courses,
                transcript=student_data.get("transcript"),
                advisor=student_data.get("advisor")
            )
        except FileNotFoundError:
            print(f"Error: Student file for ID {student_id} not found.")
            return None
        except json.JSONDecodeError:
            print(f"Error: Failed to parse the JSON file for student ID {student_id}.")
            return None
        except Exception as e:
            print(f"Unexpected error while loading student ID {student_id}: {e}")
            return None
