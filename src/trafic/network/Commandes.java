package trafic.network;

public class Commandes {

	public static String pcf(int reqid, PCFType type, String content) {
		return "<pcf reqid='" + reqid + "' type='" + type + "'>" + content
				+ "</pcf>";
	}

	public static String hello(int id) {
		return "<hello id='" + id + "'/>";
	}

	public static String info(boolean status, String content) {
		String s = "<info status='";
		if (status) {
			s += "ok";
		} else {
			s += "ko";
		}
		if (content == null)
			content = "";
		return s + "'>" + content + "</info>";
	}

	public static String start() {
		return "<start></start>";
	}

	public static String bye() {
		return "<bye></bye>";
	}
	
	public static String set(String msg){
		return "<set>"+msg+"</set>";
	}
	
	
	/**
	 * @param id id du train
	 * @param action true=start, false=stop
	 * @param dir true=forward, false = backward
	 * @return
	 */
	public static String train(int id, boolean action, boolean dir){
		String s = "<train id='"+id+"' action='";
		if(action)
			s+="start";
		else
			s+="stop";
		s+="' dir='";
		if(dir)
			s+="forward";
		else
			s+="backward";
		s+="'/>";
		return s;		
	}

}
