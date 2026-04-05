package Cine;

public class FichierCinemaException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FichierCinemaException(String message) {
        super(message);
    }

    public FichierCinemaException(String message, Throwable cause) {
        super(message, cause);
    }
}