class Student:
    def __init__(self, student_id: str = "",username = "", password = "", name: str = ""):
        self.student_id = student_id
        self.name = name
        self.username = username
        self.password = password

        def get_student_id(self) -> str:
            return self.student_id

        def get_username(self) -> str:
            return self.username

        def get_password(self) -> str:
            return self.password

        def get_name(self) -> str:
            return self.name
