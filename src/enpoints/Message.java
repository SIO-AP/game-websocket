package enpoints;

public class Message {
	private int option;
	private int idGame;

	public Message() {
	}

	public Message(int option) {
		this.option = option; 
	}
	public Message(int option, int idGame) {
		this.option = option; 
		this.idGame = idGame;
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

}
