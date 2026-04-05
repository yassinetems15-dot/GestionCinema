package Cine;

public class SalleNormale extends Salle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SalleNormale(int numero, String nom, int capacite) {
        super(numero, nom, capacite);
    }

    @Override
    public double getPrix() {
        return 30.0;
    }

    @Override
    public String toString() {
        return "SalleNormale{numero=" + numero + ", nom='" + nom + "', capacite=" + capacite + ", prix=" + getPrix() + "}";
    }
}