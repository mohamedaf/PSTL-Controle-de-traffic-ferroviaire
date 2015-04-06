package trafic.interfaces;

import trafic.elements.Pcf;
import trafic.enums.Color;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

public interface IController {

    public void setTrain(int id, TrainAction action, TrainDirection direction,
	    boolean init);

    public void setLight(int id, Color color);

    public Pcf getPCF();

    public void setPCF(Pcf circuit);
    
    public void setSwitch(int id, SwitchPos pos);

}
