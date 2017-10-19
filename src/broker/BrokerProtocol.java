package broker;

public class BrokerProtocol {

	private BrokerClientProxy clientProxy;
	private BrokerServerProxy serverProxy;
	
	public BrokerProtocol(BrokerClientProxy clientProxy) {
		this.clientProxy = clientProxy;
	}
	
	public BrokerProtocol(BrokerServerProxy serverProxy) {
		this.serverProxy = serverProxy;
	}

	public String processInput(String input) {
		String theOutput = null;
		
		return theOutput;
	}
	private void protocol() {
		
	}
}
