package server;

import model.Service;

public class ServerProtocol {

	private ServerProxy proxy;
	
	public ServerProtocol(ServerProxy proxy) {
		this.proxy = proxy;
	}
	
	public void processInput(Service service) {
		for (int i = 0; i < service.numberOfServices(); i++) {
			if (service.getService(i).equals(ServerProxy.VOTAR)) {
				service.getVote();
			}
		}
	}
	
	public void setTheOutputline() {
		this.proxy.generateJSON();
		this.proxy.setTheOutput();
	}
}
