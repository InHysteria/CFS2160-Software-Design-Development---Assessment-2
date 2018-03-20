package com.inhysterics.zillionaire;

/**
 * Interface for defining event interactions.
 */
public interface SelectionHandler<T>
{	
	/**
	 * Called when an interface has made a selection.
	 * 
	 * @param selectedObject The selected object.
	 */
	public void OnSelectionMade(T selectedObject);
}
