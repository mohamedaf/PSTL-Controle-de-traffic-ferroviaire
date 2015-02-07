package trafic.network.Elements;

public class PcfStart {

    private int reqid;
    private String type;
    private Info info;

    public PcfStart() {

    }

    public PcfStart(int reqid, String type, Info info) {
	this.reqid = reqid;
	this.type = type;
	this.info = info;
    }

    public int getReqid() {
	return reqid;
    }

    public void setReqid(int reqid) {
	this.reqid = reqid;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Info getInfo() {
	return info;
    }

    public void setInfo(Info info) {
	this.info = info;
    }

}
