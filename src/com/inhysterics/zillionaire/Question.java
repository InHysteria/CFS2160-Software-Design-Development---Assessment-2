package com.inhysterics.zillionaire;

import java.lang.String;
import java.util.ArrayList;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Represents a Question as defined in a QuestionSet.
 * 
 * @see QuestionSet
 */
public class Question {
	
	/**
	 * A guid used for identifying this Question, generated when this question was loaded from XML.
	 */
	protected String id;
	
	/**
	 * The message to display for this question.
	 */
	protected String body;

	/**
	 * The id's of the categories this question belongs to.
	 */
	protected int[] categories;
	
	/**
	 * An array of 4 answers which are used with this question.
	 */
	protected String[] answers;

	/**
	 * Which of the answers is the correct one.
	 */
	protected int correctAnswer;
	
	/**
	 * How many questions must be answered before this question can appear. Higher represents a harder question.
	 */
	protected int minstage;
	
	/**
	 * After how many questions, will this question stop appearing. Higher represents a harder question.
	 */
	protected int maxstage;
	
	/**
	 * Constructor for manual population.
	 */
	protected Question() {
		
	}
	
	/**
	 * Constructor which populates all fields.
	 */
	public Question(
			String message,
			String[] answers,
			
			int correctAnswer,
			int minstage,
			int maxstage) 
	{
		this.id = java.util.UUID.randomUUID().toString();
		this.body = message;
		this.answers = answers;
		
		this.correctAnswer = correctAnswer;
		this.minstage = minstage;
		this.maxstage = maxstage;
	}


	/**
	 * Getter for id.
	 * 
	 * @return The id field.
	 */
	public String getID()
	{
		return id;
	}

	/**
	 * Getter for body.
	 * 
	 * @return The body field.
	 */
	public String getMessage() {
		return body;
	}

	/**
	 * Getter for categories.
	 * 
	 * @return The categories field.
	 */
	public int[] getCategories() {
		return categories;
	}

	/**
	 * Getter for answers.
	 * 
	 * @return The answers field.
	 */
	public String[] getAnswers() {
		return answers;
	}

	/**
	 * Getter for correctAnswer.
	 * 
	 * @return The correctAnswer field.
	 */
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * Getter for minstage.
	 * 
	 * @return The minstage field.
	 */
	public int getMinStage() {
		return minstage;
	}	

	/**
	 * Getter for maxstage.
	 * 
	 * @return The maxstage field.
	 */
	public int getMaxStage() {
		return maxstage;
	}	

	/**
	 * Utility class for creating questions from XML.
	 * 
	 * @see Question
	 */
	public static class Builder 
	{

		/*
		<Question>
			<Category>..</Category>
			<Category>..</Category>
			...
			
			<Body>..</Body>
			<Answer>..</Answer>
			<Answer correct="">..</Answer>
			<Answer>..</Answer>
			<Answer>..</Answer>
		</Question> 
		*/
		/**
		 * Reads an XML element and attempts to load question data from it.
		 * 
		 * @param questionXML A parsed XML node which is to be read for question information.
		 * @param errors An array list to log any XML errors to.
		 */
		public static Question CreateQuestion(Element questionXML, ArrayList<Exception> errors) {
			Question question = new Question();
			
			question.id = java.util.UUID.randomUUID().toString();

			int i;
			NodeList categoryList = questionXML.getElementsByTagName("Category"); //TODO: Demagicify these strings
			NodeList answerList = questionXML.getElementsByTagName("Answer");
			NodeList bodyList = questionXML.getElementsByTagName("Body");
			NodeList minStageList = questionXML.getElementsByTagName("MinStage");
			NodeList maxStageList = questionXML.getElementsByTagName("MaxStage");
			
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
			
			//MinStage
			if (minStageList.getLength() == 0) question.minstage = 0;
			else
			{
				try 
				{
					question.minstage = Integer.parseInt(minStageList.item(0).getTextContent()); 
				}
				catch (Exception e)
				{
					errors.add(e);
				}
			}

			
			//MaxStage
			if (minStageList.getLength() == 0) question.maxstage = question.minstage;
			else
			{
				try 
				{
					question.maxstage = Integer.parseInt(maxStageList.item(0).getTextContent()); 
				}
				catch (Exception e)
				{
					errors.add(e);
				}
			}
						
			return question;				
		}
	}
}
