package broker;

import org.json.simple.JSONObject;

import model.Archivos;
import model.Service;

public class BrokerProtocol {

	private BrokerProxy proxy;
	
	public BrokerProtocol(BrokerProxy serverProxy) {
		this.proxy = serverProxy;
	}

	public JSONObject processInput(String[][] input) {
		JSONObject theOutput = null;
		if (!input.equals(null)) {
			theOutput = protocol(input);
		} else {
			String[] buttonNames = new Archivos("buttons").getButtonsNames();
			String[][] jsonElements = new String[buttonNames.length][2];
			for (int i = 0; i < buttonNames.length; i++) {
				jsonElements[i][0] = buttonNames[i];
				jsonElements[i][1] = "0";
			}
			theOutput = this.proxy.results(jsonElements);
		}
		return theOutput;
	}

	private JSONObject protocol(String[][] input) {
		JSONObject theOutput = null;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				if (input[i][0].equals("")) {
					
				}
			}
		}
		return theOutput;
	}

	public void registerServices(Service services) {
		for (int i = 0; i < services.numberOfServices(); i++) {
			new Archivos("Servicios").insertarLinea(services.getService(i));
		}
	}
	
	public void registerClients(Service clients) {
		for (int i = 0; i < clients.numberOfServices(); i++) {
			new Archivos("Clientes").insertarLinea(clients.getService(i));
		}
	}
}
