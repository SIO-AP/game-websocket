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
		kryo.register(Game.class);
		kryo.register(LesGame.class);

		server.start();

		try {
			server.bind(54556, 54776);
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {

				if (object instanceof LesGame) {
					LesGame lesParty = new LesGame();

					for (Game game : monController.getLesGames().getLesGame()) {
						if (game.getStatusGame() == 1) {
							lesParty.getLesGame().add(
									new Game(game.getName(), game.getIdGame(), game.getNbQuestion(), game.getTime()));
						}
					}
					connection.sendTCP(lesParty);
				}

				if (object instanceof Game) {
					Game partyRequest = (Game) object;
					System.out.println("Nouvelle partie");
					System.out.println("Nom : " + partyRequest.getName());
					System.out.println("Nombre de question : " + partyRequest.getNbQuestion());

					Game party = monController.createParty(partyRequest, connection);
					connection.sendTCP(party);
				}

				if (object instanceof Message) {
					Message request = (Message) object;
					monController.selectOption(request, connection);
				}
			}
		});
	}
}