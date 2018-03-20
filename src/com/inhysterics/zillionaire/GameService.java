package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public final class GameService
{
	public static GameState currentGame = new GameState();	
	
	public static void newGame()
	{
		currentGame = new GameState();
	}
	
	/**
	 * Player related
	 **/
	public static boolean rotatePlayers()
	{
		currentGame.rotation++;
		int i = 0;
		while (getCurrentPlayer().getHasFinished())
		{
			currentGame.rotation++;
			i++;
			
			if (i > currentGame.players.length)
			{
				//All players have finished..
				return false;
			}
		}
		return true;
	}
		
	public static PlayerState getCurrentPlayer()
	{
		return currentGame.players[currentGame.rotation % currentGame.players.length];
	}
	
	public static PlayerState[] getPlayers() 
	{
		return currentGame.players;
	}
	
	public static void setPlayers(PlayerState[] players) 
	{
		currentGame.players = players;
	}

	private static final int[] checkpoints = new int[] { 0, 4, 12, 16 };
	public static int getScoreForQuestion(int questionNo)
	{
		if (questionNo < 4)
			return questionNo * 100;
		else if (questionNo < 12)
			return 500*(int)(Math.pow(2,questionNo-4));
		else if (questionNo < 16)
			return 125000*(int)(Math.pow(2,questionNo-12));
		else
			return 1000000;
	}
	
	public static int[] getCheckpoints()
	{
		return checkpoints;
	}
	
	/**
	 * Question Related
	 **/	
	public static Category[] getCategoryChoices(int count)
	{
		HashSet<Integer> avoid = new HashSet<Integer>();
		Category[] choices = currentGame.questions.keySet().toArray(new Category[0]);
		Category[] out = new Category[Math.min(count, choices.length)];
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

	public static Question getNewQuestion(Category key)
	{
		ArrayList<Question> categorychoices = currentGame.questions.get(key);
		ArrayList<Question> choices = new ArrayList<Question>();
				
		for (Question question : categorychoices)
			if (question.minstage <= getCurrentPlayer().questionNo 
								  && getCurrentPlayer().questionNo <= question.maxstage)
				choices.add(question);
		
		if (choices.size() == 0)
			choices.addAll(categorychoices); //Fallback
		
		if (choices.size() == 0)
			return null; //This category has no questions.
		
		ArrayList<String> choiceUUIDs = new ArrayList<String>();
		
		for (Question question : choices)
			choiceUUIDs.add(question.id);
			
		
		Question choice = null;
		while (choice == null  //While a random question hasn't been picked
			|| (currentGame.usedquestions.contains(choice.id) && !currentGame.usedquestions.containsAll(choiceUUIDs))) //Or the question has been picked before and the used questions list doesn't contain all possible choices
			choice = choices.get(ThreadLocalRandom.current().nextInt(choices.size())); //Choose a random question 
		
		currentGame.usedquestions.add(choice.id);
		return (currentGame.lastQuestion = choice);
	}
	
	public static Question getLastQuestion()
	{
		return currentGame.lastQuestion;
	}
	
	public static String getLastQuestionAnswerString()
	{
		return currentGame.lastQuestion.answers[currentGame.lastQuestion.correctAnswer];
	}
	
	public static int getNoOfUniqueQuestions()
	{
		return currentGame.countOfUniqueQuestions;
	}
	
	public static Boolean[] answerLastQuestion(Integer choice)
	{
		PlayerState currentPlayer = getCurrentPlayer();
		if (currentGame.lastQuestion.correctAnswer == choice)
		{
			currentPlayer.setQuestionNo(currentPlayer.getQuestionNo() + 1);
			if (currentPlayer.getQuestionNo() >= 16)
				currentPlayer.hasFinished = true; //Finish at 1m
			
			return new Boolean[] { true, rotatePlayers() };
		}
		else
		{
			currentPlayer.hasFinished = true;
			int questionNo = currentPlayer.getQuestionNo();
			for (int i = 0; i < checkpoints.length && checkpoints[i] <= questionNo; i++)
				currentPlayer.setQuestionNo(checkpoints[i]);			
			
			return new Boolean[] { false, rotatePlayers() };
		}
	}
	
	private static final float[] getQuestionAskTheAudianceCorrectTuning = new float[] {
		2.15f,2.15f,2.15f,2.15f,
		1.5f,1.5f,1.5f,1.25f,1.25f,1f,1f,
		0.75f,0.75f,0.75f,0.75f,
		0.5f
	};
	
	public static float[] getQuestionAskTheAudiance(ArrayList<Integer> mapping)
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int stage = getCurrentPlayer().questionNo;
		float[]	out = new float[4];
				
		for (int i = 0; i < 4; i++)
			out[mapping.get(i)] = ((currentGame.lastQuestion.correctAnswer == i && stage < getQuestionAskTheAudianceCorrectTuning.length && random.nextInt(10) != 9)
				? getQuestionAskTheAudianceCorrectTuning[stage] 
				: 0.5f) + random.nextFloat();
		
		float size = out[0] + out[1] + out[2] + out[3];
		for (int i = 0; i < 4; i++)
			out[i] = out[i] / size;
		
		return out;
	}	
	
	public static void setQuestions(QuestionSet[] sets) 
	{		
		HashSet<String> uniqueQuestionGUIDs = new HashSet<String>();
		currentGame.questions = new HashMap<Category, ArrayList<Question>>();
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
					if (!currentGame.questions.containsKey(key))
						currentGame.questions.put(key, new ArrayList<Question>());
										
					currentGame.questions.get(key).add(question);
					uniqueQuestionGUIDs.add(question.id);
				}
			}				
		}
		currentGame.countOfUniqueQuestions = uniqueQuestionGUIDs.size();
	}
}
