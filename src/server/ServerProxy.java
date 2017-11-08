package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

import model.Archivos;

public class ServerProxy {

	public static final String VOTAR = "VOTAR";
	private static final String BUTTONS = "buttons";
	private static final String SERVICIO = "SERVICIO";
	
	private String fromServer;
	private int portNumber;
	private ServerSocket serverSocket;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private JSONObject json;
	
	public ServerProxy(int portNumber) {
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
		return (this.fromServer = this.in.readLine()) != null;//funciona?
	}

	public void vote(String voto) {
		new Archivos(voto).insertarFecha();
	}

	@SuppressWarnings("unchecked")
	public void generateJSON() {
		String[] buttonNames = new Archivos(ServerProxy.BUTTONS).getButtonsNames();
		this.json = new JSONObject();
		for (int i = 0; i < buttonNames.length; i++) {
			int numVotos = (int) new Archivos(buttonNames[i]).contarLineas();
			this.json.put(buttonNames[i], numVotos);
		}
	}

	public void setTheOutput() {
		this.out.println(this.json);
	}

	@SuppressWarnings("unchecked")
	public void sendServices() {
		this.json = new JSONObject();
		this.json.put(ServerProxy.SERVICIO, ServerProxy.VOTAR);
        this.out.println(this.json);
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
