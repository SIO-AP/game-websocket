package model;

import controller.Controller;

public class Player {

	private int myId;
	private String myName;
	private int myScore;

	public Player() {

	}

	public Player(String aName, int aScore) {
		this.myName = aName;
		this.myScore = aScore;
	}

	public Player(int aId, String aName, int aScore) {
		this.myId = aId;
		this.myName = aName;
		this.myScore = aScore;
	}


	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyScore() {
		return myScore;
	}

	public void setMyScore(int myScore) {
		this.myScore = myScore;
	}

	public int getMyId() {
		return myId;
	}

	public void setMyId(int myId) {
		this.myId = myId;
	}

}
