package controller;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import data.MySQLAccess;
import enpoints.Message;
import model.Party;
 
public class ServerGame {
	Server server = new Server();
	MySQLAccess bdd = new MySQLAccess();

	public ServerGame() {
		Kryo kryo = server.getKryo();
	    kryo.register(Party.class);
	    kryo.register(Message.class);

	    server.start();

	    try {
	        server.bind(54556, 54776);
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	    server.addListener(new Listener() {
	           public void received (Connection connection, Object object) {
	              if (object instanceof Message) {
	            	  Message request = (Message)object;
	            	  System.out.println(request.text);
	              }
	              
	              if (object instanceof Party) {
	            	  Party partyRequest = (Party)object;
	            	  System.out.println("Nouvelle partie: "+partyRequest.getName());
	            	  
	            	  Message response = new Message();   
	            	  response.text = "Party created "+partyRequest.getName()+" !";
		              connection.sendTCP(response);
		              
	            	  bdd.createParty(partyRequest);

	              }
	           }
	    });
	}
}