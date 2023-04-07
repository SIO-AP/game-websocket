package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;

import data.MySQLAccess;
import enpoints.Message;
import model.Game;
import model.LesGame;
import model.Player;
import model.Question;

public class Controller {

	private MySQLAccess laBase;
	private LesGame lesGames = new LesGame();

	private Jasypt theDecrypter;

	public Controller() {
		this.theDecrypter = new Jasypt();
		this.laBase = new MySQLAccess(this);

		// Server instantiation
		ServerGame theServer = new ServerGame(this);
	}

	public Game createParty(Game partyRequest, Connection connection) {
		ArrayList<Question> quizQuestions;
		try {
//			ArrayList<Integer> listeIdQuestion = listeIdQuestion(partyRequest.getNbQuestion());
//			quizQuestions = laBase.getQuestions(listeIdQuestion);

			quizQuestions = laBase.getQuestions(partyRequest.getNbQuestion());

			Game game = new Game(0, partyRequest.getName(), partyRequest.getIdLeader(), partyRequest.getPlayerList(),
					quizQuestions, partyRequest.getNbQuestion(), partyRequest.getTime());

			game = laBase.createMultiPlayerGame(game);
			// laBase.createQuestionParty(game);
			// laBase.createPlayerParty(game);

			lesGames.getLesGame().add(new Game(game.getIdGame(), game.getName(), game.getIdLeader(),
					game.getPlayerList(), game.getGroupQuestions(), game.getNbQuestion(), game.getTime(), connection));

			return game;
		} catch (Exception e) {
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

	public void deconnectionClient(Connection connection) {
		System.out.println("traitement");
		for (Game game : lesGames.getLesGame()) {
			game.getLesConnections().removeIf(connexion -> connexion.equals(connection));

			if (game.getLesConnections().isEmpty()) {
				laBase.finishedSoloPlayerGame(game);
				lesGames.getLesGame().remove(game);
			}
		}
	}

	private void startGame(int idGame, Connection connection) {
		for (Game game : lesGames.getLesGame()) {
			if (game.getIdGame() == idGame) {
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
		for (Game game : lesGames.getLesGame()) {
			if (game.getIdGame() == idGame) {
				for (Player p : game.getPlayerList()) {
					if (p.getMyId() == player.getMyId()) {
						p.setMyScore(player.getMyScore());
						p.setNbQuestion(player.getNbQuestion());
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
		for (Game game : lesGames.getLesGame()) {
			if (game.getIdGame() == idGame) {
				if (game.getStatusGame() == 1) {
					game.getPlayerList().add(player);
					connection.sendTCP(new Game(game.getIdGame(), game.getName(), game.getIdLeader(),
							game.getPlayerList(), game.getGroupQuestions(), game.getNbQuestion(), game.getTime()));

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

	public MySQLAccess getLaBase() {
		return laBase;
	}

	public void setLaBase(MySQLAccess laBase) {
		this.laBase = laBase;
	}

	public LesGame getLesGames() {
		return lesGames;
	}

	public void setLesGames(LesGame lesGames) {
		this.lesGames = lesGames;
	}

	public Jasypt getTheDecrypter() {
		return theDecrypter;
	}

	public void setTheDecrypter(Jasypt theDecrypter) {
		this.theDecrypter = theDecrypter;
	}

}
