package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionSet {

	protected HashMap<Integer, Category> categories;
	protected Question[] questions;
	
	
	public QuestionSet() {
		
	}

	//Still working out quite how I want these classes to be created, but in the interests of speed I'm going to do this..
	//Probably isn't correct but I've seen java classes doing this before.
	public static class Builder 
	{

		/*
		<QuestionSet>
			<Categories>
				...	
			</Categories>
			<Questions>
				...
			</Questions>
		</QuestionSet>
		*/
		public static QuestionSet CreateQuestionSet(String filepath, ArrayList<Exception> errors) {
			QuestionSet questionset = new QuestionSet();

			int i, y;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try 
			{
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document dom = builder.parse(filepath);
				Element rootElement = dom.getDocumentElement();

				NodeList categoryList = rootElement.getElementsByTagName("Categories"); //TODO: Demagicify these strings
				NodeList questionList = rootElement.getElementsByTagName("Questions");
				
				//Category
				if (categoryList.getLength() == 0) throw new Exception("Question set does not have a 'Categories' definition.");
				categoryList = categoryList.item(0).getChildNodes();
				
				questionset.categories = new HashMap<Integer, Category>();
				for (i = 0; i < categoryList.getLength(); i++)
				{
					try 
					{
						if (categoryList.item(i).getNodeType() == Node.ELEMENT_NODE)
						{
							Category category = Category.Builder.CreateCategory((Element)categoryList.item(i).getChildNodes(), errors);
							questionset.categories.putIfAbsent(category.id, category);
						}
					}
					catch (Exception e)
					{
						errors.add(e);
					}
				}
				
				//Questions
				if (questionList.getLength() == 0) throw new Exception("Question set does not have a 'Questions' definition.");
				questionList = questionList.item(0).getChildNodes();
				
				questionset.questions = new Question[questionList.getLength()];
				for (i = 0; i < questionList.getLength(); i++)
				{
					try 
					{
						if (questionList.item(i).getNodeType() == Node.ELEMENT_NODE)
						{
							Question question = Question.Builder.CreateQuestion((Element)questionList.item(i).getChildNodes(), errors);
							questionset.questions[i] = question;
						}
					}
					catch (Exception e)
					{
						errors.add(e);
					}
				}
							
				return questionset;	
			}
			catch (Exception e)
			{
				errors.add(e);
				return null;
			}		
		}
	}
	
}
