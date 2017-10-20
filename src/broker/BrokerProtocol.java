package broker;

public class BrokerProtocol {

	private BrokerProxy clientProxy;
	private BrokerServerProxy serverProxy;
	
	public BrokerProtocol(BrokerProxy clientProxy) {
		this.clientProxy = clientProxy;
	}
	
	public BrokerProtocol(BrokerServerProxy serverProxy) {
		this.serverProxy = serverProxy;
	}

	public String processInput(String input) {
		String theOutput = null;
		if (!input.equals(null)) {
			switch(input) {
			case "":
				break;
			}
		} else {
			
		}
		return theOutput;
	}
	private void protocol() {
		
	}
}
