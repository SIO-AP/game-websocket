package model;

import java.util.ArrayList;

public class Question {
    String descriptionQuestion;
    ArrayList<Answer> answers;

    public Question() {

    }
    
    public Question(String descriptionQuestion, ArrayList<Answer> answers) {
    	this.descriptionQuestion = descriptionQuestion;
    	this.answers = answers;
    }

    public String getDescriptionQuestion() {
        return descriptionQuestion;
    }

    public void setDescriptionQuestion(String descriptionQuestion) {
        this.descriptionQuestion = descriptionQuestion;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
