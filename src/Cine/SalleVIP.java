package Cine;

public class SalleVIP extends Salle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalleVIP(int numero, String nom, int capacite) {
        super(numero, nom, capacite);
    }

    @Override
    public double getPrix() {
        return 60.0;
    }

    @Override
    public String toString() {
        return "SalleVIP{numero=" + numero + ", nom='" + nom + "', capacite=" + capacite + ", prix=" + getPrix() + "}";
    }
}