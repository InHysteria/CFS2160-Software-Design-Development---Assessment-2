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

/**
 * Represents a set of questions and categories.
 * 
 * @see Question
 * @see Category
 */
public class QuestionSet {

	/**
	 * User friendly name of this question set.
	 */
	protected String name;

	/**
	 * Version number of this question set. Usually incremented every time a change happens to a question set so that people know to update.
	 */ 
	protected String version;

	/**
	 * The name of the author of this question set.
	 */
	protected String author;

	/**
	 * A description of this question set.
	 */
	protected String description;

	/**
	 * The local filename of this question set.
	 */
	protected String filename;

	/**
	 * A dictionary of the categories inside this question set.
	 */
	protected HashMap<Integer, Category> categories;

	/**
	 * An array of questions in this question set.
	 */
	protected Question[] questions;	

	/**
	 * Formats the data inside the question set for display inside a JLabel.
	 */
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

	/**
	 * Getter for the name field.
	 * 
	 * @returns The name of this category.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Getter for the version field.
	 * 
	 * @returns The version of this category.
	 */
	public String getVersion() 
	{
		return version;
	}

	/**
	 * Getter for the author field.
	 * 
	 * @returns The author of this category.
	 */
	public String getAuthor() 
	{
		return author;
	}

	/**
	 * Getter for the filename field.
	 * 
	 * @returns The filename of this category.
	 */
	public String getFilename() 
	{
		return filename;
	}

	/**
	 * Getter for the description field.
	 * 
	 * @returns The description of this category.
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * Getter for the categories field.
	 * 
	 * @returns The categories of this category.
	 */
	public HashMap<Integer, Category> getCategories() 
	{
		return categories;
	}

	/**
	 * Getter for the questions field.
	 * 
	 * @returns The questions of this category.
	 */
	public Question[] getQuestions() {
		return questions;
	}

	/**
	 * Utility class for creating questionsets from XML.
	 * 
	 * @see QuestionSet
	 */
	public static class Builder 
	{

		/*
		<QuestionSet>
			[<Name>..</Name>]
			[<Version>..</Version>]
			[<Author>..</Author>]
			[<Description>..</Description>]
			<Categories>
				...	
			</Categories>
			<Questions>
				...
			</Questions>
		</QuestionSet>
		*/
		/**
		 * Loads and parses an XML file and attempts to read question set data from it.
		 * 
		 * @param filepath A local filepath to an XML file.
		 * @param errors An array list to log any XML errors to.
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

				NodeList nameList = rootElement.getElementsByTagName("Name"); 
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
