package broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

public class BrokerProxy {

	private String fromServer;
	private int portNumber;
	private ServerSocket clientsSocket;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private JSONObject json;
	
	public BrokerProxy(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public String[][] getDataFromServer() {
		String[] dataJSON = this.fromServer.split(","), peersDataJSON = null, correctPeers = null;
		String[][] result = new String[dataJSON.length][2];
		
		for (int i = 0; i < dataJSON.length; i++) {
			peersDataJSON = dataJSON[i].split(":"); 
			for (int j = 0; j < peersDataJSON.length; j++) {
				correctPeers = peersDataJSON[j].split("\"");
				result[i][j] = correctPeers[1];
			}
		}
		return result;
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
	
	public void setTheOutput(JSONObject output) {
		this.out.println(output);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject results(String[][] results) {
		this.json = new JSONObject();
		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < results[i].length; j++) {
				this.json.put(results[i][0], results[i][1]);
			}
		}
		return this.json;
	}
}
