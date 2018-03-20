package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages interactions between the interface and the game state.
 *
 * @see GameState
 */
public final class GameService
{
	/**
	 * The currently active GameState. Can be reset with newGame().
	 * 
	 * @see GameState
	 * @see GameService.newGame()
	 */
	public static GameState currentGame = new GameState();	
	
	/**
	 * Creates a new GameState, abandoning the old.
	 */
	public static void newGame()
	{
		currentGame = new GameState();
	}
	
	/**
	 * Increments the turn tracker until it is pointing at a player who hasn't finished.
	 * 
	 * @return True if a new player was pointed at. False if all players have finished, indicating that the game has ended.
	 * @see PlayerState
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
		
	/**
	 * Gets the active player.
	 * 
	 * @return A state representing the active player.
	 * @see PlayerState
	 */
	public static PlayerState getCurrentPlayer()
	{
		return currentGame.players[currentGame.rotation % currentGame.players.length];
	}

	/**
	 * Gets an array of all the players.
	 * 
	 * @return An array of all players.
	 */
	public static PlayerState[] getPlayers() 
	{
		return currentGame.players;
	}
	
	/**
	 * Sets the player list.
	 * 
	 * @param players An array of players to set to the player list.
	 */
	public static void setPlayers(PlayerState[] players) 
	{
		currentGame.players = players;
	}

	/**
	 * An array of values to drop a player down to when they answer a question wrong.
	 * When the player answers a question wrong, their questionNo is lowered to the 
	 * highest value in this list that isn't higher than their current.
	 * 
	 * I.E.
	 *   
	 *   2 > 0
	 *   3 > 0
	 *   4 > 4
	 *   5 > 4
	 *   
	 *	@see PlayerState.questionNo
	 *  @see GameService.getScoreForQuestion
	 */
	private static final int[] _checkpoints = new int[] { 0, 4, 12, 16 };
	
	/**
	 * Maps the players questionNo to a monetary value.
	 * 
	 * Increases in a semi-standard manner.
	 * 
	 * @params questionNo How many questions the player has answered correctly.
	 * @returns A monetary value.
	 *	@see PlayerState.questionNo
	 */
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
	
	/**
	 * Gets the list of checkpoints.
	 * 
	 * @return The list of checkpoints.
	 */
	public static int[] getCheckpoints()
	{
		return _checkpoints;
	}
	
	/**
	 * Picks random categories.
	 * 
	 * @param count How many categories to pick.
	 * @return An array of 'count' categories.
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

	/**
	 * Picks a random unused question from a particular category.
	 * If all questions in that category have been used, instead pick a random one.
	 * Also sets lastQuestion to the returned value.
	 * 
	 * @param key The category to pick questions from.
	 * @return A random (unused if possible) question.
	 */
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
	
	/**
	 * Gets the last question which was returned from getNewQuestion.
	 * 
	 * @return The last question which was returned from getNewQuestion. 
	 */
	public static Question getLastQuestion()
	{
		return currentGame.lastQuestion;
	}
	
	/**
	 * Gets the text of the answer of the last question returned from getNewQuestion.
	 * 
	 * @return The text of the answer of the last question returned from getNewQuestion.
	 */
	public static String getLastQuestionAnswerString()
	{
		return currentGame.lastQuestion.answers[currentGame.lastQuestion.correctAnswer];
	}
	
	/**
	 * Gets how many unique questions that are currently loaded.
	 * 
	 * @return The number of unique questions that are currently loaded.
	 */
	public static int getNoOfUniqueQuestions()
	{
		return currentGame.countOfUniqueQuestions;
	}
	
	/**
	 * Takes an integer (0-3) representing which answer a user chose, then handles all the interactions that occur from choosing that answer.
	 * 
	 * If the answer was correct it increases the players questionNo then rotates players.
	 * If the answer was incorrect it drops the player down to highest checkpoint that isn't higher than their current questionNo then rotates players.
	 * 
	 * @return An array of two booleans, the first represents if the user answered the question correctly, the second is the output of rotatePlayers().
	 * @see GameService.rotatePlayers()
	 */
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
			for (int i = 0; i < _checkpoints.length && _checkpoints[i] <= questionNo; i++)
				currentPlayer.setQuestionNo(_checkpoints[i]);			
			
			return new Boolean[] { false, rotatePlayers() };
		}
	}
	
	/**
	 * Sets tuning values for how correct the audience is for any given question.
	 * 
	 * @see GameService.getQuestionAskTheAudiance
	 */
	private static final float[] _getQuestionAskTheAudianceCorrectTuning = new float[] {
		2.15f,2.15f,2.15f,2.15f,
		1.5f,1.5f,1.5f,1.25f,1.25f,1f,1f,
		0.75f,0.75f,0.75f,0.75f,
		0.5f
	};
	
	/**
	 * Generates random percentiles for each answer of the current question, with a preference for the correct one based on how many question have already been answered.
	 * The result is also mapped to the randomised answers provided by mapping.
	 * 
	 * @param A mapping that denotes how the questions were randomised.
	 * @return A normalised array of floats associated with each answer of the question, weighted towards the correct answer.
	 */
	public static float[] getQuestionAskTheAudiance(ArrayList<Integer> mapping)
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int stage = getCurrentPlayer().questionNo;
		float[]	out = new float[4];
				
		//For each answer..
		//  If the current answer is the correct one
		//  And this roll hasn't been chosen to be completely random.
		//    Set the value to the tuning value + variance of 0.0-1.0
		//  Otherwise
		//	  Set the value to 0.5 + variance of 0.0-1.0
		for (int i = 0; i < 4; i++)
			out[mapping.get(i)] = ((currentGame.lastQuestion.correctAnswer == i && random.nextInt(10) != 9)
				? _getQuestionAskTheAudianceCorrectTuning[Math.min(stage, _getQuestionAskTheAudianceCorrectTuning.length)] 
				: 0.5f) + random.nextFloat();
		
		//Then normalise the generated values, causing them to sum to 1.
		float size = out[0] + out[1] + out[2] + out[3];
		for (int i = 0; i < 4; i++)
			out[i] = out[i] / size;
		
		return out;
	}	
	
	/**
	 * Builds the question list from an array of question sets.
	 * 
	 * Generates a HashMap where the Key is a category and it's value is an ArrayList of questions belonging too it.
	 * Note, because a question can belong to multiple categories, a question may appear in multiple different lists.
	 * 
	 * @param set An array of QuestionSets to load questions from.
	 * @see QuestionSet
	 */
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
