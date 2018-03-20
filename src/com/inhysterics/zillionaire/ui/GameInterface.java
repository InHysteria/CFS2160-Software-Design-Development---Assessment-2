package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.inhysterics.zillionaire.Category;
import com.inhysterics.zillionaire.GameService;
import com.inhysterics.zillionaire.GameState;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;
import com.inhysterics.zillionaire.QuestionSet;
import com.inhysterics.zillionaire.SelectionHandler;
import com.inhysterics.zillionaire.Zillionaire;

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
	protected CardLayout manager;
	protected JPanel wrapper;
	protected ConfigInterface configInterface;
	protected QuestionSetInterface questionSetInterface;
	protected PlayerInterface playerInterface;
	protected CategoryInterface categoryInterface;
	protected QuestionInterface questionInterface;	
	protected FinalScoreInterface finalScoreInterface;
	
	public GameInterface()
	{
		super("Who Wants To Be A Zillionaire!");
		
		InitializeComponent();
		Initialize();
	}
	
	protected void Initialize()
	{
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
		finalScoreInterface = new FinalScoreInterface();
		
		configInterface.setSelectionHandler(new SelectionHandler<PlayerState[]>()
		{
			@Override
			public void OnSelectionMade(PlayerState[] selectedObject) 
			{
				GameService.setPlayers(selectedObject);
				display(questionSetInterface);
			}			
		});

		questionSetInterface.setSelectionHandler(new SelectionHandler<QuestionSet[]>()
		{
			@Override
			public void OnSelectionMade(QuestionSet[] selectedObject) 
			{
				GameService.setQuestions(selectedObject);
				playerInterface.reset();
				display(playerInterface);
			}			
		});
		
		playerInterface.setSelectionHandler(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				categoryInterface.setPlayer(GameService.getCurrentPlayer());
				categoryInterface.setCategories(GameService.getCategoryChoices(3));
				display(categoryInterface);
			}			
		});
		
		categoryInterface.setSelectionHandler(new SelectionHandler<Category>() 
		{
			@Override
			public void OnSelectionMade(Category selectedObject) 
			{
				Question question = GameService.getNewQuestion(selectedObject);
				if (question == null)
					JOptionPane.showMessageDialog(null, "There were no questions for that category. Please select another.");
				else
				{
					questionInterface.setPlayer(GameService.getCurrentPlayer());
					questionInterface.setQuestion(question);
					display(questionInterface);
				}
			}			
		});
		
		questionInterface.setSelectionHandler(new SelectionHandler<Integer>() 
		{
			@Override
			public void OnSelectionMade(Integer selectedObject) 
			{
				Boolean isGameContinuing = false;
				if (selectedObject == -1)
				{
					//Player choose to finish here.
					JOptionPane.showMessageDialog(null, String.format("You decide to play it safe and leave with.. %s%s", 
							Zillionaire.CurrencySymbol, 
							GameService.getCurrentPlayer().getScore()));
					GameService.getCurrentPlayer().setHasFinished(true);
					isGameContinuing = GameService.rotatePlayers();
				}
				else
				{
					
					Boolean[] results = GameService.answerLastQuestion(selectedObject);
					if (results[0]) //Is question correct?
						JOptionPane.showMessageDialog(null, "Correct!");
					else
						JOptionPane.showMessageDialog(null, "Incorrect! The correct answer was '"+GameService.getLastQuestionAnswerString()+"'.");
					
					isGameContinuing = results[1];
				}
				if (isGameContinuing)
				{
					playerInterface.reset();
					display(playerInterface);
				}
				else
				{
					finalScoreInterface.reset();
					display(finalScoreInterface);
				}
			}			
		});
		
		finalScoreInterface.setSelectionHandler(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				/*Re*/Initialize();
				configInterface.clear();
			}			
		});
		
		wrapper.add(configInterface, configInterface.getClass().getName());
		wrapper.add(questionSetInterface, questionSetInterface.getClass().getName());
		wrapper.add(playerInterface, playerInterface.getClass().getName());
		wrapper.add(categoryInterface, categoryInterface.getClass().getName());
		wrapper.add(questionInterface, questionInterface.getClass().getName());
		wrapper.add(finalScoreInterface, finalScoreInterface.getClass().getName());
		this.add(wrapper);
	}
	
	protected void display(JPanel panel)
	{
		manager.show(wrapper, panel.getClass().getName());
	}
}
