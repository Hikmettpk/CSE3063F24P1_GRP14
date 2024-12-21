from typing import List
from Grade import Grade

class Transcript:
    def __init__(self, grades_data=None):
        self.__grades = []
        if grades_data:
            if isinstance(grades_data, list):
                # Check if the input is raw JSON data that needs conversion
                if grades_data and isinstance(grades_data[0], dict):
                    for grade_data in grades_data:
                        # Create Grade object from the dictionary data
                        grade = Grade(grade_data['course'], grade_data['gradeValue'])
                        self.__grades.append(grade)
                else:
                    # Input is already a list of Grade objects
                    self.__grades = grades_data

    # Getter method to return all grades
    def all_grades(self) -> List[Grade]:
        return self.__grades

    # Method to provide a string representation of the Transcript
    def __str__(self):
        grades_info = "\n".join(str(grade) for grade in self.__grades) if self.__grades else "No grades available"
        return f"Transcript:\n{grades_info}"