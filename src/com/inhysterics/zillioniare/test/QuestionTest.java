package com.inhysterics.zillioniare.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.inhysterics.zillionaire.Question;

public class QuestionTest
{
	private final String _EXPECTED_MESSAGE = UUID.randomUUID().toString();
	private final String[] _EXPECTED_ANSWERS = new String[] { 
			UUID.randomUUID().toString(), 
			UUID.randomUUID().toString(), 
			UUID.randomUUID().toString(), 
			UUID.randomUUID().toString()
	};
	
	private final int _EXPECTED_CORRECTANSWER = ThreadLocalRandom.current().nextInt(4);
	private final int _EXPECTED_MINSTAGE = ThreadLocalRandom.current().nextInt(16);
	private final int _EXPECTED_MAXSTAGE = ThreadLocalRandom.current().nextInt(_EXPECTED_MINSTAGE,16);
	
	private final Question _question;
	
	public QuestionTest()
	{
		_question = new Question(
			_EXPECTED_MESSAGE,
			_EXPECTED_ANSWERS,
			
			_EXPECTED_CORRECTANSWER,
			_EXPECTED_MINSTAGE,
			_EXPECTED_MAXSTAGE
		);
	}
	
	@Test
	public void getsMessage()
	{
		assertEquals(_EXPECTED_MESSAGE, _question.getMessage());
	}
	
	@Test
	public void getsAnswers()
	{
		assertArrayEquals(_EXPECTED_ANSWERS, _question.getAnswers());
	}
	
	@Test
	public void getsCorrectAnswer()
	{
		assertEquals(_EXPECTED_CORRECTANSWER, _question.getCorrectAnswer());
	}
	
	@Test
	public void getsMinStage()
	{
		assertEquals(_EXPECTED_MINSTAGE, _question.getMinStage());
	}
	
	@Test
	public void getsMaxStage()
	{
		assertEquals(_EXPECTED_MAXSTAGE, _question.getMaxStage());
	}
}
