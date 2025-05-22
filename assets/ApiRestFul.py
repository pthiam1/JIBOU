    # Description: API RESTful pour la gestion des utilisateurs
# Date: 24/07/2021
# Auteur: THIAM PAPA 

from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.engine import URL
from sqlalchemy import create_engine

app = Flask(__name__)
#phpmyadmin
# user = 'root'
# password = 'Xwamb97@'
# host = 'localhost'
# port = '3306'
# database = 'Jibou'

#postgresql
_user = 'postgres'
_password = 'Xwamb97@'
_host = 'localhost'
_port  = '5432'
_database = 'jibou'

# Configuration de la base de données

url = URL.create(
    drivername='postgresql',
    username=_user,
    password=_password,
    host=_host,
    port=_port,
    database=_database
)
engine = create_engine(url)
app.config['SQLALCHEMY_DATABASE_URI'] = url
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


# Création de la table Utilisateurs
class Utilisateurs(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50))
    prenom = db.Column(db.String(50))
    email = db.Column(db.String(50))
    mot_de_passe = db.Column(db.String(50))
    date_inscription = db.Column(db.String(50))

    def __repr__(self):
        return '<Utilisateurs %r>' % self.id + self.nom + self.prenom + self.email + self.mot_de_passe + self.date_inscription
    
    # route principale
    @app.route('/')
    def index():
        return 'Bienvenue sur l\'API RESTful de gestion de Jibou'

    # route pour ajouter un utilisateur
    @app.route('/utilisateurs', methods=['POST'])
    def ajouter_utilisateur():
        data = request.get_json()
        new_user = Utilisateurs(nom=data['nom'], prenom=data['prenom'], email=data['email'], mot_de_passe=data['mot_de_passe'], date_inscription=data['date_inscription'])
        db.session.add(new_user)
        db.session.commit()
        return jsonify({'message': 'Utilisateur ajouté avec succès'})
    
    # route pour afficher tous les utilisateurs
    @app.route('/utilisateurs', methods=['GET'])
    def get_utilisateurs():
        utilisateurs = Utilisateurs.query.all()
        output = []
        for utilisateur in utilisateurs:
            utilisateur_data = {}
            utilisateur_data['id'] = utilisateur.id
            utilisateur_data['nom'] = utilisateur.nom
            utilisateur_data['prenom'] = utilisateur.prenom
            utilisateur_data['email'] = utilisateur.email
            utilisateur_data['mot_de_passe'] = utilisateur.mot_de_passe
            utilisateur_data['date_inscription'] = utilisateur.date_inscription
            output.append(utilisateur_data)
        return jsonify(output)
    
    
    # route pour afficher un utilisateur
    @app.route('/utilisateurs/<id>', methods=['GET'])
    def get_utilisateur(id):
        utilisateur = Utilisateurs.query.get(id)
        utilisateur_data = {}
        utilisateur_data['id'] = utilisateur.id
        utilisateur_data['nom'] = utilisateur.nom
        utilisateur_data['prenom'] = utilisateur.prenom
        utilisateur_data['email'] = utilisateur.email
        utilisateur_data['mot_de_passe'] = utilisateur.mot_de_passe
        utilisateur_data['date_inscription'] = utilisateur.date_inscription
        return jsonify(utilisateur_data)
    
    
class Categories(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50))

    def __repr__(self):
        return '<Categories %r>' % self.id + self.nom
    
    # route pour ajouter une catégorie
    @app.route('/categories', methods=['POST'])
    def ajouter_categorie():
        data = request.get_json()
        new_categorie = Categories(nom=data['nom'])
        db.session.add(new_categorie)
        db.session.commit()
        return jsonify({'message': 'Catégorie ajoutée avec succès'})
    
    # route pour afficher toutes les catégories
    @app.route('/categories', methods=['GET'])
    def get_categories():
        categories = Categories.query.all()
        output = []
        for categorie in categories:
            categorie_data = {}
            categorie_data['id'] = categorie.id
            categorie_data['nom'] = categorie.nom
            output.append(categorie_data)
        return jsonify(output)
    


class Produits(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50))
    description = db.Column(db.String(50))
    prix = db.Column(db.String(50))
    quantite = db.Column(db.String(50))
    date_ajout = db.Column(db.String(50))
    image_path = db.Column(db.String(50))
    categorie_id = db.Column(db.Integer)

    def __repr__(self):
        return '<Produits %r>' % self.id + self.nom + self.description + self.prix + self.quantite + self.date_ajout + self.image_path + self.categorie_id
    
    # route pour ajouter un produit
    @app.route('/produits', methods=['POST'])
    def ajouter_produit():
        data = request.get_json()
        new_produit = Produits(nom=data['nom'], description=data['description'], prix=data['prix'], quantite=data['quantite'], date_ajout=data['date_ajout'], image_path=data['image_path'], categorie_id=data['categorie_id'])
        db.session.add(new_produit)
        db.session.commit()
        return jsonify({'message': 'Produit ajouté avec succès'})
    
    # route pour afficher tous les produits
    @app.route('/produits', methods=['GET'])
    def get_produits():
        produits = Produits.query.all()
        output = []
        for produit in produits:
            produit_data = {}
            produit_data['id'] = produit.id
            produit_data['nom'] = produit.nom
            produit_data['description'] = produit.description
            produit_data['prix'] = produit.prix
            produit_data['quantite'] = produit.quantite
            produit_data['date_ajout'] = produit.date_ajout
            produit_data['image_path'] = produit.image_path
            produit_data['categorie_id'] = produit.categorie_id
            output.append(produit_data)
        return jsonify(output)
    
    # route pour afficher un produit
    @app.route('/produits/<id>', methods=['GET'])
    def get_produit(id):
        produit = Produits.query.get(id)
        produit_data = {}
        produit_data['id'] = produit.id
        produit_data['nom'] = produit.nom
        produit_data['description'] = produit.description
        produit_data['prix'] = produit.prix
        produit_data['quantite'] = produit.quantite
        produit_data['date_ajout'] = produit.date_ajout
        produit_data['image_path'] = produit.image_path
        produit_data['categorie_id'] = produit.categorie_id
        return jsonify(produit_data)
    

class Fournisseurs(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50))
    prenom = db.Column(db.String(50))
    email = db.Column(db.String(50))
    mot_de_passe = db.Column(db.String(50))
    date_inscription = db.Column(db.String(50))

    def __repr__(self):
        return '<Fournisseurs %r>' % self.id + self.nom + self.prenom + self.email + self.mot_de_passe + self.date_inscription
    
    # route pour ajouter un fournisseur
    @app.route('/fournisseurs', methods=['POST'])
    def ajouter_fournisseur():
        data = request.get_json()
        new_fournisseur = Fournisseurs(nom=data['nom'], prenom=data['prenom'], email=data['email'], mot_de_passe=data['mot_de_passe'], date_inscription=data['date_inscription'])
        db.session.add(new_fournisseur)
        db.session.commit()
        return jsonify({'message': 'Fournisseur ajouté avec succès'})
    
    # route pour afficher tous les fournisseurs
    @app.route('/fournisseurs', methods=['GET'])
    def get_fournisseurs():
        fournisseurs = Fournisseurs.query.all()
        output = []
        for fournisseur in fournisseurs:
            fournisseur_data = {}
            fournisseur_data['id'] = fournisseur.id
            fournisseur_data['nom'] = fournisseur.nom
            fournisseur_data['prenom'] = fournisseur.prenom
            fournisseur_data['email'] = fournisseur.email
            fournisseur_data['mot_de_passe'] = fournisseur.mot_de_passe
            fournisseur_data['date_inscription'] = fournisseur.date_inscription
            output.append(fournisseur_data)
        return jsonify(output)
    
    # route pour afficher un fournisseur
    @app.route('/fournisseurs/<id>', methods=['GET'])
    def get_fournisseur(id):
        fournisseur = Fournisseurs.query.get(id)
        fournisseur_data = {}
        fournisseur_data['id'] = fournisseur.id
        fournisseur_data['nom'] = fournisseur.nom
        fournisseur_data['prenom'] = fournisseur.prenom
        fournisseur_data['email'] = fournisseur.email
        fournisseur_data['mot_de_passe'] = fournisseur.mot_de_passe
        fournisseur_data['date_inscription'] = fournisseur.date_inscription
        return jsonify(fournisseur_data)
    
    
class ListeCourses(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nom = db.Column(db.String(50))
    quantite = db.Column(db.String(50))
    utilisateur_id = db.Column(db.Integer)
    produit_id = db.Column(db.Integer)

    def __repr__(self):
        return '<ListeCourses %r>' % self.id + self.nom + self.quantite + self.utilisateur_id + self.produit_id
    
    # route pour ajouter un produit à la liste de courses
    @app.route('/liste_courses', methods=['POST'])
    def ajouter_liste_courses():
        data = request.get_json()
        new_liste_courses = ListeCourses(nom=data['nom'], quantite=data['quantite'], utilisateur_id=data['utilisateur_id'], produit_id=data['produit_id'])
        db.session.add(new_liste_courses)
        db.session.commit()
        return jsonify({'message': 'Produit ajouté à la liste de courses avec succès'})
    
    # route pour afficher tous les produits de la liste de courses
    @app.route('/liste_courses', methods=['GET'])
    def get_liste_courses():
        liste_courses = ListeCourses.query.all()
        output = []
        for produit in liste_courses:
            produit_data = {}
            produit_data['id'] = produit.id
            produit_data['nom'] = produit.nom
            produit_data['quantite'] = produit.quantite
            produit_data['utilisateur_id'] = produit.utilisateur_id
            produit_data['produit_id'] = produit.produit_id
            output.append(produit_data)
        return jsonify(output)
    
        
 
if __name__ == '__main__':
      app.run(host='0.0.0.0', port=5000, debug=True)
    