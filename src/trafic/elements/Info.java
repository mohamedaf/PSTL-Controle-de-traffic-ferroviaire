package trafic.elements;

import trafic.enums.Status;

public class Info {

    private Status status;
    private String chaine;

    public Info(Status status, String chaine) {
	this.status = status;
	this.chaine = chaine;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    public String getChaine() {
	return chaine;
    }

    public void setChaine(String chaine) {
	this.chaine = chaine;
    }
}
