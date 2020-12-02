package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CBClientIntf extends Remote {

	public void notifyMe(String Message) throws RemoteException;
}
