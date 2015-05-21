package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IParser {

    /**
     * Parser du texte xml respectant le DTD de l'ennonce
     * 
     * @param xml
     *            : Texte xml a parser
     */
    public void parse(String xml);

}
