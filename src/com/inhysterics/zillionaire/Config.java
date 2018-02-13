package com.inhysterics.zillionaire;

public class Config {

	protected String questionSet;
	protected int playerCount;
	protected ConfigRotationScheme rotationScheme;
	
	public Config() {
		
	}


	public String getQuestionSet() {
		return questionSet;
	}

	public void setQuestionSet(String questionSet) {
		this.questionSet = questionSet;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public ConfigRotationScheme getRotationScheme() {
		return rotationScheme;
	}

	public void setRotationScheme(ConfigRotationScheme rotationScheme) {
		this.rotationScheme = rotationScheme;
	}
	
}
