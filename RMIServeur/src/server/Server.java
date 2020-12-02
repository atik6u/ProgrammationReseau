package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import client.CBClientIntf;

public class Server extends UnicastRemoteObject implements ChatServer {
	private static final long serialVersionUID = 1L;
	private Vector<String> messages;
	private Vector<CBClientIntf> CBs;
	private int clients;

	public Server() throws RemoteException{
		super(0);
		messages = new Vector<String>();
		CBs = new Vector<CBClientIntf>();
		clients = 0;
	}
	
	public synchronized void addCB(CBClientIntf CB) {
		CBs.add(CB);
	}
	
	public synchronized void removeCB(CBClientIntf CB) {
		CBs.remove(CB);
	}
	
	public Vector<String> getMessages() throws RemoteException{
		return messages;
	}

	public void setMessages(Vector<String> messages) throws RemoteException{
		this.messages = messages;
	}

	public int getClients() throws RemoteException {
		return clients;
	}

	public void setClients(int clients) throws RemoteException{
		this.clients = clients;
	}

	
	@Override
	public String messageHello(String name) throws RemoteException{
		clients++;
		messages.add("Serveur: " + name + " a rejoint le chat!");
		return "Bienvenue " + name  + "!," + clients;
	}
	

	@Override
	public String messageBye(String name) throws RemoteException {
		messages.add("Server: " + name + " a quitté le chat!");
		clients--;
		return "Bye " + name + "!";
	}

	@Override
	public synchronized void sendMsg(String msg, int id) throws RemoteException {
		messages.add(msg);
	}
	
	@Override
	public synchronized Vector<String> receiveMsg(int lastMessage) throws RemoteException {
		Vector<String> res = new Vector<String>(messages.subList(lastMessage, messages.size()));
		
		return res;
	}
	
	@Override
	public synchronized Vector<String> receiveMsg(int lastMessage, CBClientIntf CB) throws RemoteException { // cettee methode est appelé dans l'approche callback
		Vector<String> res = new Vector<String>(messages.subList(lastMessage, messages.size()));
		int index = CBs.indexOf(CB);
		synchronized (CB) {
			
		}
//		CBs.get(index)
		return res;
	}
	
	 public static void main(String args[]) throws Exception {
	        try { 
	            LocateRegistry.createRegistry(1099);
	        } catch (RemoteException e) {
	        }
	        Server chatServeur = new Server();
	        Naming.rebind("//localhost/RmiServer", chatServeur);
	        System.out.println("Serveur prét!");
        }

}
