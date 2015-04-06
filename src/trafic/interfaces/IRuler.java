package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IRuler extends IUpNotifier {

    /**
     * @param cont
     */
    public void setController(IController cont);
    
    public int getNumScen();

}
