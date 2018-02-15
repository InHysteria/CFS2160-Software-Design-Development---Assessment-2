package com.inhysterics.zillionaire;

import java.lang.String;
import java.util.ArrayList;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Question {
	
	protected String body;

	protected int[] categories;
	protected String[] answers;

	protected int correctAnswer;
	protected int stage;
	
	protected Question() {
		
	}
	
	public Question(
			String message,
			String[] answers,
			
			int correctAnswer,
			int stage) 
	{
		this.body = message;
		this.answers = answers;
		
		this.correctAnswer = correctAnswer;
		this.stage = stage;
	}
	
	public String getMessage() {
		return body;
	}

	public int[] getCategories() {
		return categories;
	}
	
	public String[] getAnswers() {
		return answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public int getStage() {
		return stage;
	}	
	
	//Still working out quite how I want these classes to be created, but in the interests of speed I'm going to do this..
	//Probably isn't correct but I've seen java classes doing this before.
	public static class Builder 
	{

		/*
		<Question>
			<Category>..</Category>
			<Category>..</Category>
			...
			
			<Body>..</Body>
			<Answer>..</Answer>
			<Answer correct>..</Answer>
			<Answer>..</Answer>
			<Answer>..</Answer>
		</Question> 
		*/
		public static Question CreateQuestion(Element questionXML, ArrayList<Exception> errors) {
			Question question = new Question();

			int i;
			NodeList categoryList = questionXML.getElementsByTagName("Category"); //TODO: Demagicify these strings
			NodeList answerList = questionXML.getElementsByTagName("Answer");
			NodeList bodyList = questionXML.getElementsByTagName("Body");

			//Category
			question.categories = new int[categoryList.getLength()];
			for (i = 0; i < categoryList.getLength(); i++)
			{
				try 
				{
					question.categories[i] =  Integer.parseInt(categoryList.item(i).getTextContent());
				}
				catch (Exception e)
				{
					errors.add(e);
				}
			}
			
			//Answers
			question.answers = new String[answerList.getLength()];
			for (i = 0; i < answerList.getLength(); i++)
			{
				try 
				{
					Node currentAnswer = answerList.item(i);
					question.answers[i] =  currentAnswer.getTextContent();
					if (currentAnswer.getAttributes().getNamedItem("correct") != null)
						question.correctAnswer = i;
				}
				catch (Exception e)
				{
					errors.add(e);
				}
			}
			
			//Body
			if (bodyList.getLength() == 0) question.body = "!!NO QUESTION BODY!!";
			else question.body = bodyList.item(0).getTextContent();
						
			return question;				
		}
	}
}
