package model;

public class Service {

	private String ip, vote;
	private String[] services;
	
	public Service() {
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVote() {
		return vote;
	}

	public void setVote(String vote) {
		this.vote = vote;
	}

	public String getService(int position) {
		return services[position];
	}

	public void setService(String service, int position) {
		this.services[position] = service;
	}
	
	public int numberOfServices() {
		return this.services.length;
	}
}
