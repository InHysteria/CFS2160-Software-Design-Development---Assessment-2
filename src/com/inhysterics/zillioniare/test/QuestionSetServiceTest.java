package com.inhysterics.zillioniare.test;

import org.junit.Test;

import com.inhysterics.zillionaire.QuestionSetService;

public class QuestionSetServiceTest
{
	@Test
	public void getsLocalQuestionSets()
	{
		QuestionSetService.getLocalQuestionSets();
	}
	
	@Test
	public void getsRemoteQuestionSets()
	{
		QuestionSetService.getRemoteQuestionSets();
	}
	
	@Test 
	public void generatesManifest()
	{
		QuestionSetService.generateLocalQuestionManifest();
	}
}
