package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class CBClient extends UnicastRemoteObject implements CBClientIntf {
	private static final long serialVersionUID = 1L;
	Client client;
	
	protected CBClient() throws RemoteException {
		super();
	}

	public void notifyMe(String Message) throws RemoteException {
		Vector<String> res = client.receiveMsg();
		if (res != null) {
			if (res.size() != 0) {
				for (String string : res) {
					System.out.println(string);
				}
			}
		}
	}
	

}
