from JsonMethods import JsonMethods
from Student import Student

def main():
    json_methods = JsonMethods()
    student_id = input("Enter the student ID: ").strip()
    student: Student = json_methods.load_student(student_id)

    if student:
        # Print student information
        print("\nStudent Information:")
        print(student)
        
        # Get and print transcript information
        print("\nTranscript:")
        transcript = student.get_transcript()
        if transcript and transcript.all_grades():
            for grade in transcript.all_grades():
                course = grade.get_course()
                grade_value = grade.get_grade_value()
                print(f"Course: {course.get_course_name()} - Grade: {grade_value}")
        else:
            print("No grades available in the transcript.")

if __name__ == "__main__":
    main()