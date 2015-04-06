package trafic.cparser;

import trafic.control.Controller;
import trafic.cparser.parser.Parser;
import trafic.cparser.toxml.Commandes;
import trafic.cparser.toxml.PcfToXml;
import trafic.enums.Color;
import trafic.enums.PCFType;
import trafic.enums.Status;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IParser;
import trafic.interfaces.IToXml;
import trafic.network.SocketCommunicator;

public class CParser implements IParser, IToXml {
    private IParser parser;
    private ICommunicator communicator;
    private static int reqid;

    public CParser(Controller controller) {
	parser = new Parser(controller);
	communicator = new SocketCommunicator(parser);
	communicator.connect("grimau.dynamic-dns.net", 55556);
	reqid = 0;
    }

    @Override
    public void setTrainToXml(int id, TrainAction action,
	    TrainDirection direction) {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.set(PcfToXml.train(id, action, direction))));

    }

    @Override
    public void setLightToXml(int id, Color color) {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.set(PcfToXml.light(id, color))));

    }

    @Override
    public void helloToXml(int id) {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.hello(id)));

    }

    @Override
    public void startToXml() {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.start()));

    }

    @Override
    public void byeToXml() {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.bye()));

	communicator.close();

    }

    @Override
    public void infoToXml(Status status, String message) {
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.info(status, message)));

    }

    @Override
    public void parse(String xml) {
	parser.parse(xml);

    }
    
    @Override
    public void setSwitchToXml(int id, SwitchPos pos) {
	int branch;
	if (pos == SwitchPos.b0)
	    branch = 0;
	else
	    branch = 1;
	communicator.sendMsg(Commandes.pcf(reqid++, PCFType.request,
		Commandes.set(Commandes.switchXml(id, branch))));

    }

}
