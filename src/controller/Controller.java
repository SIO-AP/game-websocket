package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;

import data.MySQLAccess;
import enpoints.Message;
import model.LesParty;
import model.Party;
import model.Player;
import model.Question;

public class Controller {

	private MySQLAccess laBase;
	private LesParty lesGames = new LesParty();

	public Controller() {
		this.laBase = new MySQLAccess(this);

		// Server instantiation
		ServerGame theServer = new ServerGame(this);
	}

	public Party createParty(Party partyRequest, Connection connection) {
		ArrayList<Question> quizQuestions;
		try {
			ArrayList<Integer> listeIdQuestion = listeIdQuestion(partyRequest.getNbQuestion());

			quizQuestions = laBase.getQuestions(listeIdQuestion);

			Party game = new Party(0, partyRequest.getName(), partyRequest.getIdLeader(), partyRequest.getPlayerList(),
					quizQuestions, partyRequest.getNbQuestion());

			game = laBase.createParty(game);
		//	laBase.createQuestionParty(game);
		//	laBase.createPlayerParty(game);

			lesGames.getLesParty().add(new Party(game.getIdParty(), game.getName(), game.getIdLeader(),
					game.getPlayerList(), game.getGroupQuestions(), game.getNbQuestion(), "12:00:00", connection));

			return game;
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void selectOption(Message message, Connection connection) {
		if (message.getOption() == 1) { // JoinGame
			joinGame(message.getIdGame(), message.getPlayer(), connection);
		} else if (message.getOption() == 2) { // Start game
			startGame(message.getIdGame(), connection);
		} else if (message.getOption() == 5) { // Change le score du player
			setScorePlayer(message.getIdGame(), message.getPlayer(), connection);
		}
	}

	private void startGame(int idGame, Connection connection) {
		for (Party game : lesGames.getLesParty()) {
			if (game.getIdParty() == idGame) {
				for (Connection conn : game.getLesConnections()) {
					try {
						conn.sendTCP(new Message(2));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				game.setStatusGame(2);
				break;
			}
		}
	}

	public void setScorePlayer(int idGame, Player player, Connection connection) {
		for (Party game : lesGames.getLesParty()) {
			if (game.getIdParty() == idGame) {
				for (Player p : game.getPlayerList()) {
					if (p.getMyId() == player.getMyId()) {
						p.setMyScore(player.getMyScore());

						// Envoie de la nouvelle liste des joueurs aux connexions de la partie
						for (Connection conn : game.getLesConnections()) {
							try {
								conn.sendTCP(new Message(4, game.getPlayerList()));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						return;
					}
				}

			}
		}

	}

	public void joinGame(int idGame, Player player, Connection connection) {
		for (Party game : lesGames.getLesParty()) {
			if (game.getIdParty() == idGame) {
				if (game.getStatusGame() == 1) {
					game.getPlayerList().add(player);
					connection.sendTCP(new Party(game.getIdParty(), game.getName(), game.getIdLeader(),
							game.getPlayerList(), game.getGroupQuestions(), game.getNbQuestion()));

					// Envoie de la nouvelle liste des joueurs aux connections de la partie
					for (Connection conn : game.getLesConnections()) {
						try {
							conn.sendTCP(new Message(4, game.getPlayerList()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					System.out.println("add connection");
					game.getLesConnections().add(connection);
				} else {
					connection.sendTCP(new Message(3));
				}
				return;
			}
		}
		connection.sendTCP(new Message(3));
	}

	private ArrayList<Integer> listeIdQuestion(int maxQuestions)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

		ArrayList<Integer> listeIdQuestion = new ArrayList<Integer>();
		int nombreTotalQuestion = laBase.nombreTotalQuestion();

		for (int i = 0; i < maxQuestions; i++) {
			int test = generateQuestionId(nombreTotalQuestion);

			if (listeIdQuestion.contains(test)) {
				while (listeIdQuestion.contains(test)) {
					test = generateQuestionId(nombreTotalQuestion);
				}
				listeIdQuestion.add(test);
			} else {
				listeIdQuestion.add(test);
			}
		}
		return listeIdQuestion;
	}

	public int generateQuestionId(int nombreTotalQuestion) {
		int randomNumber = new Random().nextInt(nombreTotalQuestion + 0);
		return randomNumber;
	}

	public ArrayList<Party> getListParty() {
		ArrayList<Party> lesParty = laBase.getListParty();
		return lesParty;
	}

	public MySQLAccess getLaBase() {
		return laBase;
	}

	public void setLaBase(MySQLAccess laBase) {
		this.laBase = laBase;
	}

	public LesParty getLesGames() {
		return lesGames;
	}

	public void setLesGames(LesParty lesGames) {
		this.lesGames = lesGames;
	}

}
