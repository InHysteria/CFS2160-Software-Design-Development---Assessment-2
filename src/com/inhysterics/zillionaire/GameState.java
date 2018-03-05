package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class GameState {
	
	protected PlayerState[] players;	
	protected HashMap<Category, ArrayList<Question>> questions;
	
	protected Question lastQuestion;
	
	protected int rotation;

	public GameState() 
	{		
	}
	public GameState(QuestionSet[] questions, PlayerState[] players) 
	{
		this();
		setQuestions(questions);
		setPlayers(players);		
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
	
	public Category[] getCategoryChoices(int count)
	{
		HashSet<Integer> avoid = new HashSet<Integer>();
		Category[] choices = questions.keySet().toArray(new Category[0]);
		Category[] out = new Category[count];
		int i = 0;
		while (i < Math.min(count, choices.length))
		{
			int r = ThreadLocalRandom.current().nextInt(choices.length);
			if (avoid.contains(r))
				continue;
			Category choice = choices[r];
			avoid.add(r);
			out[i++] = choice;
		}
		
		return out;
	}
	
	public Question getQuestion(Category key)
	{
		ArrayList<Question> choices = questions.get(key);
		int r = ThreadLocalRandom.current().nextInt(choices.size());
		return (lastQuestion = choices.get(r));
	}
	
	public Boolean[] answerQuestion(Integer choice)
	{
		PlayerState currentPlayer = getCurrentPlayer();
		if (lastQuestion.correctAnswer == choice)
		{
			currentPlayer.setQuestionNo(currentPlayer.getQuestionNo() + 1);
			
			return new Boolean[] { true, rotatePlayers() };
		}
		else
		{
			currentPlayer.hasFinished = true;
			//Drop player to last milestone
			
			return new Boolean[] { false, rotatePlayers() };
		}
	}
	public String getQuestionAnswer()
	{
		return lastQuestion.answers[lastQuestion.correctAnswer];
	}

	public PlayerState[] getPlayers() 
	{
		return players;
	}
	
	public void setQuestions(QuestionSet[] sets) 
	{
		questions = new HashMap<Category, ArrayList<Question>>();
		for (QuestionSet set : sets)
		{
			HashMap<Integer, Category> categories = set.getCategories();
			for (Question question : set.getQuestions())
			{
				for (int categoryID : question.categories)
				{
					if (!categories.containsKey(categoryID))
						continue;
					Category key = categories.get(categoryID);
					if (!questions.containsKey(key))
						questions.put(key, new ArrayList<Question>());
					
					questions.get(key).add(question);
				}
			}				
		}
		
		String taco = "";
	}
	
	public void setPlayers(PlayerState[] players) 
	{
		this.players = players;
	}
	
	
}
