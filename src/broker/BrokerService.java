package broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

import server.KnockKnockProtocol;

public class BrokerService {

	public static void main(String[] args) {
		if (args.length != 2) {
            System.err.println("Usage: java BrokerServer <client port number> <server port number>");
            System.exit(1);
        }

		BrokerClientProxy clientProxy = new BrokerClientProxy(Integer.parseInt(args[0]));
		clientProxy.connect();
		BrokerProtocol clientProtocol = new BrokerProtocol(clientProxy);
		
		BrokerServerProxy serverProxy = new BrokerServerProxy(Integer.parseInt(args[1]));
		serverProxy.connect();
		BrokerProtocol serverProtocol = new BrokerProtocol(serverProxy);
		
		
		
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

	private static void clientExecute(PrintWriter out, BufferedReader in) {
		String fromServer;
        String fromUser;
        
        //{"Servicio":"Registrar","ip":"192.158.1.0"}
        JSONObject json = new JSONObject();
        json.put("Servicio", "Registrar");
        json.put("ip", "192.158.1.0");
        //System.out.println(json);
        out.println(json);
	}

	private static void serverExecute(PrintWriter out, BufferedReader in) throws IOException {
		String inputLine, outputLine;
        
        // Initiate conversation with client
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        inputLine = in.readLine();
        System.out.println(inputLine);
        outputLine = kkp.processInput(inputLine);
        out.println(outputLine);

//        while ((inputLine = in.readLine()) != null) {
//            outputLine = kkp.processInput(inputLine);
//            out.println(outputLine);
//            if (outputLine.equals("Bye."))
//                break;
//        }
	}
}
