package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameState {
	
	protected PlayerState[] players;	
	protected HashMap<Category, ArrayList<Question>> questions;
	protected int countOfUniqueQuestions;
	protected HashSet<String> usedquestions = new HashSet<String>();
	
	protected Question lastQuestion;	
	protected int rotation;

	public GameState() 
	{			
	}	
}
