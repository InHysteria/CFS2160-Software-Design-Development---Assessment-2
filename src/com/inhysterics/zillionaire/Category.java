package com.inhysterics.zillionaire;

import java.util.ArrayList;

import org.w3c.dom.Element;

public class Category {

	protected int id;
	protected String name;
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	

	@Override
	public String toString() {
		return getName(); 
	}



	//Still working out quite how I want these classes to be created, but in the interests of speed I'm going to do this..
	//Probably isn't correct but I've seen java classes doing this before.
	public static class Builder 
	{

		/*
			<CategoryDefinition id="..">...</CategoryDefinition>
		*/
		public static Category CreateCategory(Element categoryXML, ArrayList<Exception> errors) {
			int id = -1;
			
			try 
			{
				id = Integer.parseInt(categoryXML.getAttribute("id"));
			}
			catch (Exception e)
			{
				errors.add(e);
			}
			
			return new Category(
					id,
					categoryXML.getTextContent());
		}
	}
}
