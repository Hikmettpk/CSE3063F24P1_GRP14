class CourseSection:
    def __init__(self, day: str = "", place: str = "", hour: str = ""):
        self.day = day
        self.place = place
        self.hour = hour

    # Getter methods
    def getDay(self) -> str:
        return self.day

    def getPlace(self) -> str:
        return self.place

    def getHour(self) -> str:
        return self.hour

    # Utility method
    def __str__(self) -> str:
        return f"Day: {self.day}, Place: {self.place}, Hour: {self.hour}"

