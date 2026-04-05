package Cine;

import java.io.Serializable;

public abstract class Salle implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int numero;
    protected String nom;
    protected int capacite;

    public Salle(int numero, String nom, int capacite) {
        this.numero = numero;
        this.nom = nom;
        this.capacite = capacite;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public abstract double getPrix();

    @Override
    public String toString() {
        return "Salle{numero=" + numero + ", nom='" + nom + "', capacite=" + capacite + "}";
    }
}