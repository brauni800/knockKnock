package knockKnock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

public class BrokerServer {

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: java KnockKnockServer <port number> or EchoClient <host name> <port number>");
            System.exit(1);
        }

		int portNumber = 0;
		String hostName = null;
		ServerSocket serverSocket = null;
		Socket socket = null;
		
        try {
        	if (args.length == 1) {
    			portNumber = Integer.parseInt(args[0]);
    			serverSocket = new ServerSocket(portNumber);
                socket = serverSocket.accept();
    		} else if (args.length == 2) {
    			hostName = args[0];
    			portNumber = Integer.parseInt(args[1]);
    			socket = new Socket(hostName, portNumber);
    		}
    		
    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            switch (args.length) {
        	case 1:
        		serverExecute(out, in);
        		break;
        	case 2:
        		clientExecute(out, in);
        		break;
        	}
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
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
