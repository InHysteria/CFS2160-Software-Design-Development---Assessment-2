package com.inhysterics.zillionaire;

public class QuestionSetRemote 
{
	protected String name;
	protected String version;
	protected String author;
	protected int questions;
	protected String remote;
	
	public QuestionSetRemote()
	{
		this.name = null;
		this.version = null;
		this.author = null;
		this.questions = -1;
		this.remote = null;
	}
	
	public QuestionSetRemote(String name, String version, String author, int questions, String remote)
	{
		this.name = name;
		this.version = version;
		this.author = author;
		this.questions = questions;
		this.remote = remote;
	}

	public String getName() 
	{
		return name;
	}

	public String getVersion() 
	{
		return version;
	}
	
	public String getRemote() 
	{
		return remote;
	}
	
	public void download()
	{
		if (name != null)
			QuestionSetService.pullFromGit("questionsets/"+remote+".questionset", remote+"_remote.questionset");
	}
	
	@Override
	public String toString()
	{
		if (name == null)
			return "Contacting server..";
		return String.format("<html>%s v%s<br/>&nbsp;&nbsp;&nbsp;&nbsp;contains %s questions<br/>&nbsp;&nbsp;&nbsp;&nbsp;by %s</html>", name, version, questions, author);  
	}
}
