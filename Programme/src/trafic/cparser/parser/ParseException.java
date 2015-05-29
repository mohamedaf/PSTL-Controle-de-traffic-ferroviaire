package trafic.cparser.parser;

/**
 * 
 * @author KOBROSLI - AFFES
 * 
 *         Exception concernant un erreur de parse
 */
public class ParseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * 
     * @param message
     *            : message de l'exception
     */
    public ParseException(String message) {
	super(message);
    }

}
