package Cine;

import java.util.List;

public class TestFilmDAO {
    public static void main(String[] args) {

        FilmDAO filmDAO = new FilmDAO();

        // Ajouter un film
        Film film = new Film("X3D", "Christopher Nolan");
        filmDAO.ajouterFilm(film);

        // Lister les films
        List<Film> films = filmDAO.listerFilms();

        System.out.println("Liste des films dans la base :");
        for (Film f : films) {
            System.out.println(f);
        }

        // Chercher un film
        Film filmTrouve = filmDAO.chercherFilmParTitre("X3D");

        if (filmTrouve != null) {
            System.out.println("Film trouvé : " + filmTrouve);
        } else {
            System.out.println("Film non trouvé.");
        }
    }
}