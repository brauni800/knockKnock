package broker;

import java.io.IOException;

public class BrokerService {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
            System.err.println("Usage: java BrokerServer <hostname from server> <client port number> <server port number>");
            System.exit(1);
        }

		BrokerProxy generalProxy = new BrokerProxy(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		BrokerProtocol protocol = new BrokerProtocol(generalProxy);
		
		generalProxy.connectServer();
		protocol.registerServices(generalProxy.getInputValueFromServer());
		generalProxy.setTheOutputToServer("REGISTRADO");
		
		generalProxy.connectClient();
		protocol.registerClients(generalProxy.getInputValueFromClient());
		generalProxy.setTheOutputToClient("REGISTRADO");
		
		while(generalProxy.getResponseFromClient()) {
			generalProxy.setTheOutputToServer(protocol.processInput(generalProxy.getDataFromClient()));
		}
	}
}
