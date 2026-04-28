package Cine;

import java.util.List;

public class TestSalleDAO {
    public static void main(String[] args) {

        SalleDAO salleDAO = new SalleDAO();

        // Ajouter des salles
        Salle salle1 = new SalleNormale(1, "Salle A", 10);
        Salle salle4 = new SalleVIP(4, "Salle VIP", 15);

        salleDAO.ajouterSalle(salle1);
        salleDAO.ajouterSalle(salle4);

        // Lister les salles
        List<Salle> salles = salleDAO.listerSalles();

        System.out.println("Liste des salles dans la base :");
        for (Salle s : salles) {
            System.out.println(s);
        }

        // Chercher une salle
        Salle salleTrouvee = salleDAO.chercherSalleParNumero(4);

        if (salleTrouvee != null) {
            System.out.println("Salle trouvée : " + salleTrouvee);
        } else {
            System.out.println("Salle non trouvée.");
        }
    }
}