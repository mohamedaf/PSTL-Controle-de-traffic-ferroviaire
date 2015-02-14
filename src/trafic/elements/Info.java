package trafic.elements;

import trafic.enums.Status;

public class Info {

    private Status statuts;
    private String chaine;

    public Info(Status status, String chaine) {
	this.statuts = status;
	this.chaine = chaine;
    }

    public Status getStatus() {
	return statuts;
    }

    public void setStatus(Status status) {
	this.statuts = status;
    }

    public String getChaine() {
	return chaine;
    }

    public void setChaine(String chaine) {
	this.chaine = chaine;
    }
}
