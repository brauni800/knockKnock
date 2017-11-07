package broker;

import java.io.IOException;

public class BrokerMain {

	public static void main(String[] args) throws IOException {
		BrokerService.main(new String[] {"localhost","4444", "4445"});
	}
}
