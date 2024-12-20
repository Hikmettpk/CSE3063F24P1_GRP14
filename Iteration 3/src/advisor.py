class Advisor:
    def __init__(self, advisor_id: str, name: str, department: str, ):
        self.advisor_id = advisor_id
        self.name = name
        self.department = department



    def get_advisor_id(self) -> str:
        return self.advisor_id

    def get_name(self) -> str:
        return self.name

    def get_department(self) -> str:
        return self.department

    @classmethod
    def get_all_advisors(self):
        pass

