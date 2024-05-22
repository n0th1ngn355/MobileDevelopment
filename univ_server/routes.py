from flask import Blueprint, request, jsonify
from models import db, University, Faculty

api_bp = Blueprint('api', __name__)


@api_bp.route('/', methods=['GET'])
def main():
    code = request.args.get('code')
    if code == '10':
        data = University.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    if code == '20':
        data = Faculty.query.all()
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