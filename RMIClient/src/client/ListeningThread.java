package client;

import java.io.IOException;
import java.util.Vector;


public class ListeningThread extends Thread{
	Client client;
	
	public ListeningThread(Client client) throws IOException {
		this.client = client;
	}
	
	public void run(){
		try {
			while (!client.isStopped()) {
				Vector<String> res = client.receiveMsg();
				if (res != null) {
					if (res.size() != 0) {
						for (String string : res) {
							System.out.println(string);
						}
					}
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problème ListeningThread");
		};
	}
}
