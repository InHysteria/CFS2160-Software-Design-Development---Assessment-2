package com.inhysterics.zillionaire;

/**
 * Holds information relating to the state of a player.
 */
public class PlayerState implements Comparable<PlayerState>
{
	/**
	 * The name of the player this state represents.
	 */
	protected String name;

	/**
	 * Has this player used the FiftyFifty lifeline.
	 */
	protected Boolean hasFiftyFifty;
	
	/**
	 * Has this player used the AskTheAudiance lifeline.
	 */
	protected Boolean hasAskAudiance;
	
	/**
	 * Has this player finished.
	 */
	protected Boolean hasFinished;
	
	/**
	 * How many questions has this player answered.
	 */
	protected int questionNo;
	
	/**
	 * The constructor for the PlayerState.
	 */
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

	/**
	 * Getter for name.
	 * 
	 * @return The name field.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Getter for hasFiftyFifty.
	 * 
	 * @return The hasFiftyFifty field.
	 */
	public Boolean getHasFiftyFifty() 
	{
		return hasFiftyFifty;
	}

	/**
	 * Getter for hasAskAudiance.
	 * 
	 * @return The hasAskAudiance field.
	 */
	public Boolean getHasAskAudiance() 
	{
		return hasAskAudiance;
	}

	/**
	 * Getter for hasFinished.
	 * 
	 * @return The hasFinished field.
	 */
	public Boolean getHasFinished() 
	{
		return hasFinished;
	}

	/**
	 * Getter for questionNo.
	 * 
	 * @return The questionNo field.
	 */
	public int getQuestionNo() 
	{
		return questionNo;
	}

	/**
	 * Utility for returning the score of this player based on their questionNo.
	 * 
	 * @return The score of this player based on their questionNo.
	 * @see GameService.getScoreForQuestion
	 */
	public int getScore()
	{
		return GameService.getScoreForQuestion(questionNo);
	}

	/**
	 * Setter for name.
	 * 
	 * @param The value to set name too.
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * Setter for hasFiftyFifty.
	 * 
	 * @param The value to set hasFiftyFifty too.
	 */
	public void setHasFiftyFifty(Boolean hasFiftyFifty) 
	{
		this.hasFiftyFifty = hasFiftyFifty;
	}

	/**
	 * Setter for hasAskAudiance.
	 * 
	 * @param The value to set hasAskAudiance too.
	 */
	public void setHasAskAudiance(Boolean hasAskAudiance) 
	{
		this.hasAskAudiance = hasAskAudiance;
	}

	/**
	 * Setter for hasFinished.
	 * 
	 * @param The value to set hasFinished too.
	 */
	public void setHasFinished(Boolean hasFinished) 
	{
		this.hasFinished = hasFinished;
	}

	/**
	 * Setter for questionNo.
	 * 
	 * @param The value to set questionNo too.
	 */
	public void setQuestionNo(int questionNo) 
	{
		this.questionNo = questionNo;
	}


	/**
	 * Overridden to enable players to be sorted by how many questions they have answered.
	 */
	@Override
	public int compareTo(PlayerState arg0) 
	{
		return Integer.compare(this.getQuestionNo(), arg0.getQuestionNo());
	}
}
