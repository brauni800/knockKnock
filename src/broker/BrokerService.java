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
		
		generalProxy.connectClient();
		protocol.registerClients(generalProxy.getInputValueFromClient());
		
		while(generalProxy.getResponseFromClient()) {
			
		}
		
		generalProxy.setTheOutputToServer(protocol.processInput(null));
		
//		BrokerProxy serverProxy = new BrokerProxy(Integer.parseInt(args[2]));
//		serverProxy.connect();
//		BrokerProtocol serverProtocol = new BrokerProtocol(serverProxy);
//		serverProxy.setTheOutput(serverProtocol.processInput(null));
		
		
//		while(clientProxy.getResponseFromServer() && serverProxy.getResponseFromServer()) {
//			JSONObject clientOutput = clientProtocol.processInput(clientProxy.getDataFromServer());
//			JSONObject serverOutput = serverProtocol.processInput(serverProxy.getDataFromServer());
//		}
		
//		int clientsPortNumber = Integer.parseInt(args[0]), serversPorNumber = Integer.parseInt(args[1]);
//		String fromClient, fromServer, inputLine, outputLine;
//		
//		
//		try (
//	            ServerSocket clientsSocket = new ServerSocket(clientsPortNumber);
//	            Socket inOutClientsSocket = clientsSocket.accept();
//				PrintWriter outClients = new PrintWriter(inOutClientsSocket.getOutputStream(), true);
//	            BufferedReader inClients = new BufferedReader(new InputStreamReader(inOutClientsSocket.getInputStream()));
//				
//				ServerSocket serverSocket = new ServerSocket(serversPorNumber);
//				Socket inOutServerSocket = serverSocket.accept();
//				PrintWriter outServer = new PrintWriter(inOutServerSocket.getOutputStream(), true);
//	            BufferedReader inServer = new BufferedReader(new InputStreamReader(inOutServerSocket.getInputStream()));
//	            
//	        ) {
//			outputLine = protocol.processInput(null);
//			outClients.println(outputLine);
//			outServer.println(outputLine);
//			while ((fromClient = inClients.readLine()) != null && (fromServer = inServer.readLine()) != null) {
//				
//			}
//		} catch (IOException e) {
//            System.out.println("Exception caught when trying to listen on port "
//                    + clientsPortNumber + " or listening for a connection");
//                System.out.println(e.getMessage());
//            }
		
//        try {
//        	if (args.length == 1) {
//    			serverSocket = new ServerSocket(portNumber);
//                socket = serverSocket.accept();
//    		} else if (args.length == 2) {
//    			hostName = args[0];
//    			portNumber = Integer.parseInt(args[1]);
//    			socket = new Socket(hostName, portNumber);
//    		}
//    		
//    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            
//            switch (args.length) {
//        	case 1:
//        		serverExecute(out, in);
//        		break;
//        	case 2:
//        		clientExecute(out, in);
//        		break;
//        	}
//        } catch (IOException e) {
//            System.out.println("Exception caught when trying to listen on port "
//                + portNumber + " or listening for a connection");
//            System.out.println(e.getMessage());
//        }
	}
}
