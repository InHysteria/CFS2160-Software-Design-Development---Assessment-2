package com.inhysterics.zillionaire;

public class GameState {
	
	protected QuestionSet set;
	protected PlayerState[] players;
	
	protected int rotation;

	public GameState(QuestionSet set, PlayerState[] players) {
		this.set = set;
		this.players = players;		
	}
	
	public boolean rotatePlayers()
	{
		rotation++;
		int i = 0;
		while (getCurrentPlayer().getHasFinished())
		{
			rotation++;
			i++;
			
			if (i > players.length)
			{
				//All players have finished..
				return false;
			}
		}
		return true;
	}
	
	public PlayerState getCurrentPlayer()
	{
		return players[rotation % players.length];
	}

	public PlayerState[] getPlayers() 
	{
		return players;
	}
}
