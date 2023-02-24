package model;

public class Player {
	
	private String myName;
	private int myScore;
	private int aGroupId;

	public Player(String aName, int aScore) {
		this.myName = aName;
		this.myScore = aScore;				
	}
	
	public Player(String aName, int aScore, int groupId) {
		this.myName = aName;
		this.myScore = aScore;				
		this.aGroupId = groupId;
	}
	
	public int getaGroupId() {
		return aGroupId;
	}

	public void setaGroupId(int aGroupId) {
		this.aGroupId = aGroupId;
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
