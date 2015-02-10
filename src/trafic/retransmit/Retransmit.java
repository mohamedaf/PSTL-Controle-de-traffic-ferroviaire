package trafic.retransmit;

import trafic.interfaces.ICommunicator;
import trafic.interfaces.IRetransmit;
import trafic.network.SocketCommunicator;

public class Retransmit implements ICommunicator, IRetransmit {
    private int timeBeforeRetransmit;
    private RetransmitThread retransmitThread;
    private ICommunicator communicator;

    public Retransmit() {
	this.timeBeforeRetransmit = 500;
	this.communicator = new SocketCommunicator();
	this.retransmitThread = new RetransmitThread(timeBeforeRetransmit,
		communicator);
	this.retransmitThread.start();
    }

    public Retransmit(int timeBeforeRetransmit) {
	this.timeBeforeRetransmit = timeBeforeRetransmit;
	this.communicator = new SocketCommunicator();
	this.retransmitThread = new RetransmitThread(timeBeforeRetransmit,
		communicator);
	this.retransmitThread.start();
    }

    @Override
    public void acquit(int reqid) {
	retransmitThread.removeMsg(reqid);
    }

    @Override
    public boolean connect(String host, int port) {
	return communicator.connect(host, port);
    }

    @Override
    public void sendMsg(String msg) {
	// TODO Auto-generated method stub

    }

    @Override
    public String getNextMsg() {
	return communicator.getNextMsg();
    }

    @Override
    public void close() {
	communicator.close();

    }

}
