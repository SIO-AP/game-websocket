package model;

import controller.Controller;

public class Player {
	
	private Controller monCtroller;
	private String myName;
	private int myScore;
	
	public Player () {
		
	}
	public Player(String aName, int aScore) {
		this.myName = aName;
		this.myScore = aScore;				
	}
	
	public Player(Controller aController, String aName, int aScore) {
		this.monCtroller = aController;
		this.myName = aName;
		this.myScore = aScore;				
	}

	public Controller getMonCtroller() {
		return monCtroller;
	}

	public void setMonCtroller(Controller monCtroller) {
		this.monCtroller = monCtroller;
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
	
	
}
