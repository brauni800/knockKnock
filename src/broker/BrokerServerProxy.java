package broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServerProxy {

	private int portNumber;
	ServerSocket serverSocket;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	public BrokerServerProxy(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void connect() {
		try {
			this.serverSocket = new ServerSocket(this.portNumber);
			this.socket = this.serverSocket.accept();
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + this.portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
		}
	}
}
