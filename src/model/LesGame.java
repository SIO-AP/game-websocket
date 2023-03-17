package model;

import java.util.ArrayList;

public class LesGame {
	private ArrayList<Game> lesGame = new ArrayList<Game>();

	public LesGame() {
	}

	public LesGame(ArrayList<Game> lesGame) {
		this.lesGame = lesGame;
	}

	public ArrayList<Game> getLesGame() {
		return lesGame;
	}

	public void setLesGame(ArrayList<Game> lesGame) {
		this.lesGame = lesGame;
	}

}
