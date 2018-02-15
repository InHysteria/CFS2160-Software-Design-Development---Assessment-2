package com.inhysterics.zillionaire;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.inhysterics.zillionaire.ui.CategoryInterface;
import com.inhysterics.zillionaire.ui.PlayerInterface;
import com.inhysterics.zillionaire.ui.PlayerMessageInterface;
import com.inhysterics.zillionaire.ui.QuestionInterface;
import com.inhysterics.zillionaire.ui.QuestionSetInterface;

public class Zillionaire {
	
	//TODO: Go clean up/work out how best to actually do it, the XML parsing for question sets.
	//TODO: Take alllllllll these hardcoded strings and put em in a strings resource etc;
	public static void main(String args[])
	{
		if (args.length > 0 && args[0].toString().equals("/generatemanifest"))
		{
			System.out.println("Creating manifest for files in " + Paths.get(QuestionSetService.QUESTION_SET_LOCAL_PATH).toAbsolutePath().toString());
			QuestionSetService.generateLocalQuestionManifest();
			return;
		}
		
		//DEBUG!!!
		PlayerState[] debugPlayers = new PlayerState[4];
		debugPlayers[0] = new PlayerState();
		debugPlayers[0].setName("DEBUG_PLAYER_0");
		debugPlayers[0].setHasFiftyFifty(true);
		debugPlayers[0].setHasAskAudiance(false);
		debugPlayers[0].setQuestionNo(12);

		debugPlayers[1] = new PlayerState();
		debugPlayers[1].setName("DEBUG_PLAYER_1");
		debugPlayers[1].setHasFiftyFifty(true);
		debugPlayers[1].setHasAskAudiance(true);
		debugPlayers[1].setQuestionNo(12);

		debugPlayers[2] = new PlayerState();
		debugPlayers[2].setName("DEBUG_PLAYER_2");
		debugPlayers[2].setHasFiftyFifty(true);
		debugPlayers[2].setHasAskAudiance(true);
		debugPlayers[2].setQuestionNo(4);

		debugPlayers[3] = new PlayerState();
		debugPlayers[3].setName("DEBUG_PLAYER_3");
		debugPlayers[3].setHasFiftyFifty(false);
		debugPlayers[3].setHasAskAudiance(false);
		debugPlayers[3].setQuestionNo(11);
		
		ArrayList<Exception> errors = new ArrayList<Exception>();
		QuestionSet debugSet = QuestionSet.Builder.CreateQuestionSet("./questionsets/debug.questionset", errors);
		
		GameState debugGame = new GameState(debugSet, debugPlayers);


		//QuestionSetInterface questionSetInterface = new QuestionSetInterface();
		//questionSetInterface.setVisible(true);
		
		/*
		PlayerInterface playerInterface = new PlayerInterface();
		playerInterface.setGame(debugGame);
		playerInterface.setVisible(true);
		
		CategoryInterface categoryInterface = new CategoryInterface();
		categoryInterface.setCategories(debugSet.categories.values().toArray(new Category[debugSet.categories.size()]));
		categoryInterface.setPlayer(debugPlayers[0]);
		categoryInterface.setSelectionHandler(new SelectionHandler<Category>() 
		{
			@Override
			public void OnSelectionMade(Category selectedObject) 
			{				
				categoryInterface.setVisible(false);
				PlayerMessageInterface messageInterface = new PlayerMessageInterface();
				messageInterface.setPlayer(debugPlayers[0]);
				messageInterface.setMessage("You have selected " + selectedObject.getName());
				messageInterface.setVisible(true);
			}
		});
		categoryInterface.setVisible(true);
		*/
		/*
		QuestionInterface questionInterface = new QuestionInterface();
		questionInterface.setQuestion(debugSet.questions[0]);
		questionInterface.setPlayer(debugPlayers[0]);
		questionInterface.setVisible(true);
		*/
		
		
		/*
		configInterface = new ConfigInterface();		
		configInterface.setVisible(true);
		*/
	}
	
}
