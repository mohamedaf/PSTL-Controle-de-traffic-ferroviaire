package trafic.interfaces;

/**
 * Interface de connexion a un serveur et envoi de messages
 * @author KOBROSLI - AFFES
 * 
 */
public interface ICommunicator {

    /**
     * @param host
     * @param port
     * @return
     */
    public boolean connect(String host, int port);

    /**
     * Envoie un message au serveur auquel on est connecte
     * @param msg texte a envoyer
     */
    public void sendMsg(String msg);

    /**
     * Acquitte un message envoye.
     * @param id numero du message envoye
     */
    public void acquit(int id);

    /**
     * Ferme la connexion
     */
    public void close();
}
