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

			Party party = new Party(0, partyRequest.getName(), partyRequest.getIdLeader(), null, quizQuestions,
					partyRequest.getNbQuestion());

			party = laBase.createParty(party);
			laBase.createQuestionParty(party);
			laBase.createPlayerParty(party);

			// lesGames.getLesParty().add(new Party(party.getIdParty(), connection));
			lesGames.getLesParty().add(new Party(party.getIdParty(), party.getName(), party.getIdLeader(), null,
					party.getGroupQuestions(), party.getNbQuestion(), "12:00:00", connection));

			return party;
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void selectOption(Message message, Connection connection) {
		if (message.getOption() == 1) { // JoinGame
			joinGame(message.getIdGame(), connection);
		}
	}

	public void joinGame(int idGame, Connection connection) {
		for (Party game : lesGames.getLesParty()) {
			if (game.getIdParty() == idGame) {
				game.getLesConnections().add(connection);
				System.out.println("add connection");
				connection.sendTCP(new Party(game.getIdParty(), game.getName(), game.getIdLeader(), null, game.getGroupQuestions(), game.getNbQuestion()));
				// connection.sendTCP();
				break;
			}
		}
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
