package server;

public class ServerProtocol {

	private ServerProxy proxy;
	
	public ServerProtocol(ServerProxy proxy) {
		this.proxy = proxy;
	}
	
	public void processInput(String[][] input) {
		for (int i = 0; i < input.length; i++) {
			switch(input[i][0]) {
			case ServerProxy.VOTAR:
				this.proxy.vote(input[i][1]);
				break;
			}
		}
	}
	
	public void setTheOutputline() {
		this.proxy.generateJSON();
		this.proxy.setTheOutput();
	}
}
