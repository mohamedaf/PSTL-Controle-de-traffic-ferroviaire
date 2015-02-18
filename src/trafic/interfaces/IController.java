package trafic.interfaces;

import trafic.elements.Pcf;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

public interface IController {

    public void setTrain(int id, TrainAction action, TrainDirection direction);

    public void setLight(int id, Color color);

    public Pcf getPCF();

    public void setPCF(Pcf circuit);

}
