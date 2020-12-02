package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.Vector;

import server.ChatServer;

public class Client {
	ChatServer server;
	int lastRead;
	private boolean stopped;

	public Client() throws MalformedURLException, RemoteException, NotBoundException {
//		server = (ChatServer)Naming.lookup("//localhost/RmiServer");
		lastRead = 0;
		stopped = false;
	}
	
	public static void main(String args[]) throws Exception {
        Client chatClient = new Client();
        
        CBClientIntf CB = new CBClient();
        chatClient.server.addCB(CB);
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez votre nom: ");
        String name = sc.nextLine();
        
        
        String arrStr[] = chatClient.server.messageHello(name).split(","); // retourne Bienvenue,name
        int id = Integer.parseInt(arrStr[1]);
       
        if (name == null) {
        	name = "Client " + id;
		}
        System.out.println(arrStr[0]); //affiche Bienvenue
        
        // Decommentez la ligne suivante pour tester le programme sans CallBack (Polling)
//        new ListeningThread(chatClient).start();
        
     
        
        
		String line = sc.nextLine();
		while (!line.equals("quit")) {
			synchronized (CB) {
				chatClient.server.sendMsg(name + " : " + line, 0);
				// Decommentez la ligne suivante pour tester le programme sans Polling (Callback)
				CB.notifyMe(line);
			
				line = sc.nextLine();
			}
			
			
		}
		chatClient.server.messageBye(name);
		chatClient.server.removeCB(CB);
		chatClient.stop();
		sc.close();
    }
	
	public Vector<String> receiveMsg() {
		try {
			Vector<String> res = this.server.receiveMsg(lastRead);
			lastRead = server.getMessages().size();
			return res;
		} catch (Exception e) {
			System.out.println("Problème recevoirMsg");
			return null;
		}
	}
	
	public int getLastRead() {
		return lastRead;
	}

	public void setLastRead(int lastRead) {
		this.lastRead = lastRead;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	
	public void stop() {
		stopped = true;
	}
	
	
}
