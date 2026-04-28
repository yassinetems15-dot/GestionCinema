# 🎬 Gestion d’un Cinéma - Java

## 📌 Description
Ce projet est une application orientée objet développée en Java permettant de gérer un cinéma.  
Il permet de manipuler des films, des salles et des séances, ainsi que de gérer les ventes de places.
Le projet contient deux versions :
- Main.java : version console
- MainApp.java : version JavaFX connectée à PostgreSQL
---

## 🎯 Objectifs du projet
- Appliquer les concepts de la programmation orientée objet (POO)
- Utiliser les exceptions personnalisées en Java
- Gérer des collections d’objets (List)
- Lire des données depuis un fichier texte
- Implémenter des interfaces
- Sérialiser les données

---

## ⚙️ Fonctionnalités

### 👤 Utilisateur
- Consulter un film par titre
- Rechercher des films par mot-clé
- Consulter les séances disponibles
- Acheter une place

### 🧑‍💼 Vendeur
- Vendre plusieurs places pour une séance

### 👨‍💻 Administrateur
- Ajouter un film
- Ajouter une salle (normale ou VIP)
- Ajouter une séance
- Calculer le chiffre d’affaires
- Calculer le taux de remplissage
- Charger les films depuis un fichier texte
- Sérialiser les données

---

## 🧱 Structure du projet

- `Film` : représente un film
- `Salle` (abstraite) : représente une salle
- `SalleNormale` / `SalleVIP` : types de salles
- `Seance` : représente une séance de projection
- `Cinema` : classe principale de gestion
- Interfaces :
  - `IUserCinema`
  - `IVendeurCinema`
  - `IAdminCinema`

---

## ⚠️ Gestion des exceptions
Le projet utilise des exceptions personnalisées :
- `FilmIntrouvableException`
- `SalleIntrouvableException`
- `PlaceIndisponibleException`
- `AuthentificationException`
- `FichierCinemaException`

---

## 📂 Fichier utilisé
Le fichier `cinema.txt` contient les films sous la forme : titre;realisateur
