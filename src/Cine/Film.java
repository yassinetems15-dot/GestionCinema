package Cine;

import java.io.Serializable;

public class Film implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titre;
    private String realisateur;

    public Film(String titre, String realisateur) {
        this.titre = titre;
        this.realisateur = realisateur;
    }

    public String getTitre() {
        return titre;
    }

    public String getRealisateur() {
		return realisateur;
	}

	@Override
	public String toString() {
	    return "Film{titre='" + titre + "', realisateur='" + realisateur + "'}";
	}

}