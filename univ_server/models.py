from flask_sqlalchemy import SQLAlchemy
from uuid import uuid4

db = SQLAlchemy()

class University(db.Model):
    __tablename__ = 'universities'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    name = db.Column(db.String(128), nullable=False)
    city = db.Column(db.String(128), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'university_name': self.name,
            'city': self.city
        }

class Faculty(db.Model):
    __tablename__ = 'faculties'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    name = db.Column(db.String(128), nullable=False)
    university_id = db.Column(db.String(36), db.ForeignKey('universities.id'), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'faculty_name': self.name,
            'university_id': self.university_id
        }
    

class Groups(db.Model):
    __tablename__ = 'groups'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    name = db.Column(db.String(128), nullable=False)
    faculty_id = db.Column(db.String(36), db.ForeignKey('faculties.id'), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'group_name': self.name,
            'faculty_id': self.university_id
        }

class Student(db.Model):
    __tablename__ = 'student'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    last_name = db.Column(db.String(128), nullable=False)
    first_name = db.Column(db.String(128), nullable=False)
    middle_name = db.Column(db.String(128), nullable=False)
    birth_date = db.Column(db.)
    faculty_id = db.Column(db.String(36), db.ForeignKey('faculties.id'), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'group_name': self.name,
            'faculty_id': self.university_id
        }