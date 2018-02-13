package com.inhysterics.zillionaire;

public class PlayerState {
	
	protected String name;
	
	protected Question[] questionSet;
	protected int questionNo;
	
	public PlayerState() {

	}
	
	public void setQuestionSet(Question[] set)
	{
		questionSet = set;
	}
	
	public Question nextQuestion() {
		return questionSet[questionNo++];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public void DEBUG__setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}
}
