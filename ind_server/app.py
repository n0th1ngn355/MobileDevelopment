from flask import Flask
from flask_migrate import Migrate
from config import Config
from models import db, Category, Product
from routes import api_bp

def init_db(db):
    Category.query.delete()
    Product.query.delete()

        # Добавление категорий
    categories = [
        Category(name='Men'),
        Category(name='Women'),
        Category(name='Kids'),
        Category(name='Accessories')
    ]

    db.session.bulk_save_objects(categories)
    db.session.commit()

    c0 = Category.query.filter_by(name="Men").first()
    c1 = Category.query.filter_by(name="Women").first()
    c4 = Category.query.filter_by(name="Kids").first()
    c3 = Category.query.filter_by(name="Accessories").first()
    
    products = [
        Product(
            name='T-shirt',
            description='A comfortable cotton t-shirt',
            price=19.99,
            category_id=c0.id,
            stock_quantity=100,
            manufacturer='Brand A',
            sizes_available=';'.join(['S', 'M', 'L', 'XL']),
            color='Blue'
        ),
        Product(
            name='Jeans',
            description='Stylish blue jeans',
            price=49.99,
            category_id=c0.id,
            stock_quantity=50,
            manufacturer='Brand B',
            sizes_available=';'.join(['32', '34', '36', '38']),
            color='Blue'
        ),
        Product(
            name='Dress',
            description='Elegant evening dress',
            price=99.99,
            category_id=c1.id,
            stock_quantity=30,
            manufacturer='Brand C',
            sizes_available=';'.join(['S', 'M', 'L']),
            color='Red'
        ),
        Product(
            name='Hat',
            description='Stylish summer hat',
            price=14.99,
            category_id=c3.id,
            stock_quantity=150,
            manufacturer='Brand D',
            sizes_available=';'.join(['One Size']),
            color='White'
        )
    ]


    db.session.bulk_save_objects(products)
    db.session.commit()
    print("Database has been initialized with categories and products.")

def create_app():
    app = Flask(__name__)
    app.config.from_object(Config)

    db.init_app(app)

    migrate = Migrate(app, db)
    
    with app.app_context():
        db.create_all()
        #### Инициализация ####
        # init_db(db)


    app.register_blueprint(api_bp, url_prefix='/')

    return app

if __name__ == '__main__':
    app = create_app()
    app.run(debug=True)
