package trafic.IHM;

import java.awt.Color;

public interface LumiereInterface {

    public void on();

    public void off();

    public void setColorOn(Color c);

    public void setColorOff(Color c);

    public Color getColorOn();

    public Color getColorOff();

    public Color getColor();

    public boolean isOn();

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

}
