package server;

import java.io.IOException;

public class ServerService {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
            System.err.println(
                "Usage: java ServerService <port number>");
            System.exit(1);
        }
		
		ServerProxy proxy = new ServerProxy(Integer.parseInt(args[0]));
		proxy.connect();
		//mandado de servicios al broker
		proxy.sendServices();
		
		ServerProtocol protocol = new ServerProtocol(proxy);
		
		while (proxy.getResponseFromServer()) {
			protocol.processInput(proxy.getDataFromServer());
			protocol.setTheOutputline();
		}
	}
}
