package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

public class ClientProxy {

	public static final String REGISTRAR = "REGISTRAR";
	public static final String VOTAR = "VOTAR";
	private static final String SERVICIO = "SERVICIO";
	private static final String IP = "IP";
	
	private String hostName, fromServer;
	private int portNumber;
	private Socket kkSocket;
	private PrintWriter out;
	private BufferedReader in;
	private JSONObject json;
	
	public ClientProxy(String hostName, int portNumber) {
		this.hostName = hostName;
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

	public boolean getResponseFromServer() throws IOException {
		return (this.fromServer = this.in.readLine()) != null;
	}

	@SuppressWarnings("unchecked")
	public void register(String ip) {
		this.json = new JSONObject();
		this.json.put(ClientProxy.REGISTRAR, ip);
        this.out.println(this.json);
	}

	@SuppressWarnings("unchecked")
	public void vote(String voto, String ip) {
		this.json = new JSONObject();
		this.json.put(ClientProxy.SERVICIO, ClientProxy.VOTAR);
		this.json.put(ClientProxy.IP, ip);
		this.json.put("VOTO", voto);
        this.out.println(this.json);
	}

	public void connect() {
		try {
			this.kkSocket = new Socket(this.hostName, this.portNumber);
			this.out = new PrintWriter(this.kkSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.kkSocket.getInputStream()));
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host " + this.hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + this.hostName);
            System.exit(1);
        }
	}
}
