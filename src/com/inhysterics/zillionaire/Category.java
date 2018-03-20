package com.inhysterics.zillionaire;

import java.util.ArrayList;

import org.w3c.dom.Element;

/**
 * Represents a Category as defined in a QuestionSet.
 * 
 * @see QuestionSet
 */
public class Category 
{
	/**
	 * A number representing this Category.
	 */
	protected int id;
	
	/**
	 * A user friendly string defining this category.
	 */
	protected String name;
	
	/**
	 * Constructor for a category.
	 * 
	 * @param id A number representing this Category.
	 * @param name A user friendly string defining this category.
	 */
	public Category(int id, String name) 
	{
		this.id = id;
		this.name = name;
	}	

	/**
	 * Getter for the id field.
	 * 
	 * @returns The id of this category.
	 */
	public int getId() 
	{
		return id;
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
	 * Calls the getName method and returns its value.
	 * 
	 * @returns The name of this category.
	 */
	@Override
	public String toString() 
	{
		return getName(); 
	}

	/**
	 * Compares this category against another.
	 * 
	 * @param obj The category to compare against.
	 * @returns Whether this category is equal to obj.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Category))
			return false;
		return this.getName().equals(((Category)obj).getName());
	}
	
	/**
	 * Computes a hashCode of this category.
	 * 
	 * @returns A hash which represents this category.
	 */
	@Override
	public int hashCode()
	{
		return getName().hashCode();
	}


	/**
	 * Utility class for creating categories from XML.
	 * 
	 * @see Category
	 */
	public static class Builder 
	{

		/*
			<CategoryDefinition id="..">...</CategoryDefinition>
		*/
		/**
		 * Reads an XML element and attempts to load category data from it.
		 * 
		 * @param categoryXML A parsed XML node which is to be read for category information.
		 * @param errors An array list to log any XML errors to.
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
