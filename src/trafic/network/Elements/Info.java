package trafic.network.Elements;

import trafic.network.Enum.Status;

public class Info {

    private Status statuts;
    private String chaine;

    public Info(Status statuts, String chaine) {
	this.statuts = statuts;
	this.chaine = chaine;
    }

    public Status getStatuts() {
	return statuts;
    }

    public void setStatuts(Status statuts) {
	this.statuts = statuts;
    }

    public String getChaine() {
	return chaine;
    }

    public void setChaine(String chaine) {
	this.chaine = chaine;
    }
}
