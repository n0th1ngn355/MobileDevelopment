from flask import Blueprint, request, jsonify
from models import db, Category, Product

api_bp = Blueprint('api', __name__)


@api_bp.route('/', methods=['GET'])
def main():
    code = request.args.get('code')
    if code == '10':
        data = Category.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    elif code == '20':
        data = Product.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    return jsonify({'error': 'Invalid code'}), 400

@api_bp.route('/category', methods=['POST'])
def manage_category():
    data = request.get_json()
    category_data = data['category']

    if data['action'] == 11:
        new_category = Category(id=category_data['id'], name=category_data['category_name'])
        db.session.add(new_category)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 12:
        category = Category.query.get_or_404(category_data['id'])
        category.name = category_data['category_name']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 13:
        category = Category.query.get_or_404(category_data['id'])
        db.session.delete(category)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400

@api_bp.route('/product', methods=['POST'])
def manage_product():
    data = request.get_json()
    product_data = data['product']
    
    if data['action'] == 21:
        new_product = Product(
            id=product_data['id'],
            name=product_data['product_name'],
            description=product_data.get('description', ''),
            price=product_data['price'],
            category_id=product_data['category_id'],
            stock_quantity=product_data['stock_quantity'],
            manufacturer=product_data['manufacturer'],
            sizes_available=','.join(product_data['sizes_available']),  # Преобразование списка в строку
            color=product_data['color']
        )
        db.session.add(new_product)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 22:
        product = Product.query.get_or_404(product_data['id'])
        product.name = product_data['product_name']
        product.description = product_data.get('description', '')
        product.price = product_data['price']
        product.category_id = product_data['category_id']
        product.stock_quantity = product_data['stock_quantity']
        product.manufacturer = product_data['manufacturer']
        product.sizes_available = ','.join(product_data['sizes_available'])  # Преобразование списка в строку
        product.color = product_data['color']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 23:
        product = Product.query.get_or_404(product_data['id'])
        db.session.delete(product)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400
