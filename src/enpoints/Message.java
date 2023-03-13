package enpoints;

import java.util.ArrayList;

import model.Player;

public class Message {
	private int option;
	private int idGame;
	private Player player;
	private ArrayList<Player> lesPlayer;

	public Message() {
	}

	public Message(int option) {
		this.option = option; 
	}
	public Message(int option, int idGame) {
		this.option = option; 
		this.idGame = idGame;
	}

	public Message(int option, int idGame, Player monPlayer) {
		this.option = option; 
		this.idGame = idGame;
		this.player = monPlayer;		
	}
	
	public Message(int option, ArrayList<Player> lesPlayer) {
		this.option = option; 
		this.lesPlayer = lesPlayer;		
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Player> getLesPlayer() {
		return lesPlayer;
	}

	public void setLesPlayer(ArrayList<Player> lesPlayer) {
		this.lesPlayer = lesPlayer;
	}

	
}
