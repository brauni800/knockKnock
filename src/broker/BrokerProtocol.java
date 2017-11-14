package broker;

import org.json.simple.JSONObject;

import model.Archivos;
import model.Service;

public class BrokerProtocol {

	private static final String SERVICIO = "SERVICIO";
	private static final String VOTO = "VOTO";
	public static final String VOTAR = "VOTAR";
	private static final String RESULTADO = "RESULTADO";
	private BrokerProxy proxy;
	
	public BrokerProtocol(BrokerProxy serverProxy) {
		this.proxy = serverProxy;
	}

	public JSONObject processInput(Service input) {
		JSONObject theOutput = null;
		for (int i = 0; i < input.numberOfServices(); i++) {
			switch(input.getService(i)) {
			case VOTAR:
				voteProcess(input);
				break;
			case RESULTADO:
				resultProcess(input);
				break;
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
		new Archivos("Clientes").insertarLinea(clients.getIp());
	}
	
	@SuppressWarnings("unchecked")
	private void voteProcess(Service input) {
		boolean IPlocated = new Archivos("Clientes").buscarElemento(input.getIp());
		if (IPlocated) {
			JSONObject json = new JSONObject();
			json.put(SERVICIO, VOTAR);
			json.put(VOTO, input.getVote());
			proxy.setTheOutputToServer(json);
		} else {
			proxy.setTheOutputToClient("ERROR");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void resultProcess(Service input) {
		JSONObject json = new JSONObject();
		for (int i = 0; i < input.numberOfResults(); i++) {
			json.put(input.getResult(i).getKey(), input.getResult(i).getValue());
		}
		proxy.setTheOutputToClient(json);
	}
}
