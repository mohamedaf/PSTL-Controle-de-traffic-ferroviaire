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

/**
 * Classe parsant les requetes du controller en requetes XML comprehensibles par
 * le serveur, cette classe s'occupe aussi de l'envois de ces requetes au au
 * moniteur apres le parse. Elle permet aussi par le biai de sa methode parse de
 * demander a faire le chemain inverse c'est a dire parser une chaine XML et la
 * transformer en un objet PCF
 * 
 * @author KOBROSLI - AFFES
 *
 */
public class CParser implements IParser, IToXml {
	private IParser parser;
	private ICommunicator communicator;
	private int reqid;

	/**
	 * Constructeur
	 * 
	 * @param controller
	 *            : instance du composant Controller
	 */
	public CParser(Controller controller) {
		this.reqid = 0;
		this.parser = new Parser(controller);
		this.communicator = new SocketCommunicator(parser);
		this.communicator.connect("grimau.dynamic-dns.net", 55558);
	}

	public CParser(Controller controller, String address, int port) {
		this.reqid = 0;
		this.parser = new Parser(controller);
		this.communicator = new SocketCommunicator(parser);
		this.communicator.connect(address, port);
	}

	/**
	 * Constructeur
	 * 
	 * @param controller
	 *            : instance du composant Controller
	 * @param communicator
	 *            : instance du composant gerant la communication avec le
	 *            moniteur (serveur)
	 */
	public CParser(Controller controller, ICommunicator communicator) {
		this.reqid = 0;
		this.parser = new Parser(controller);
		this.communicator = communicator;
		this.communicator.connect();

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
