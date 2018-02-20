package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.inhysterics.zillionaire.Category;
import com.inhysterics.zillionaire.GameState;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.QuestionSet;
import com.inhysterics.zillionaire.SelectionHandler;

/*

FIRST_PASS: ConfigInterface
	V
FIRST_PASS: QuestionSetInterface
	V
FIRST_PASS: PlayerInterface <-------+
	V								|
FIRST_PASS: CategoryInterface		|
	V								|
FIRST_PASS: QuestionInterface ------+
	V
TODO: FinalScoreInterface

 */

public class GameInterface extends JFrame
{
	protected GameState state;

	protected CardLayout manager;
	protected JPanel wrapper;
	protected ConfigInterface configInterface;
	protected QuestionSetInterface questionSetInterface;
	protected PlayerInterface playerInterface;
	protected CategoryInterface categoryInterface;
	protected QuestionInterface questionInterface;	
	
	public GameInterface()
	{
		super("Who Wants To Be A Zillionaire!");
		state = new GameState();

		InitializeComponent();
		
		display(configInterface);
	}
	
	protected void InitializeComponent()
	{		
		wrapper = new JPanel(manager = new CardLayout());
		
		configInterface = new ConfigInterface();
		questionSetInterface = new QuestionSetInterface();
		playerInterface = new PlayerInterface();
		categoryInterface = new CategoryInterface();
		questionInterface = new QuestionInterface();
		
		configInterface.setSelectionHandler(new SelectionHandler<PlayerState[]>()
		{
			@Override
			public void OnSelectionMade(PlayerState[] selectedObject) 
			{
				state.setPlayers(selectedObject);
				display(questionSetInterface);
			}			
		});

		questionSetInterface.setSelectionHandler(new SelectionHandler<QuestionSet[]>()
		{
			@Override
			public void OnSelectionMade(QuestionSet[] selectedObject) 
			{
				state.setQuestions(selectedObject);
				playerInterface.setGame(state);
				display(playerInterface);
			}			
		});
		
		playerInterface.setSelectionHandler(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				categoryInterface.setCategories(state.getCategoryChoices(3));
				display(categoryInterface);
			}			
		});
		
		categoryInterface.setSelectionHandler(new SelectionHandler<Category>() 
		{
			@Override
			public void OnSelectionMade(Category selectedObject) 
			{
				questionInterface.setPlayer(state.getCurrentPlayer());
				questionInterface.setQuestion(state.getQuestion(selectedObject));
				display(questionInterface);
			}			
		});
		
		questionInterface.setSelectionHandler(new SelectionHandler<Integer>() 
		{
			@Override
			public void OnSelectionMade(Integer selectedObject) 
			{
				if (state.answerQuestion(selectedObject))
				{
					//Correct
					playerInterface.setGame(state);
					display(playerInterface);
				}
				else
				{
					//TODO: Incorrect
					playerInterface.setGame(state);
					display(playerInterface);
				}
			}			
		});

		wrapper.add(configInterface, configInterface.getClass().getName());
		wrapper.add(questionSetInterface, questionSetInterface.getClass().getName());
		wrapper.add(playerInterface, playerInterface.getClass().getName());
		wrapper.add(categoryInterface, categoryInterface.getClass().getName());
		wrapper.add(questionInterface, questionInterface.getClass().getName());
		this.add(wrapper);
	}
	
	protected void display(JPanel panel)
	{
		manager.show(wrapper, panel.getClass().getName());
	}
}
