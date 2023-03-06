package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import data.MySQLAccess;
import data.SomeRequest;
import enpoints.Message;
import model.*;

public class ServerGame {
	Server server = new Server(100000, 100000);
	private Controller monController;

	public ServerGame(Controller unController) {
		
		this.monController = unController;
		Kryo kryo = server.getKryo();
		kryo.register(SomeRequest.class);		
		kryo.register(Message.class);
	    kryo.register(ArrayList.class);
	    kryo.register(QuizGame.class);
	    kryo.register(Answer.class);
	    kryo.register(Player.class);
	    kryo.register(Question.class);
	    kryo.register(Party.class);
	    kryo.register(Controller.class);
	    kryo.register(LesParty.class);
	    
		server.start();

		try {
			server.bind(54551, 54771);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {

				if (object instanceof QuizGame) {
					QuizGame game = (QuizGame) object;
					System.out.println(game.getMyPlayer().getMyName());
					for (Question question : game.getQuestions()) {
						System.out.println(question.getDescriptionQuestion());

					}
				}

				if (object instanceof LesParty) {
					LesParty lesParty = new LesParty(monController.getListParty());
					connection.sendTCP(lesParty);
				}

				if (object instanceof Party) {
					Party partyRequest = (Party) object;
					System.out.println("Nouvelle partie");
					System.out.println("Nom : " + partyRequest.getName());
					System.out.println("Nombre de question : " + partyRequest.getNbQuestion());

					
					Party party = monController.createParty(partyRequest);					
					connection.sendTCP(party);
/*
					Message response = new Message();
					response.text = "Party created " + partyRequest.getName() + " !";
					connection.sendTCP(response);

*/
				}

				if (object instanceof Player) {
					Player request2 = (Player) object;
					System.out.println("ok");
				}
			}
		});
	}
}