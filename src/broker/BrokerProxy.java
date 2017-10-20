package broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerProxy {

	private String fromServer;
	private int portNumber;
	private ServerSocket clientsSocket;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	public BrokerProxy(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public void connect() {
		try {
			this.clientsSocket = new ServerSocket(this.portNumber);
			this.socket = this.clientsSocket.accept();
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + this.portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
		}
	}
	
	public boolean getResponseFromServer() throws IOException {
		return (this.fromServer = this.in.readLine()) != null;
	}
	
	public void setTheOutput(String output) {
		this.out.println(output);
	}
}
