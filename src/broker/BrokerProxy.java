package broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

public class BrokerProxy {

	private String fromClient,fromServer,hostName;
	private int portNumberAsAClient,portNumberAsAServer;
	private ServerSocket clientsSocket;
	private Socket socketAsAClient,socketAsAServer;
	private PrintWriter outToClient,outToServer;
	private BufferedReader inFromClient,inFromServer;
	private JSONObject json;
	
	public BrokerProxy(String hostName,int portNumberAsAServer, int portNumberASaClient) {
		this.portNumberAsAClient = portNumberASaClient;
		this.portNumberAsAServer = portNumberAsAServer;
		this.hostName = hostName;
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
	
	public String[][] getDataFromClient() {
		String[] dataJSON = this.fromClient.split(","), peersDataJSON = null, correctPeers = null;
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
	
	public void connectServer() {
		try {
			this.socketAsAClient = new Socket(this.hostName,this.portNumberAsAClient);
			this.outToServer = new PrintWriter(this.socketAsAClient.getOutputStream(), true);
			this.inFromServer = new BufferedReader(new InputStreamReader(this.socketAsAClient.getInputStream()));
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + this.portNumberAsAClient + "or" + this.portNumberAsAServer + " or listening for a connection");
            System.out.println(e.getMessage());
		}
		
	}
	
	public void connectClient() {
		try {
			this.clientsSocket = new ServerSocket(this.portNumberAsAServer);
			this.socketAsAServer = this.clientsSocket.accept();
			this.outToClient = new PrintWriter(this.socketAsAServer.getOutputStream(), true);
			this.inFromClient = new BufferedReader(new InputStreamReader(this.socketAsAServer.getInputStream()));
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + this.portNumberAsAClient + "or" + this.portNumberAsAServer + " or listening for a connection");
            System.out.println(e.getMessage());
		}
	}
	
	public boolean getResponseFromServer() throws IOException {
		return (this.fromServer = this.inFromServer.readLine()) != null;
	}
	public boolean getResponseFromClient() throws IOException {
		return (this.fromClient = this.inFromClient.readLine()) != null;
	}
	
	public void setTheOutputToServer(JSONObject output) {
		this.outToServer.println(output);
	}
	public void setTheOutputToClient(JSONObject output) {
		this.outToClient.println(output);
	}
	public String[][] getInputValueFromServer() throws IOException {
		this.fromServer = this.inFromServer.readLine();
		return getDataFromServer();
	}
	public String[][] getInputValueFromClient() throws IOException {
		this.fromClient = this.inFromClient.readLine();
		return getDataFromClient();
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
