package com.inhysterics.zillionaire;

import com.inhysterics.zillionaire.ui.ConfigInterface;
import com.inhysterics.zillionaire.ui.PlayerInterface;

public class Zillionaire {

	protected static PlayerInterface gameInterface;
	protected static ConfigInterface configInterface;
	
	public static void main(String args[])
	{
		//DEBUG!!!
		/*
		PlayerState state = new PlayerState();
		state.setName("DEBUG_PLAYER");
		state.DEBUG__setQuestionNo(-1);
		
		
		gameInterface = new PlayerInterface();
		gameInterface.setPlayer(state);		
		gameInterface.setVisible(true);
		*/
		
		configInterface = new ConfigInterface();		
		configInterface.setVisible(true);
	}
}
