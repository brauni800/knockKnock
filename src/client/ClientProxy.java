package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import model.Service;

public class ClientProxy {

	public static final String REGISTRAR = "REGISTRAR";
	public static final String VOTAR = "VOTAR";
	private static final String SERVICIO = "SERVICIO";
	private static final String IP = "IP";
	private static final String VOTO = "VOTO";
	
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
			default:
				service.addResult(key[1], value[1]);
				break;
			}
		}
		return service;
	}

	public boolean getResponseFromServer() throws IOException {
		return (this.fromServer = this.in.readLine()) != null;
	}

	@SuppressWarnings("unchecked")
	public void register(String ip) {
		this.json = new JSONObject();
		this.json.put(ClientProxy.SERVICIO, ClientProxy.REGISTRAR);
		this.json.put(ClientProxy.IP, ip);
        this.out.println(this.json);
	}

	@SuppressWarnings("unchecked")
	public void vote(String voto, String ip) {
		this.json = new JSONObject();
		this.json.put(ClientProxy.SERVICIO, ClientProxy.VOTAR);
		this.json.put(ClientProxy.IP, ip);
		this.json.put(ClientProxy.VOTO, voto);
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
