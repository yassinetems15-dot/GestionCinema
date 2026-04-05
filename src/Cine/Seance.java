package Cine;

import java.io.Serializable;

public class Seance implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Film film;
    private Salle salle;
    private String date;
    private int placesVendues;

    public Seance(Film film, Salle salle, String date) {
        this.film = film;
        this.salle = salle;
        this.date = date;
        this.placesVendues = 0;
    }

    public Film getFilm() {
        return film;
    }

    public Salle getSalle() {
        return salle;
    }

    public String getDate() {
        return date;
    }

    public int getPlacesVendues() {
        return placesVendues;
    }

    public int getPlacesRestantes() {
        return salle.getCapacite() - placesVendues;
    }

    public void vendrePlace(int nombre) throws PlaceIndisponibleException {
        if (nombre <= 0) {
            throw new PlaceIndisponibleException("Le nombre de places doit être supérieur à 0.");
        }

        if (placesVendues + nombre > salle.getCapacite()) {
            throw new PlaceIndisponibleException("Places insuffisantes dans la salle.");
        }

        placesVendues += nombre;
    }

    public double getRecette() {
        return placesVendues * salle.getPrix();
    }

    public double getTauxRemplissage() {
        if (salle.getCapacite() == 0) {
            return 0;
        }
        return ((double) placesVendues / salle.getCapacite()) * 100.0;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "film=" + film.getTitre() +
                ", salle=" + salle.getNom() +
                ", date='" + date + '\'' +
                ", placesVendues=" + placesVendues +
                ", placesRestantes=" + getPlacesRestantes() +
                '}';
    }
}