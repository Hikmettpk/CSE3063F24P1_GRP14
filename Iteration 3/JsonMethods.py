import os
from Staff import Staff
from Course import Course 
from Student import Student
from Grade import Grade
from Transcript import Transcript
import json

class JsonMethods:
    def __init__(self, courses_file="./resources/course.json", students_folder="./resources/Students"):
        self.courses_file = courses_file
        self.students_folder = students_folder

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

            # Parse transcript data
            transcript_data = student_data.get("transcript", {})
            grades = transcript_data.get("grades", [])

            # Convert grades into Grade objects
            grade_objects = []
            for grade in grades:
                course = Course(**grade["course"])
                grade_obj = Grade(course, grade["gradeValue"])
                grade_objects.append(grade_obj)

            # Create Transcript object with grade objects
            transcript = Transcript(grade_objects)
            student_data["transcript"] = transcript

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