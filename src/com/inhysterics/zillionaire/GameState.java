package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Holds information relating to the state of the game.
 */
public class GameState 
{
	/**
	 * A list of the players involved in this game.
	 */
	protected PlayerState[] players;	
	
	/**
	 * A map of the questions in this game, stored against any categories they belong too.
	 */
	protected HashMap<Category, ArrayList<Question>> questions;
	
	/**
	 * How many unique questions have been loaded into this game.
	 */
	protected int countOfUniqueQuestions;
	
	/**
	 * A hash set of all the UID's of the questions which have been used.
	 * 
	 * Used to prevent the same question coming up more than once.
	 */
	protected HashSet<String> usedquestions = new HashSet<String>();
	
	/**
	 * The last question which was used in this game.
	 */
	protected Question lastQuestion;	
	
	/**
	 * Index of the current player when passed through a modulus opperation.
	 * Such that...
	 * 	currentPlayer = players[rotation%players.length]
	 */
	protected int rotation;

	/**
	 * Getter for players.
	 * 
	 * @return The players field.
	 */
	public PlayerState[] getPlayers()
	{
		return this.players;
	}

	/**
	 * Getter for questions.
	 * 
	 * @return The questions field.
	 */
	public HashMap<Category, ArrayList<Question>> getQuestions()
	{
		return this.questions;
	}

	/**
	 * Getter for countOfUniqueQuestions.
	 * 
	 * @return The countOfUniqueQuestions field.
	 */
	public int getCountOfUniqueQuestions()
	{
		return this.countOfUniqueQuestions;
	}

	/**
	 * Getter for usedquestions.
	 * 
	 * @return The usedquestions field.
	 */
	public HashSet<String> getUsedquestions()
	{
		return this.usedquestions;
	}

	/**
	 * Getter for lastQuestion.
	 * 
	 * @return The lastQuestion field.
	 */
	public Question getLastQuestion()
	{
		return this.lastQuestion;
	}

	/**
	 * Getter for rotation.
	 * 
	 * @return The rotation field.
	 */
	public int getRotation()
	{
		return this.rotation;
	}

	/**
	 * Setter for players.
	 * 
	 * @param The value to set players too.
	 */
	public void setPlayers(PlayerState[] players)
	{
		this.players = players;
	}

	/**
	 * Setter for questions.
	 * 
	 * @param The value to set questions too.
	 */
	public void setQuestions(HashMap<Category, ArrayList<Question>> questions)
	{
		this.questions = questions;
	}

	/**
	 * Setter for countOfUniqueQuestions.
	 * 
	 * @param The value to set countOfUniqueQuestions too.
	 */
	public void setCountOfUniqueQuestions(int countOfUniqueQuestions)
	{
		this.countOfUniqueQuestions = countOfUniqueQuestions;
	}

	/**
	 * Setter for usedquestions.
	 * 
	 * @param The value to set usedquestions too.
	 */
	public void setUsedquestions(HashSet<String> usedquestions)
	{
		this.usedquestions = usedquestions;
	}

	/**
	 * Setter for lastQuestion.
	 * 
	 * @param The value to set lastQuestion too.
	 */
	public void setLastQuestion(Question lastQuestion)
	{
		this.lastQuestion = lastQuestion;
	}

	/**
	 * Setter for rotation.
	 * 
	 * @param The value to set rotation too.
	 */
	public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}
	
	
}
