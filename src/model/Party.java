package model;

import java.util.ArrayList;

public class Party {

	private int idGroup;
	private String name;
	private int leaderId;
	private int gameId;
	public Party() {}
	
	public Party(int aGroup, String name, int aleaderId, ArrayList<Player> playerList, ArrayList<Question> theQuestions) {
		this.idGroup = aGroup;
		this.name = name;
		this.leaderId = aleaderId;
	}

	

	public int getLeaderId() {
		return leaderId;
	}



	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}



	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


}
