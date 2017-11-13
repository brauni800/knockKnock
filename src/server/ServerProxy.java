package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;

import model.Archivos;
import model.Service;

public class ServerProxy {

	public static final String VOTAR = "VOTAR";
	private static final String BUTTONS = "buttons";
	private static final String SERVICIO = "SERVICIO";
	private static final String RESULTADO = "RESULTADO";
	private static final String VOTO = "VOTO";
	
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

	public Service getDataFromServer() {
		String[] dataJSON = this.fromServer.split(","), peersDataJSON = null, key = null, value = null;
		Service service = new Service();
		int numOfServices = 0;
		
		for (int i = 0; i < dataJSON.length; i++) {
			peersDataJSON = dataJSON[i].split(":"); 
			key = peersDataJSON[0].split("\"");
			value = peersDataJSON[1].split("\"");
			switch(key[1]) {
			case SERVICIO:
				service.setService(value[1], numOfServices);
				numOfServices ++;
				break;
			case VOTO:
				service.setVote(value[1]);
				break;
			}
		}
		return service;
	}

	public boolean getResponseFromServer() throws IOException {
		return (this.fromServer = this.in.readLine()) != null;//funciona?
	}

	public void vote(String voto) {
		new Archivos(voto).insertarFecha();
	}

	@SuppressWarnings("unchecked")
	public void generateJSON() {
		String[] buttonNames = new Archivos(BUTTONS).getButtonsNames();
		this.json = new JSONObject();
		this.json.put(SERVICIO, RESULTADO);
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
		this.json.put(SERVICIO, VOTAR);
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
