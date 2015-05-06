package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IRuler extends IUpNotifier {

    /**
     * Fournie le controlleur
     * 
     * @param cont
     *            : Controller
     */
    public void setController(IController cont);

    /**
     * 
     * @return numero du scenario de l'objet IRuler
     */
    public int getNumScen();

}
