package model;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;

public class Game {

	private int idGame;
	private String name;
	private int idLeader;
	private int nbQuestion;
	private ArrayList<Player> playerList;
	private ArrayList<Question> groupQuestions;
	private String time;
	private ArrayList<Connection> lesConnections;
	private int statusGame;
	
	public Game() {};
	
	public Game(String name, int idLeader, int nbQuestion, Player leader, String time) {
		this.name = name;
		this.idLeader = idLeader;
		this.nbQuestion = nbQuestion;
		this.playerList = new ArrayList<Player>();
		this.playerList.add(leader);
		this.time = time;
	}
	
	public Game(String name, int idGame, int nbQuestion, String time) {
		this.name = name;
		this.nbQuestion = nbQuestion;
		this.time = time;
		this.idGame = idGame;
	}
	
	public Game(int idGame, String name, int idLeader, ArrayList<Player> playerList, ArrayList<Question> theQuestions, int nbQuestion, String time) {
		this.idGame = idGame;
		this.name = name;
		this.idLeader = idLeader;
		this.playerList = playerList;
		this.groupQuestions = theQuestions;
		this.nbQuestion = nbQuestion;
		this.time = time;
	}
	
	public Game(int idGame, String name, int idLeader, ArrayList<Player> playerList, ArrayList<Question> theQuestions, int nbQuestion, String time, Connection connection) {
		this.idGame = idGame;
		this.name = name;
		this.idLeader = idLeader;
		this.playerList = playerList;
		this.groupQuestions = theQuestions;
		this.nbQuestion = nbQuestion;
		this.lesConnections = new ArrayList<Connection>();
		this.lesConnections.add(connection);	
		this.time = time;
		this.statusGame = 1;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdLeader() {
		return idLeader;
	}

	public void setIdLeader(int idLeader) {
		this.idLeader = idLeader;
	}

	public int getNbQuestion() {
		return nbQuestion;
	}

	public void setNbQuestion(int nbQuestion) {
		this.nbQuestion = nbQuestion;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<Question> getGroupQuestions() {
		return groupQuestions;
	}

	public void setGroupQuestions(ArrayList<Question> groupQuestions) {
		this.groupQuestions = groupQuestions;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ArrayList<Connection> getLesConnections() {
		return lesConnections;
	}

	public void setLesConnections(ArrayList<Connection> lesConnections) {
		this.lesConnections = lesConnections;
	}

	public int getStatusGame() {
		return statusGame;
	}

	public void setStatusGame(int statusGame) {
		this.statusGame = statusGame;
	}

	
	

	
}
