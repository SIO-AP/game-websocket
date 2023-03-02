package model;

import java.util.ArrayList;

import controller.Controller;

public class QuizGame {
//	private String playerName;
//    private int playerScore;
	
	private Player myPlayer;
    
    private ArrayList<Question> questions;
    private ArrayList<Integer> listeIdQuestions;
    
    private Controller monController;

    public QuizGame() {
    }
    
    public QuizGame(Player player, ArrayList<Question> questions) {
    	this.myPlayer = player;
    	this.questions = questions;
    }
    
    public QuizGame(Controller aController, Player player, ArrayList<Question> questions) {
    	this.monController = aController;
    	this.myPlayer = player;
    	this.questions = questions;
    }

	public Player getMyPlayer() {
		return myPlayer;
	}
    
	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    
    

//    public Boolean isValidAnswer(String selectedAnswer) {
//        selectedAnswer = selectedAnswer.toLowerCase();
//        if (!selectedAnswer.equals("1") && !selectedAnswer.equals("2") && !selectedAnswer.equals("3") && !selectedAnswer.equals("4")) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    public Boolean isCorrectThisAnswer(Question question, int idAnswer) {
    	if (question.getAnswers().get(idAnswer).getIsCorrect()) {
    		this.myPlayer.setMyScore(this.myPlayer.getMyScore() + 10);
			return true;
    	} else {
    		return false;
    	}
    }
}
