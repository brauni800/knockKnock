package broker;

import org.json.simple.JSONObject;

import model.Archivos;

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

	public void registerServices(String[][] services) {
		for (int i = 0; i < services.length; i++) {
			if (services[i][0].equals("SERVICIO")) {
				new Archivos("Servicios").insertarServicio(services[i][1]);
			}
		}
	}
	
	public void registerClients(String[][] clients) {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i][0].equals("REGISTRAR")) {
				new Archivos("Clientes").insertarServicio(clients[i][1]);
			}
		}
	}
}
