package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import data.MySQLAccess;
import model.Party;
import model.Question;

public class Controller {

	private MySQLAccess laBase;

	public Controller() {
		this.laBase = new MySQLAccess(this);
		try {
			this.laBase.connection();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}		
		
		// Server instantiation
		ServerGame theServer = new ServerGame(this);
	}

	public Party createParty(Party partyRequest) {
		ArrayList<Question> quizQuestions;
		try {
			quizQuestions = laBase.getQuestions(partyRequest.getNbQuestion());
			Party party = new Party(0, partyRequest.getName(), partyRequest.getIdLeader(), null, quizQuestions,
					partyRequest.getNbQuestion());

			party = laBase.createParty(party);
			laBase.createQuestionParty(party);
			laBase.createPlayerParty(party);

			return party;
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public ArrayList<Party> getListParty(){
		ArrayList<Party> lesParty = laBase.getListParty();
		return lesParty;
	}

	public MySQLAccess getLaBase() {
		return laBase;
	}

	public void setLaBase(MySQLAccess laBase) {
		this.laBase = laBase;
	}

}
