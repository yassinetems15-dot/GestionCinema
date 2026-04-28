package Cine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {

    public void ajouterSeance(String titreFilm, int numeroSalle, String dateSeance) {
        String sql = """
            INSERT INTO seance (film_id, salle_id, date_seance, places_vendues)
            VALUES (
                (SELECT id FROM film WHERE LOWER(titre) = LOWER(?) LIMIT 1),
                (SELECT id FROM salle WHERE numero = ? LIMIT 1),
                ?,
                0
            )
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titreFilm);
            ps.setInt(2, numeroSalle);
            ps.setString(3, dateSeance);

            ps.executeUpdate();
            System.out.println("Séance ajoutée dans la base de données.");

        } catch (Exception e) {
            System.out.println("Erreur ajout séance : " + e.getMessage());
        }
    }

    public List<Seance> listerSeances() {
        List<Seance> seances = new ArrayList<>();

        String sql = """
            SELECT 
                f.titre,
                f.realisateur,
                sa.numero,
                sa.nom,
                sa.capacite,
                sa.type_salle,
                se.date_seance,
                se.places_vendues
            FROM seance se
            JOIN film f ON se.film_id = f.id
            JOIN salle sa ON se.salle_id = sa.id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Film film = new Film(
                        rs.getString("titre"),
                        rs.getString("realisateur")
                );

                Salle salle;

                if (rs.getString("type_salle").equalsIgnoreCase("VIP")) {
                    salle = new SalleVIP(
                            rs.getInt("numero"),
                            rs.getString("nom"),
                            rs.getInt("capacite")
                    );
                } else {
                    salle = new SalleNormale(
                            rs.getInt("numero"),
                            rs.getString("nom"),
                            rs.getInt("capacite")
                    );
                }

                Seance seance = new Seance(film, salle, rs.getString("date_seance"));

                int placesVendues = rs.getInt("places_vendues");

                try {
                    if (placesVendues > 0) {
                        seance.vendrePlace(placesVendues);
                    }
                } catch (Exception e) {
                    System.out.println("Erreur chargement places vendues : " + e.getMessage());
                }

                seances.add(seance);
            }

        } catch (Exception e) {
            System.out.println("Erreur liste séances : " + e.getMessage());
        }

        return seances;
    }

    public void vendrePlaces(String titreFilm, int numeroSalle, int nombre) {
        String selectSql = """
            SELECT se.id, se.places_vendues, sa.capacite
            FROM seance se
            JOIN film f ON se.film_id = f.id
            JOIN salle sa ON se.salle_id = sa.id
            WHERE LOWER(f.titre) = LOWER(?) AND sa.numero = ?
            LIMIT 1
        """;

        String updateSql = """
            UPDATE seance
            SET places_vendues = places_vendues + ?
            WHERE id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectPs = conn.prepareStatement(selectSql)) {

            selectPs.setString(1, titreFilm);
            selectPs.setInt(2, numeroSalle);

            ResultSet rs = selectPs.executeQuery();

            if (rs.next()) {
                int idSeance = rs.getInt("id");
                int placesVendues = rs.getInt("places_vendues");
                int capacite = rs.getInt("capacite");

                if (nombre <= 0) {
                    System.out.println("Erreur : le nombre de places doit être supérieur à 0.");
                    return;
                }

                if (placesVendues + nombre > capacite) {
                    System.out.println("Erreur : places insuffisantes.");
                    return;
                }

                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, nombre);
                    updatePs.setInt(2, idSeance);

                    updatePs.executeUpdate();
                    System.out.println(nombre + " places vendues avec succès.");
                }

            } else {
                System.out.println("Erreur : séance introuvable.");
            }

        } catch (Exception e) {
            System.out.println("Erreur vente places : " + e.getMessage());
        }
    }

    public double calculerChiffreAffaire() {
        String sql = """
            SELECT SUM(se.places_vendues * sa.prix) AS total
            FROM seance se
            JOIN salle sa ON se.salle_id = sa.id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Erreur calcul chiffre d'affaire : " + e.getMessage());
        }

        return 0;
    }

    public double calculerTauxRemplissage() {
        String sql = """
            SELECT AVG((se.places_vendues::double precision / sa.capacite) * 100) AS taux
            FROM seance se
            JOIN salle sa ON se.salle_id = sa.id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("taux");
            }

        } catch (Exception e) {
            System.out.println("Erreur calcul taux remplissage : " + e.getMessage());
        }

        return 0;
    }
}