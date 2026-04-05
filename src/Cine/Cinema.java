package Cine;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cinema implements IUserCinema, IVendeurCinema, IAdminCinema, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Film> films;
    private List<Salle> salles;
    private List<Seance> seances;

    private final String loginVendeur = "vendeur";
    private final String loginAdmin = "admin";

    public Cinema() {
        films = new ArrayList<>();
        salles = new ArrayList<>();
        seances = new ArrayList<>();
    }

    // ========================
    // Méthodes privées utiles
    // ========================

    private void verifierLoginVendeur(String login) {
        if (login == null || !login.equals(loginVendeur)) {
            throw new AuthentificationException("Accès refusé : vendeur non authentifié.");
        }
    }

    private void verifierLoginAdmin(String login) {
        if (login == null || !login.equals(loginAdmin)) {
            throw new AuthentificationException("Accès refusé : administrateur non authentifié.");
        }
    }

    private Seance trouverSeance(String titreFilm, int numeroSalle) throws FilmIntrouvableException, SalleIntrouvableException {
        boolean filmExiste = false;
        boolean salleExiste = false;

        for (Film f : films) {
            if (f.getTitre().equalsIgnoreCase(titreFilm)) {
                filmExiste = true;
                break;
            }
        }

        for (Salle s : salles) {
            if (s.getNumero() == numeroSalle) {
                salleExiste = true;
                break;
            }
        }

        if (!filmExiste) {
            throw new FilmIntrouvableException("Film introuvable : " + titreFilm);
        }

        if (!salleExiste) {
            throw new SalleIntrouvableException("Salle introuvable : " + numeroSalle);
        }

        for (Seance seance : seances) {
            if (seance.getFilm().getTitre().equalsIgnoreCase(titreFilm)
                    && seance.getSalle().getNumero() == numeroSalle) {
                return seance;
            }
        }

        return null;
    }

    // ========================
    // IUserCinema
    // ========================

    @Override
    public Film consulterFilm(String titre) throws FilmIntrouvableException {
        for (Film film : films) {
            if (film.getTitre().equalsIgnoreCase(titre)) {
                return film;
            }
        }
        throw new FilmIntrouvableException("Aucun film trouvé avec le titre : " + titre);
    }

    @Override
    public Salle consulterSalle(int numero) throws SalleIntrouvableException {
        for (Salle salle : salles) {
            if (salle.getNumero() == numero) {
                return salle;
            }
        }
        throw new SalleIntrouvableException("Aucune salle trouvée avec le numéro : " + numero);
    }

    @Override
    public List<Film> rechercherFilmsParMotCle(String motCle) throws FilmIntrouvableException {
        List<Film> resultat = new ArrayList<>();

        for (Film film : films) {
            if (film.getTitre().toLowerCase().contains(motCle.toLowerCase())
                    || film.getRealisateur().toLowerCase().contains(motCle.toLowerCase())) {
                resultat.add(film);
            }
        }

        if (resultat.isEmpty()) {
            throw new FilmIntrouvableException("Aucun film trouvé pour le mot-clé : " + motCle);
        }

        return resultat;
    }

    @Override
    public List<Seance> voirSeances() {
        return seances;
    }

    @Override
    public void acheterPlace(String titreFilm, int numeroSalle, int nombre) throws Exception {
        Seance seance = trouverSeance(titreFilm, numeroSalle);

        if (seance == null) {
            throw new Exception("Séance introuvable pour le film " + titreFilm + " dans la salle " + numeroSalle);
        }

        seance.vendrePlace(nombre);
    }

    // ========================
    // IVendeurCinema
    // ========================

    @Override
    public void vendrePlace(String login, String titreFilm, int numeroSalle, int nombre) throws Exception {
        verifierLoginVendeur(login);

        Seance seance = trouverSeance(titreFilm, numeroSalle);

        if (seance == null) {
            throw new Exception("Séance introuvable pour le film " + titreFilm + " dans la salle " + numeroSalle);
        }

        seance.vendrePlace(nombre);
    }

    // ========================
    // IAdminCinema
    // ========================

    @Override
    public void ajouterFilm(String login, Film film) throws Exception {
        verifierLoginAdmin(login);
        films.add(film);
    }

    @Override
    public void ajouterSalle(String login, Salle salle) throws Exception {
        verifierLoginAdmin(login);
        salles.add(salle);
    }

    @Override
    public void ajouterSeance(String login, Seance seance) throws Exception {
        verifierLoginAdmin(login);
        seances.add(seance);
    }

    @Override
    public double calculerChiffreAffaire(String login) {
        verifierLoginAdmin(login);

        double total = 0;
        for (Seance seance : seances) {
            total += seance.getRecette();
        }
        return total;
    }

    @Override
    public double calculerTauxRemplissage(String login) {
        verifierLoginAdmin(login);

        if (seances.isEmpty()) {
            return 0;
        }

        double somme = 0;
        for (Seance seance : seances) {
            somme += seance.getTauxRemplissage();
        }

        return somme / seances.size();
    }

    @Override
    public void chargerFilmsDepuisFichier(String login, String nomFichier) throws Exception {
        verifierLoginAdmin(login);

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;

                if (ligne.trim().isEmpty()) {
                    continue;
                }

                String[] parties = ligne.split(";");

                if (parties.length != 2) {
                    throw new FichierCinemaException("Erreur ligne " + numeroLigne + " : format incorrect");
                }

                String titre = parties[0].trim();
                String realisateur = parties[1].trim();

                if (titre.isEmpty() || realisateur.isEmpty()) {
                    throw new FichierCinemaException("Erreur ligne " + numeroLigne + " : données manquantes");
                }

                films.add(new Film(titre, realisateur));
            }

        } catch (IOException e) {
            throw new FichierCinemaException("Erreur lors de la lecture du fichier : " + nomFichier, e);
        }
    }

    @Override
    public void serialiserDonnees(String login, String nomFichier) throws Exception {
        verifierLoginAdmin(login);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new FichierCinemaException("Erreur lors de la sérialisation des données.", e);
        }
    }

    // ========================
    // Getters utiles
    // ========================

    public List<Film> getFilms() {
        return films;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public List<Seance> getSeances() {
        return seances;
    }
}