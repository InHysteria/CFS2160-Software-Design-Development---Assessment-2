package com.inhysterics.zillioniare.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import com.inhysterics.zillionaire.GameService;
import com.inhysterics.zillionaire.Question;
import com.inhysterics.zillionaire.QuestionSet;

public class QuestionSetTest
{
	protected static final File QUESTION_SET_SOURCE = new File("./test.questionset");

	private static final String _EXPECTED_NAME = "Test Question Set";
	private static final String _EXPECTED_VERSION = "0.0.1";
	private static final String _EXPECTED_AUTHOR = "Gemma Mallinson";
	private static final String _EXPECTED_DESCRIPTION = "This is a test set not to be included in release, it is used during unit testing.";
	private static final String _EXPECTED_CATEGORY_NAME = "cat1";
	
	private static final int _EXPECTED_QUESTION_CATEGORY = 0;
	private static final int _EXPECTED_QUESTION_MINSTAGE = 0;
	private static final int _EXPECTED_QUESTION_MAXSTAGE = 100;
	private static final String _EXPECTED_QUESTION_BODY = "b9d25dee-769c-4895-a5da-2444d7abee9c";
	private static final String[] _EXPECTED_QUESTION_ANSWERS = new String[] { "a", "b", "c", "d" };
	private static final int _EXPECTED_QUESTION_CORRECT_ANSWER = 1;
	
	public QuestionSetTest()
	{
	}

	@Test
	public void createsTheSet()
	{
		if (!QUESTION_SET_SOURCE.exists())
			fail("Debug question set must exist in order to test question set class.");
		
		ArrayList<Exception> exceptions = new ArrayList<Exception>();
		QuestionSet set = QuestionSet.Builder.CreateQuestionSet(QUESTION_SET_SOURCE.getAbsolutePath(), exceptions);

		assertTrue(exceptions.size() == 0);
		assertEquals(_EXPECTED_NAME, set.getName());
		assertEquals(_EXPECTED_VERSION, set.getVersion());
		assertEquals(_EXPECTED_AUTHOR,set.getAuthor());
		assertEquals(_EXPECTED_DESCRIPTION,set.getDescription());

		assertTrue(set.getCategories().size() == 1);
		assertEquals(_EXPECTED_CATEGORY_NAME,set.getCategories().get(0).getName());
		
		assertTrue(set.getQuestions().length == 1);
		Question question = set.getQuestions()[0];
		assertTrue(question.getCategories().length == 1);
		assertEquals(_EXPECTED_QUESTION_CATEGORY, question.getCategories()[0]);
		assertEquals(_EXPECTED_QUESTION_MINSTAGE, question.getMinStage());
		assertEquals(_EXPECTED_QUESTION_MAXSTAGE, question.getMaxStage());
		assertEquals(_EXPECTED_QUESTION_BODY, question.getMessage());
		assertArrayEquals(_EXPECTED_QUESTION_ANSWERS, question.getAnswers());
		assertEquals(_EXPECTED_QUESTION_CORRECT_ANSWER, question.getCorrectAnswer());
	}
}
