package com.inhysterics.zillionaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

public class GameState {
	
	protected PlayerState[] players;	
	protected HashMap<Category, ArrayList<Question>> questions;
	protected HashSet<String> usedquestions = new HashSet<String>();
	
	protected Question lastQuestion;	
	protected int rotation;

	public GameState() 
	{			
	}	
}
