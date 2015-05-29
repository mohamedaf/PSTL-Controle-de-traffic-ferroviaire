package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 * 
 *         Interface de connexion a un serveur et envoi de messages
 */
public interface ICommunicator {

    /**
     * Methode se connectant au serveur
     * 
     * @param host
     *            : serverhost
     * @param port
     *            : port de connexion au serveur
     * @return true si connection reussie false sinon
     */
    public boolean connect(String host, int port);

    /**
     * Methode se connectant au serveur
     * 
     * @return true si connection reussie false sinon
     */
    public boolean connect();

    /**
     * Envoie d'un message au serveur auquel on est connecte
     * 
     * @param msg
     *            : texte a envoyer
     */
    public void sendMsg(String msg);

    /**
     * Ferme la connexion
     */
    public void close();
}
