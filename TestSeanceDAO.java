package Cine;

import java.util.List;

public class TestSeanceDAO {
    public static void main(String[] args) {

        SeanceDAO seanceDAO = new SeanceDAO();

        // Ajouter une séance pour X3D dans la salle 4
        seanceDAO.ajouterSeance("X3D", 4, "2026-04-05 21:30");

        // Vendre 5 places
        seanceDAO.vendrePlaces("X3D", 4, 5);

        // Afficher les séances
        System.out.println("Liste des séances dans la base :");
        List<Seance> seances = seanceDAO.listerSeances();

        for (Seance s : seances) {
            System.out.println(s);
        }

        // Chiffre d'affaires
        double ca = seanceDAO.calculerChiffreAffaire();
        System.out.println("Chiffre d'affaire : " + ca + " DH");

        // Taux de remplissage
        double taux = seanceDAO.calculerTauxRemplissage();
        System.out.println("Taux de remplissage moyen : " + String.format("%.2f", taux) + " %");
    }
}