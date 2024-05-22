from flask import Blueprint, request, jsonify
from models import db, University, Faculty, Group, Student

api_bp = Blueprint('api', __name__)


@api_bp.route('/', methods=['GET'])
def main():
    code = request.args.get('code')
    if code == '10':
        data = University.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    elif code == '20':
        data = Faculty.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    elif code == '30':
        data = Group.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    elif code == '40':
        data = Student.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    return jsonify({'error': 'Invalid code'}), 400

@api_bp.route('/university', methods=['POST'])
def manage_university():
    data = request.get_json()
    univ = data['university']

    if data['action'] == 11:
        new_university = University(id=univ['id'], name=univ['university_name'], city=univ['city'])
        db.session.add(new_university)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 12:
        university = University.query.get_or_404(univ['id'])
        university.name = univ['university_name']
        university.city = univ['city']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 13:
        university = University.query.get_or_404(univ['id'])
        db.session.delete(university)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400

@api_bp.route('/faculty', methods=['POST'])
def manage_faculty():
    data = request.get_json()
    fac = data['faculty']
    
    if data['action'] == 21:
        faculty = Faculty(id=fac['id'],name=fac['faculty_name'], university_id=fac['university_id'])
        db.session.add(faculty)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 22:
        faculty = Faculty.query.get_or_404(fac['id'])
        faculty.name = fac['faculty_name']
        faculty.university_id = fac['university_id']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 23:
        faculty = Faculty.query.get_or_404(fac['id'])
        db.session.delete(faculty)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400


@api_bp.route('/group', methods=['POST'])
def manage_group():
    data = request.get_json()
    it = data['group']
    
    if data['action'] == 31:
        group = Group(id=it['id'], name=it['group_name'], faculty_id=it['faculty_id'])
        db.session.add(group)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 32:
        group = Group.query.get_or_404(it['id'])
        group.name = it['faculty_name']
        group.faculty_id = it['faculty_id']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 33:
        group = Group.query.get_or_404(it['id'])
        db.session.delete(group)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400


@api_bp.route('/student', methods=['POST'])
def manage_student():
    data = request.get_json()
    it = data['student']
    print(data['action'])
    if data['action'] == 41:
        student = Student(
            id=it['id'],
            last_name=it['last_name'],
            first_name=it['first_name'],
            middle_name=it['middle_name'],
            birth_date=it['birth_date'],
            group_id=it['group_id'],
            phone=it['phone'],
            sex=it['sex']
        )
        db.session.add(student)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 42:
        student = Student.query.get_or_404(it['id'])
        student.last_name = it['last_name']
        student.first_name = it['first_name']
        student.middle_name = it['middle_name']
        student.birth_date = it['birth_date']
        student.group_id = it['group_id']
        student.phone = it['phone']
        student.sex = it['sex']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 43:
        student = Student.query.get_or_404(it['id'])
        db.session.delete(student)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400