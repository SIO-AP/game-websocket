package model;

public class Answer {
    String codeAnswer;
    String descriptionAnswer;
    boolean isCorrect;

    public Answer() {
    }
    
    public Answer(String codeAnswer, String descriptionAnswer, boolean isCorrect) {
    	this.codeAnswer = codeAnswer;
    	this.descriptionAnswer = descriptionAnswer;
    	this.isCorrect = isCorrect;
    }

    public String getCodeAnswer() {
        return codeAnswer;
    }

    public void setCodeAnswer(String codeAnswer) {
        this.codeAnswer = codeAnswer;
    }

    public String getDescriptionAnswer() {
        return descriptionAnswer;
    }

    public void setDescriptionAnswer(String descriptionAnswer) {
        this.descriptionAnswer = descriptionAnswer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

}
