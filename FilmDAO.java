package Cine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    public void ajouterFilm(Film film) {
        String sql = "INSERT INTO film (titre, realisateur) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, film.getTitre());
            ps.setString(2, film.getRealisateur());

            ps.executeUpdate();
            System.out.println("Film ajouté dans la base de données.");

        } catch (Exception e) {
            System.out.println("Erreur ajout film : " + e.getMessage());
        }
    }

    public List<Film> listerFilms() {
        List<Film> films = new ArrayList<>();

        String sql = "SELECT * FROM film";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String titre = rs.getString("titre");
                String realisateur = rs.getString("realisateur");

                Film film = new Film(titre, realisateur);
                films.add(film);
            }

        } catch (Exception e) {
            System.out.println("Erreur liste films : " + e.getMessage());
        }

        return films;
    }

    public Film chercherFilmParTitre(String titreRecherche) {
        String sql = "SELECT * FROM film WHERE LOWER(titre) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titreRecherche);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String titre = rs.getString("titre");
                String realisateur = rs.getString("realisateur");

                return new Film(titre, realisateur);
            }

        } catch (Exception e) {
            System.out.println("Erreur recherche film : " + e.getMessage());
        }

        return null;
    }
}