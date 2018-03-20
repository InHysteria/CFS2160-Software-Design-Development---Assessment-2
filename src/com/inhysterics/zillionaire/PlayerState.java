package com.inhysterics.zillionaire;

public class PlayerState implements Comparable<PlayerState>
{
	
	protected String name;

	protected Boolean hasFiftyFifty;
	protected Boolean hasAskAudiance;
	protected Boolean hasFinished;
	
	protected int questionNo;
	
	public PlayerState() 
	{
		hasFiftyFifty = true;
		hasAskAudiance = true;
		hasFinished = false;
		
		questionNo = 0;
	}
	
	@Override
	public String toString()
	{
		return String.format("<html>%s<br/>&nbsp;&nbsp;&nbsp;&nbsp;Winnings: %s%s</html>",
				getName(),
				Zillionaire.CurrencySymbol,
				getScore());
	}

	
	public String getName() {
		return name;
	}

	public Boolean getHasFiftyFifty() {
		return hasFiftyFifty;
	}

	public Boolean getHasAskAudiance() {
		return hasAskAudiance;
	}

	public Boolean getHasFinished() {
		return hasFinished;
	}

	public int getQuestionNo() {
		return questionNo;
	}

	public int getScore()
	{
		return GameService.getScoreForQuestion(questionNo);
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public void setHasFiftyFifty(Boolean hasFiftyFifty) {
		this.hasFiftyFifty = hasFiftyFifty;
	}

	public void setHasAskAudiance(Boolean hasAskAudiance) {
		this.hasAskAudiance = hasAskAudiance;
	}

	public void setHasFinished(Boolean hasFinished) {
		this.hasFinished = hasFinished;
	}

	public void setQuestionNo(int questionNo) {
		this.questionNo = questionNo;
	}


	@Override
	public int compareTo(PlayerState arg0) {
		return Integer.compare(this.getQuestionNo(), arg0.getQuestionNo());
	}
}
