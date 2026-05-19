package com.mathify.model;

public abstract class Question {
    private String QuestionId;
    private String questionText;
    private int difficulty;
    private String imageUrl;
    private String explanation;
    private String hint;

    public abstract boolean checkAnswer(String answer);
}
