package model;

import java.util.ArrayList;

public class LesParty {
	private ArrayList<Party> lesParty = new ArrayList<Party>();
	
	public LesParty() {}
	
	public LesParty(ArrayList<Party> lesParty) {
		this.lesParty = lesParty;
	}

	public ArrayList<Party> getLesParty() {
		return lesParty;
	}

	public void setLesParty(ArrayList<Party> lesParty) {
		this.lesParty = lesParty;
	}

	
}
