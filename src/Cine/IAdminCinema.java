package Cine;

public interface IAdminCinema {
    void ajouterFilm(String login, Film film) throws Exception;
    void ajouterSalle(String login, Salle salle) throws Exception;
    void ajouterSeance(String login, Seance seance) throws Exception;
    double calculerChiffreAffaire(String login);
    double calculerTauxRemplissage(String login);
    void chargerFilmsDepuisFichier(String login, String nomFichier) throws Exception;
    void serialiserDonnees(String login, String nomFichier) throws Exception;
}