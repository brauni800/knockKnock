package client;

public class ClientProtocol {
	
	private String[] fromUser;
	private ClientProxy proxy;
	private String ip;
	
	public ClientProtocol(ClientProxy proxy, String ip) {
		this.proxy = proxy;
		this.ip = ip;
	}
	
	public void processInput(String input) {
		this.fromUser = parserFromUser(input);
		if (this.fromUser.length == 2) {
			protocol(this.fromUser);
		} else {
			System.out.println("Error en los datos que solicita el cliente");
		}
	}
	
	private String[] parserFromUser(String fromUser) {
    	return fromUser.split(" ");
    }
	
	private void protocol(String[] fromUser) {
    	switch (fromUser[0]) {
		case "Votar":
		case "VOTAR":
		case "votar":
			this.proxy.vote(fromUser[1], this.ip);
			break;
		default:
			System.out.println("Servicio incorrecto");
			break;
		}
    }
}
