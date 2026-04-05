package Cine;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        try {
            String loginAdmin = "admin";
            String loginVendeur = "vendeur";

            // 1. Charger les films depuis fichier
            cinema.chargerFilmsDepuisFichier(loginAdmin, "cinema.txt");

            // 2. Ajouter un film
            Film nouveauFilm = new Film("X3D", "Christopher Nolan");
            cinema.ajouterFilm(loginAdmin, nouveauFilm);

            // 3. Ajouter des salles
            Salle salle1 = new SalleNormale(1, "Salle A", 10);
            Salle salle2 = new SalleVIP(4, "Salle VIP", 15);

            cinema.ajouterSalle(loginAdmin, salle1);
            cinema.ajouterSalle(loginAdmin, salle2);

            // 4. Ajouter des séances
            Seance seance1 = new Seance(nouveauFilm, salle1, "2026-04-05 20:00");
            Seance seance2 = new Seance(nouveauFilm, salle2, "2026-04-05 21:30");

            cinema.ajouterSeance(loginAdmin, seance1);
            cinema.ajouterSeance(loginAdmin, seance2);

            // 5. Consulter un film
            Film filmTrouve = cinema.consulterFilm("X3D");
            System.out.println("Film trouvé : " + filmTrouve);

            // 6. Rechercher par mot-clé
            List<Film> resultats = cinema.rechercherFilmsParMotCle("X");
            System.out.println("Résultats de recherche :");
            for (Film film : resultats) {
                System.out.println(film);
            }

            // 7. Acheter une place (utilisateur simple)
            cinema.acheterPlace("X3D", 4, 2);
            System.out.println("Achat de 2 places effectué avec succès.");

            // 8. Vendre plusieurs places (vendeur)
            cinema.vendrePlace(loginVendeur, "X3D", 4, 5);
            System.out.println("Vente de 5 places effectuée par le vendeur.");

            // 9. Afficher les séances
            System.out.println("Liste des séances :");
            for (Seance seance : cinema.voirSeances()) {
                System.out.println(seance);
            }

            // 10. Chiffre d'affaires
            double chiffreAffaire = cinema.calculerChiffreAffaire(loginAdmin);
            System.out.println("Chiffre d'affaire : " + (int) chiffreAffaire + " DH");

            // 11. Taux de remplissage
            double taux = cinema.calculerTauxRemplissage(loginAdmin);
            System.out.println("Taux de remplissage moyen : " + String.format("%.2f", taux) + " %");

            // 12. Sérialisation
            cinema.serialiserDonnees(loginAdmin, "cinema.ser");
            System.out.println("Données sérialisées avec succès.");

        } catch (FilmIntrouvableException e) {
            System.out.println("Erreur film : " + e.getMessage());

        } catch (SalleIntrouvableException e) {
            System.out.println("Erreur salle : " + e.getMessage());

        } catch (PlaceIndisponibleException e) {
            System.out.println("Erreur place : " + e.getMessage());

        } catch (FichierCinemaException e) {
            System.out.println("Erreur fichier : " + e.getMessage());

        } catch (AuthentificationException e) {
            System.out.println("Erreur authentification : " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Erreur générale : " + e.getMessage());

        } finally {
            System.out.println("Fin du programme.");
        }
    }
}