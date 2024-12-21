from User import User  # Import User
from typing import List
from Student import Student  # Import Student class
from Course import Course  # Import Course class
from JsonMethods import JsonMethods  # Import JsonMethods for JSON handling

class Advisor(User):
    def __init__(self, username, name, surname, password, advisorID, advisedStudents=None):
        super().__init__(username, name, surname, password)
        self.__advisorID = advisorID
        self.__advisedStudents = advisedStudents if advisedStudents else []
        self.json_methods = JsonMethods()  # JsonMethods nesnesi eklendi


    def get_advisor_id(self):
        """
        Returns the advisor ID.
        """
        return self.__advisorID

    def get_advised_students(self):
        """
        Returns the list of advised students.
        """
        return self.__advisedStudents

    def refresh_advised_students(self):
        """
        Reloads the advised students for this advisor from the JSON file.
        """
        try:
            json_methods = JsonMethods()
            advisor_data = json_methods.load_advisor(self.get_username())
            if advisor_data:
                self.__advisedStudents = advisor_data.get_advised_students()
        except Exception as e:
            print(f"Error refreshing advised students: {e}")

    def view_requests(self):
        print("\nYour advised student list:")
        has_requests = False
        table = []
        table.append(f"{'No':<5} {'Student Name':<20} {'Surname':<20} {'Course ID':<10} {'Course Name':<40}")
        table.append("-" * 95)

        request_no = 1
        requests_map = []

        for student in self.__advisedStudents:
            for course in student.get_requested_courses():
                has_requests = True
                table.append(
                    f"{request_no:<5} {student.get_name():<20} {student.get_surname():<20} {course.get_course_id():<10} {course.get_course_name():<40}"
                )
                requests_map.append((student, course))
                request_no += 1

        if has_requests:
            print("\n".join(table))
        else:
            print("There are no course requests at the moment.")

        return requests_map

    def approve_request_by_index(self, requests_map, index):
        """
        Approves a course request based on the table index.
        """
        if 0 <= index < len(requests_map):
            student, course = requests_map[index]
            self.approve_requested_course(student, course)
        else:
            print("Invalid request number.")

    def reject_request_by_index(self, requests_map, index):
        """
        Rejects a course request based on the table index.
        """
        if 0 <= index < len(requests_map):
            student, course = requests_map[index]
            self.reject_requested_course(student, course)
        else:
            print("Invalid request number.")

    def reject_requested_course(self, student: Student, course: Course):
        if course in student.get_requested_courses():
            student.get_requested_courses().remove(course)

            # Bekleme listesinden ilk öğrenciyi al
            if course.get_wait_list():
                next_student_data = course.get_wait_list().pop(0)  # JSON'dan gelen sözlük
                next_student = self.json_methods.load_student(next_student_data["studentID"])  # Student nesnesine dönüştür

                if next_student:
                    # Öğrencinin `requestedCourses` kısmına ekle
                    next_student.get_requested_courses().append(course)
                    self.json_methods.save_student_to_file(next_student)

                    print(f"{next_student.get_name()} {next_student.get_surname()} is now requesting {course.get_course_name()}.")

            # JSON dosyalarını güncelle
            self.json_methods.save_student_to_file(student)
            self.json_methods.update_course_json([course])
            print(f"Course {course.get_course_name()} rejected for {student.get_name()} {student.get_surname()}.")
        else:
            print("The course is not in the student's requested courses.")


    def reject_requested_course(self, student: Student, course: Course):
        if course in student.get_requested_courses():
            # Remove the course from the student's requested courses
            student.get_requested_courses().remove(course)
            self.json_methods.save_student_to_file(student)  # Update the student's JSON file
            print(f"Course {course.get_course_name()} removed from {student.get_name()} {student.get_surname()}'s requested courses.")

            # Increment the course's capacity
            course.set_current_capacity(course.get_current_capacity() + 1)
            # Check if the waitlist exists and has entries
            wait_list = course.get_wait_list()

            if wait_list and isinstance(wait_list, list) and len(wait_list) > 0:
                # Debug: Display the current waitlist
                print(f"Waitlist before processing for course {course.get_course_name()}: {wait_list}")

                # Get the first student from the waitlist
                next_student_data = wait_list.pop(0)

                # Debug: Print the waitlist entry being processed
                print(f"Processing waitlist entry: {next_student_data}")

                # Convert the data to a `Student` object
                if isinstance(next_student_data, str):
                    next_student = self.json_methods.load_student(next_student_data)
                elif isinstance(next_student_data, dict) and "studentID" in next_student_data:
                    next_student = self.json_methods.load_student(next_student_data["studentID"])
                else:
                    print(f"Error: Invalid waitlist entry format: {next_student_data}")
                    return

                # If the student is successfully loaded, update their requested courses
                if next_student:
                    print(f"Loaded waitlisted student: {next_student.get_name()} {next_student.get_surname()}")

                    # Add the course to the student's requested courses
                    next_student.get_requested_courses().append(course)
                    self.json_methods.save_student_to_file(next_student)  # Save the updated student data
                    print(f"Course {course.get_course_name()} added to {next_student.get_name()} {next_student.get_surname()}'s requested courses.")

                    # Decrement the course's capacity again
                    course.set_current_capacity(course.get_current_capacity() - 1)
                else:
                    print("Failed to load the next student from the waitlist.")
            else:
                print(f"No students in waitlist for course {course.get_course_name()}.")


            # Update the course JSON file
            self.json_methods.update_course_json([course])
            print(f"Course {course.get_course_name()} rejected for {student.get_name()} {student.get_surname()}.")
        else:
            print("The course is not in the student's requested courses.")







    def display_menu(self):
        """
        Displays the Advisor Menu.
        """
        while True:
            print("\nAdvisor Menu:")
            print("1. View Requests")
            print("2. Approve a Request")
            print("3. Reject a Request")
            print("b. Back")
            choice = input("Enter your choice: ").strip()

            if choice.lower() == 'b':
                break

            if choice == "1":
                requests_map = self.view_requests()  # Get the list of requests for approval/rejection
            elif choice == "2":
                if not requests_map:
                    print("No requests available to approve.")
                    continue
                request_index = int(input("Enter the request number to approve: ").strip()) - 1
                self.approve_request_by_index(requests_map, request_index)
            elif choice == "3":
                if not requests_map:
                    print("No requests available to reject.")
                    continue
                request_index = int(input("Enter the request number to reject: ").strip()) - 1
                self.reject_request_by_index(requests_map, request_index)
            else:
                print("Invalid choice. Please try again.")
