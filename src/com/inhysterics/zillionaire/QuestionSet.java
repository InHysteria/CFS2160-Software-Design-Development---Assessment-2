package com.inhysterics.zillionaire;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QuestionSet {

	protected String name;
	protected String version;
	protected String author;
	protected String description;
	protected String filename;
	
	protected HashMap<Integer, Category> categories;
	protected Question[] questions;
	
	public QuestionSet() {
		
	}

	@Override
	public String toString()
	{
		return String.format("<html>%s v%s<br/>&nbsp;&nbsp;&nbsp;&nbsp;contains %s questions<br/>&nbsp;&nbsp;&nbsp;&nbsp;by %s<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<i>%s</i><br/>&nbsp;</html>", 
				name, 
				version, 
				questions.length, 
				author,
				description);  
	}
	

	public String getName() 
	{
		return name;
	}

	public String getVersion() 
	{
		return version;
	}

	public String getAuthor() 
	{
		return author;
	}

	public String getFilename() 
	{
		return filename;
	}

	public String getDescription() 
	{
		return description;
	}

	public HashMap<Integer, Category> getCategories() 
	{
		return categories;
	}

	public Question[] getQuestions() {
		return questions;
	}



	//Still working out quite how I want these classes to be created, but in the interests of speed I'm going to do this..
	//Probably isn't correct but I've seen java classes doing this before.
	public static class Builder 
	{

		/*
		<QuestionSet>
			[<Name>..</Name>]
			[<Version>..</Version>]
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

			int i;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			try 
			{
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document dom = builder.parse(filepath);
				Element rootElement = dom.getDocumentElement();

				NodeList nameList = rootElement.getElementsByTagName("Name"); //TODO: Demagicify these strings
				NodeList versionList = rootElement.getElementsByTagName("Version");
				NodeList authorList = rootElement.getElementsByTagName("Author");
				NodeList descriptionList = rootElement.getElementsByTagName("Description");
				NodeList categoryList = rootElement.getElementsByTagName("Categories");
				NodeList questionList = rootElement.getElementsByTagName("Questions");
				
				//Name
				if (nameList.getLength() == 0) questionset.name = Paths.get(filepath).getFileName().toString();
				else questionset.name = nameList.item(0).getTextContent();
				
				//Version
				if (versionList.getLength() == 0) questionset.version = "0.0";
				else questionset.version = versionList.item(0).getTextContent();
				
				//Author
				if (authorList.getLength() == 0) questionset.author = "Anonymous";
				else questionset.author = authorList.item(0).getTextContent();
				
				//Description
				if (descriptionList.getLength() == 0) questionset.description = "This question set has no description.";
				else questionset.description = descriptionList.item(0).getTextContent();
				
				//Category
				if (categoryList.getLength() == 0) throw new Exception("Question set does not have a 'Categories' definition.");
				categoryList = ((Element)categoryList.item(0).getChildNodes()).getElementsByTagName("CategoryDefinition");
				
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
				questionList = ((Element)questionList.item(0).getChildNodes()).getElementsByTagName("Question");
				
				questionset.questions = new Question[questionList.getLength()];
				for (i = 0; i < questionList.getLength(); i++)
				{
					try 
					{
						Node questionNode = questionList.item(i);
						if (questionNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Question question = Question.Builder.CreateQuestion((Element)questionNode.getChildNodes(), errors);
							questionset.questions[i] = question;
						}
					}
					catch (Exception e)
					{
						errors.add(e);
					}
				}
				
				questionset.filename = Paths.get(filepath).getFileName().toString();
							
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
