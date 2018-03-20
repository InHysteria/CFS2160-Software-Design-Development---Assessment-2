package com.inhysterics.zillioniare.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.inhysterics.zillionaire.Category;
import com.inhysterics.zillionaire.GameService;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;
import com.inhysterics.zillionaire.QuestionSet;

public class GameServiceTest
{		
	private static PlayerState[] _expectedPlayers = new PlayerState[0];
	
	@Test
	public void setsTheQuestions()
	{
		if (!QuestionSetTest.QUESTION_SET_SOURCE.exists())
			fail("Debug question set must exist in order to test question set class.");

		ArrayList<Exception> exceptions = new ArrayList<Exception>();
		QuestionSet[] sets = new QuestionSet[ThreadLocalRandom.current().nextInt(100)+1];
		for (int i = 0; i < sets.length; i++)
			sets[i] = QuestionSet.Builder.CreateQuestionSet(QuestionSetTest.QUESTION_SET_SOURCE.getAbsolutePath(), exceptions);

		assertTrue(exceptions.size() == 0);
		GameService.setQuestions(sets);
	}
	
	@Test
	public void setsThePlayers()
	{
		_expectedPlayers = new PlayerState[ThreadLocalRandom.current().nextInt(20)+2];
		for (int i = 0; i < _expectedPlayers.length; i++)
		{
			_expectedPlayers[i] = new PlayerState();
			_expectedPlayers[i].setName(UUID.randomUUID().toString());
			_expectedPlayers[i].setQuestionNo(0);
		}
		
		GameService.setPlayers(_expectedPlayers);
		assertArrayEquals(_expectedPlayers, GameService.getPlayers());
	}
	
	@Test
	public void rotatesThePlayers()
	{
		GameService.newGame();
		setsThePlayers();
		
		assertEquals(_expectedPlayers[0], GameService.getCurrentPlayer());
		assertTrue(GameService.rotatePlayers());
		assertEquals(_expectedPlayers[1], GameService.getCurrentPlayer());
		_expectedPlayers[2 % _expectedPlayers.length].setHasFinished(true);
		assertTrue(GameService.rotatePlayers());
		assertNotEquals(_expectedPlayers[2 % _expectedPlayers.length], GameService.getCurrentPlayer());
	}
	
	@Test
	public void getsThePlayers()
	{
		GameService.newGame();
		setsThePlayers();
		assertArrayEquals(_expectedPlayers, GameService.getPlayers());
	}
	
	@Test
	public void simulatesTheGame()
	{
		GameService.newGame();
		setsTheQuestions();
		setsThePlayers();
		
		int cycles = 0;
		int expectedQuestionsBeforeDuplication = GameService.getNoOfUniqueQuestions();
		HashSet<String> questionsUsed = new HashSet<String>();
		int lengthOfPlayers = GameService.getPlayers().length;
		
		
		while (true)
		{			
			assertFalse(GameService.getCurrentPlayer().getHasFinished());
			
			//Generate scores for all players
			for (PlayerState player : GameService.getPlayers())
				player.getScore();
			
			//Select three categories
			Category[] categories = GameService.getCategoryChoices(3); //NB, will only return 1 as the test data only has 1 category. This is an intended feature and isn't considered an error.
			
			assertTrue(categories.length > 0);
			
			//Select question and ensure that is hasn't been selected before
			Question question = GameService.getNewQuestion(categories[ThreadLocalRandom.current().nextInt(categories.length)]);
			
			
			if (!questionsUsed.add(question.getID()))
				if (questionsUsed.size() != expectedQuestionsBeforeDuplication)
					fail("A question has been drawn multiple times before the list is empty.");

			//Pretend to answer
			if (   ThreadLocalRandom.current().nextInt(2) == 1
				|| (cycles%lengthOfPlayers) == 0
				|| GameService.getCurrentPlayer().getQuestionNo() < 6)
				GameService.answerLastQuestion(question.getCorrectAnswer());
			else
				GameService.answerLastQuestion(-1);		
			
			//Rotate players then end the game if necessary.
			if (!GameService.rotatePlayers())
				break;
			
			
			cycles++;
		}
	}
}
