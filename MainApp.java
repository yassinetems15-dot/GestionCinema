package Cine;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private FilmDAO filmDAO = new FilmDAO();
    private SalleDAO salleDAO = new SalleDAO();
    private SeanceDAO seanceDAO = new SeanceDAO();

    private TextArea affichage = new TextArea();

    @Override
    public void start(Stage stage) {

        Label titreApp = new Label("Application Gestion d’un Cinéma");
        titreApp.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        affichage.setEditable(false);
        affichage.setPrefHeight(250);

        // ============================
        // Partie Film
        // ============================

        TextField txtTitreFilm = new TextField();
        txtTitreFilm.setPromptText("Titre du film");

        TextField txtRealisateur = new TextField();
        txtRealisateur.setPromptText("Réalisateur");

        Button btnAjouterFilm = new Button("Ajouter film");
        Button btnAfficherFilms = new Button("Afficher films");

        btnAjouterFilm.setOnAction(e -> {
            try {
                String titre = txtTitreFilm.getText();
                String realisateur = txtRealisateur.getText();

                if (titre.isEmpty() || realisateur.isEmpty()) {
                    affichage.appendText("Veuillez saisir le titre et le réalisateur.\n");
                    return;
                }

                Film film = new Film(titre, realisateur);
                filmDAO.ajouterFilm(film);

                affichage.appendText("Film ajouté : " + film + "\n");

                txtTitreFilm.clear();
                txtRealisateur.clear();

            } catch (Exception ex) {
                affichage.appendText("Erreur ajout film : " + ex.getMessage() + "\n");
            }
        });

        btnAfficherFilms.setOnAction(e -> {
            try {
                affichage.appendText("\nListe des films :\n");
                List<Film> films = filmDAO.listerFilms();

                for (Film film : films) {
                    affichage.appendText(film + "\n");
                }

            } catch (Exception ex) {
                affichage.appendText("Erreur affichage films : " + ex.getMessage() + "\n");
            }
        });

        HBox filmBox = new HBox(10, txtTitreFilm, txtRealisateur, btnAjouterFilm, btnAfficherFilms);

        // ============================
        // Partie Salle
        // ============================

        TextField txtNumeroSalle = new TextField();
        txtNumeroSalle.setPromptText("Numéro salle");

        TextField txtNomSalle = new TextField();
        txtNomSalle.setPromptText("Nom salle");

        TextField txtCapacite = new TextField();
        txtCapacite.setPromptText("Capacité");

        ComboBox<String> comboTypeSalle = new ComboBox<>();
        comboTypeSalle.getItems().addAll("Normale", "VIP");
        comboTypeSalle.setValue("Normale");

        Button btnAjouterSalle = new Button("Ajouter salle");
        Button btnAfficherSalles = new Button("Afficher salles");

        btnAjouterSalle.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(txtNumeroSalle.getText());
                String nom = txtNomSalle.getText();
                int capacite = Integer.parseInt(txtCapacite.getText());
                String type = comboTypeSalle.getValue();

                Salle salle;

                if (type.equals("VIP")) {
                    salle = new SalleVIP(numero, nom, capacite);
                } else {
                    salle = new SalleNormale(numero, nom, capacite);
                }

                salleDAO.ajouterSalle(salle);

                affichage.appendText("Salle ajoutée : " + salle + "\n");

                txtNumeroSalle.clear();
                txtNomSalle.clear();
                txtCapacite.clear();

            } catch (NumberFormatException ex) {
                affichage.appendText("Erreur : numéro et capacité doivent être des nombres.\n");

            } catch (Exception ex) {
                affichage.appendText("Erreur ajout salle : " + ex.getMessage() + "\n");
            }
        });

        btnAfficherSalles.setOnAction(e -> {
            try {
                affichage.appendText("\nListe des salles :\n");
                List<Salle> salles = salleDAO.listerSalles();

                for (Salle salle : salles) {
                    affichage.appendText(salle + "\n");
                }

            } catch (Exception ex) {
                affichage.appendText("Erreur affichage salles : " + ex.getMessage() + "\n");
            }
        });

        HBox salleBox = new HBox(10, txtNumeroSalle, txtNomSalle, txtCapacite, comboTypeSalle, btnAjouterSalle, btnAfficherSalles);

        // ============================
        // Partie Séance
        // ============================

        TextField txtTitreSeance = new TextField();
        txtTitreSeance.setPromptText("Film séance");

        TextField txtNumeroSeance = new TextField();
        txtNumeroSeance.setPromptText("Numéro salle");

        TextField txtDateSeance = new TextField();
        txtDateSeance.setPromptText("Date séance");

        Button btnAjouterSeance = new Button("Ajouter séance");
        Button btnAfficherSeances = new Button("Afficher séances");

        btnAjouterSeance.setOnAction(e -> {
            try {
                String titreFilm = txtTitreSeance.getText();
                int numeroSalle = Integer.parseInt(txtNumeroSeance.getText());
                String date = txtDateSeance.getText();

                seanceDAO.ajouterSeance(titreFilm, numeroSalle, date);

                affichage.appendText("Séance ajoutée : " + titreFilm + " dans la salle " + numeroSalle + "\n");

                txtTitreSeance.clear();
                txtNumeroSeance.clear();
                txtDateSeance.clear();

            } catch (NumberFormatException ex) {
                affichage.appendText("Erreur : le numéro de salle doit être un nombre.\n");

            } catch (Exception ex) {
                affichage.appendText("Erreur ajout séance : " + ex.getMessage() + "\n");
            }
        });

        btnAfficherSeances.setOnAction(e -> {
            try {
                affichage.appendText("\nListe des séances :\n");
                List<Seance> seances = seanceDAO.listerSeances();

                for (Seance seance : seances) {
                    affichage.appendText(seance + "\n");
                }

            } catch (Exception ex) {
                affichage.appendText("Erreur affichage séances : " + ex.getMessage() + "\n");
            }
        });

        HBox seanceBox = new HBox(10, txtTitreSeance, txtNumeroSeance, txtDateSeance, btnAjouterSeance, btnAfficherSeances);

        // ============================
        // Vente + statistiques
        // ============================

        TextField txtFilmVente = new TextField();
        txtFilmVente.setPromptText("Film");

        TextField txtSalleVente = new TextField();
        txtSalleVente.setPromptText("Numéro salle");

        TextField txtNombrePlaces = new TextField();
        txtNombrePlaces.setPromptText("Nombre places");

        Button btnVendre = new Button("Vendre places");
        Button btnStats = new Button("Afficher statistiques");

        btnVendre.setOnAction(e -> {
            try {
                String titreFilm = txtFilmVente.getText();
                int numeroSalle = Integer.parseInt(txtSalleVente.getText());
                int nombre = Integer.parseInt(txtNombrePlaces.getText());

                seanceDAO.vendrePlaces(titreFilm, numeroSalle, nombre);

                affichage.appendText(nombre + " places vendues pour " + titreFilm + ".\n");

                txtFilmVente.clear();
                txtSalleVente.clear();
                txtNombrePlaces.clear();

            } catch (NumberFormatException ex) {
                affichage.appendText("Erreur : numéro salle et nombre de places doivent être des nombres.\n");

            } catch (Exception ex) {
                affichage.appendText("Erreur vente : " + ex.getMessage() + "\n");
            }
        });

        btnStats.setOnAction(e -> {
            try {
                double ca = seanceDAO.calculerChiffreAffaire();
                double taux = seanceDAO.calculerTauxRemplissage();

                affichage.appendText("\nChiffre d'affaires : " + ca + " DH\n");
                affichage.appendText("Taux de remplissage moyen : " + String.format("%.2f", taux) + " %\n");

            } catch (Exception ex) {
                affichage.appendText("Erreur statistiques : " + ex.getMessage() + "\n");
            }
        });

        HBox venteBox = new HBox(10, txtFilmVente, txtSalleVente, txtNombrePlaces, btnVendre, btnStats);

        // ============================
        // Layout général
        // ============================

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        root.getChildren().addAll(
                titreApp,
                new Label("Gestion des films"),
                filmBox,
                new Label("Gestion des salles"),
                salleBox,
                new Label("Gestion des séances"),
                seanceBox,
                new Label("Vente et statistiques"),
                venteBox,
                new Label("Affichage"),
                affichage
        );

        Scene scene = new Scene(root, 1100, 650);

        stage.setTitle("Gestion d’un Cinéma - JavaFX + PostgreSQL");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}