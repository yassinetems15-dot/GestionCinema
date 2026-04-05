package Cine;

import java.util.List;

public interface IUserCinema {
    Film consulterFilm(String titre) throws FilmIntrouvableException;
    Salle consulterSalle(int numero) throws SalleIntrouvableException;
    List<Film> rechercherFilmsParMotCle(String motCle) throws FilmIntrouvableException;
    List<Seance> voirSeances();
    void acheterPlace(String titreFilm, int numeroSalle, int nombre) throws Exception;
}