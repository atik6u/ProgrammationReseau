package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import client.CBClientIntf;

public interface ChatServer extends Remote{

	public String messageHello(String name) throws RemoteException;
	
	public String messageBye(String name) throws RemoteException;
	
	public void sendMsg(String msg, int id) throws RemoteException;
	
	public Vector<String> receiveMsg(int lastMessage) throws RemoteException;
	
	public Vector<String> receiveMsg(int lastMessage, CBClientIntf CB) throws RemoteException;
	
	public Vector<String> getMessages() throws RemoteException;
	
	public void addCB(CBClientIntf CB) throws RemoteException;
	
	public void removeCB(CBClientIntf CB) throws RemoteException;


	
}
