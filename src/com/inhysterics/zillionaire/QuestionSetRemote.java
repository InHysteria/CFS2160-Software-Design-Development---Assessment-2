package com.inhysterics.zillionaire;

/**
 * Represents a question set that exists on the remote download server.
 * 
 * @see QuestionSet
 * @see QuestionSetService
 */
public class QuestionSetRemote 
{
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
	 * How many questions there are in the question set when it is downloaded.
	 */
	protected int questions;
	
	/**
	 * The name of the questionset on the remote server. Used when downloading the questionset file.
	 */
	protected String remote;
	
	/**
	 * Default constructor
	 */
	public QuestionSetRemote()
	{
		this.name = null;
		this.version = null;
		this.author = null;
		this.description = null;
		this.questions = -1;
		this.remote = null;
	}

	/**
	 * Constructor which populates all fields.
	 */
	public QuestionSetRemote(String name, String version, String author, String description, int questions, String remote)
	{
		this.name = name;
		this.version = version;
		this.author = author;
		this.description = description;
		this.questions = questions;
		this.remote = remote;
	}

	/**
	 * Getter for name.
	 * 
	 * @return The name field.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Getter for version.
	 * 
	 * @return The version field.
	 */
	public String getVersion() 
	{
		return version;
	}

	/**
	 * Getter for author.
	 * 
	 * @return The author field.
	 */
	public String getAuthor() 
	{
		return author;
	}

	/**
	 * Getter for description.
	 * 
	 * @return The description field.
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * Getter for remote.
	 * 
	 * @return The remote field.
	 */
	public String getRemote() 
	{
		return remote;
	}

	/**
	 * Downloads and saves this questionset in the local filesystem.
	 */
	public void download()
	{
		if (name != null)
			QuestionSetService.pullFromGit("questionsets/"+remote, remote);
	}

	/**
	 * Formats the data inside the question set for display inside a JLabel.
	 */
	@Override
	public String toString()
	{
		if (name == null)
			return "Contacting server..";
		return String.format("<html>%s v%s<br/>&nbsp;&nbsp;&nbsp;&nbsp;contains %s questions<br/>&nbsp;&nbsp;&nbsp;&nbsp;by %s<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<i>%s</i><br/>&nbsp;</html>", 
				name, 
				version, 
				questions, 
				author, 
				description);  
	}
}
