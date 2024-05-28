from flask_sqlalchemy import SQLAlchemy
from uuid import uuid4

db = SQLAlchemy()

class Category(db.Model):
    __tablename__ = 'categories'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    name = db.Column(db.String(128), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'category_name': self.name
        }

class Product(db.Model):
    __tablename__ = 'products'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    name = db.Column(db.String(128), nullable=False)
    description = db.Column(db.String(512), nullable=True)
    price = db.Column(db.Float, nullable=False)
    category_id = db.Column(db.String(36), db.ForeignKey('categories.id'), nullable=False)
    stock_quantity = db.Column(db.Integer, nullable=False, default=0)
    manufacturer = db.Column(db.String(128), nullable=False)
    country = db.Column(db.String(128), nullable=False)
    sizes_available = db.Column(db.String(128), nullable=False)  # Хранение размеров в виде строки, например "S,M,L,XL"
    color = db.Column(db.String(64), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'product_name': self.name,
            'description': self.description,
            'price': self.price,
            'category_id': self.category_id,
            'stock_quantity': self.stock_quantity,
            'manufacturer': self.manufacturer,
            'country': self.country,
            'sizes_available': self.sizes_available,  # Преобразование строки в список
            'color': self.color
        }
