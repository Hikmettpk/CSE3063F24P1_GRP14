from abc import ABC, abstractmethod
from dataclasses import dataclass, field
from typing import Optional

@dataclass
class User(ABC):
    username: str
    name: str
    surname: str
    password: str

    # Abstract methods for subclasses
    @abstractmethod
    def get_menu(self) -> None:
        pass

    @abstractmethod
    def get_username(self) -> str:
        pass

    @abstractmethod
    def get_name(self) -> str:
        pass

    @abstractmethod
    def get_surname(self) -> str:
        pass

    @abstractmethod
    def get_password(self) -> str:
        pass
