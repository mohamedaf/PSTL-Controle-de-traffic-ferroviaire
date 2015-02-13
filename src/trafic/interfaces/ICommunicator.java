package trafic.interfaces;

public interface ICommunicator {

    public boolean connect(String host, int port);

    public void sendMsg(String msg);


	public void acquit(int id);


    public void close();
}
