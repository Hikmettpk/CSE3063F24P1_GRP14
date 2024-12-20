from DepartmentHead import DepartmentHead
from JsonMethods import JsonMethods

def main():
    json_methods = JsonMethods()
    student_id = input("Enter the student ID: ").strip()
    student = json_methods.load_student(student_id)

    if student:
        print(student.__str__())
    else:
        print("Failed to load student.")

if __name__ == "__main__":
    main()



