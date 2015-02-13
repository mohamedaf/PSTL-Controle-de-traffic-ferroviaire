package trafic.retransmit;

public class DatedMessage {
    private String message;
    private long time;
    private int reqid;

    public DatedMessage(String message, int reqid) {
	super();
	this.message = message;
	this.reqid = reqid;
	this.time = System.currentTimeMillis();
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public long getTime() {
	return time;
    }

    public int getReqid() {
	return reqid;
    }

}
