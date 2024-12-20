from Staff import Staff
from Course import Course 
import json

class JsonMethods:
    def __init__(self, courses_file="./resources/course.json"):
        self.courses_file = courses_file

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

