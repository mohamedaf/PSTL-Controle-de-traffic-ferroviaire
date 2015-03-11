package trafic.elements;

import trafic.enums.SwitchPos;
import trafic.enums.SwitchType;

public class SwitchEdges {

    private int id;
    private SwitchType type;
    private int trunk;
    private int branch0;
    private int branch1;
    private SwitchPos pos;

    public SwitchEdges() {

    }

    public SwitchEdges(int id, SwitchType type, int trunk, int branch0,
	    int branch1, SwitchPos pos) {
	super();
	this.id = id;
	this.type = type;
	this.trunk = trunk;
	this.branch0 = branch0;
	this.branch1 = branch1;
	this.pos = pos;
    }

    public boolean isTrunk(int id) {
	return id == trunk;
    }

    public boolean isBranch0(int id) {
	return id == branch0;
    }

    public boolean isBranch1(int id) {
	return id == branch1;
    }

    public SwitchPos getPos() {
	return pos;
    }

    public void setPos(SwitchPos pos) {
	this.pos = pos;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public SwitchType getType() {
	return type;
    }

    public void setType(SwitchType type) {
	this.type = type;
    }

    public int getTrunk() {
	return trunk;
    }

    public void setTrunk(int trunk) {
	this.trunk = trunk;
    }

    public int getBranch0() {
	return branch0;
    }

    public void setBranch0(int branch0) {
	this.branch0 = branch0;
    }

    public int getBranch1() {
	return branch1;
    }

    public void setBranch1(int branch1) {
	this.branch1 = branch1;
    }
}
