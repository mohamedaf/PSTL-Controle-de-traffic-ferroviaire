package trafic.network;

public class Commandes {

    public static String pcf( int reqid, PCFType type, String content ) {
        return "<pcf reqid='" + reqid + "' type='" + type + "'>" + content
                + "</pcf>";
    }

    public static String hello( int id ) {
        return "<hello id='" + id + "'/>";
    }

    public static String info( boolean status, String content ) {
        String s = "<info status='";
        if ( status ) {
            s += "ok";
        } else {
            s += "ko";
        }
        if ( content == null )
            content = "";
        return s + ">" + content + "</info>";
    }

    public static String start() {
        return "<start></start>";
    }

}
