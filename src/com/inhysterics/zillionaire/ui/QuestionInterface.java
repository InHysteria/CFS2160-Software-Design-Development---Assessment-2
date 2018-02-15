package com.inhysterics.zillionaire.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.inhysterics.zillionaire.Category;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;
import com.inhysterics.zillionaire.SelectionHandler;

public class QuestionInterface extends JFrame 
{

	protected JLabel playerNameLabel;
	protected JLabel playerScoreLabel;
	protected JLabel questionLabel;
	//Progressbar?
	protected JButton answerAButton;
	protected JButton answerBButton;
	protected JButton answerCButton;
	protected JButton answerDButton;

	protected JButton fiftyFiftyButton;
	protected JButton askTheAudianceButton;
	
	protected JButton leaveButton;
	
	protected Question question;
	protected PlayerState player;

	protected SelectionHandler<Integer> selectionHandler;
	
	public QuestionInterface()
	{
		InitializeComponent();
		
		this.setSize(720, 480);
	}
		
	protected void InitializeComponent()
	{
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		
		ActionListener answerListener = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (selectionHandler != null)
					selectionHandler.OnSelectionMade(Integer.parseInt(e.getActionCommand()));
			}			
		};

		playerNameLabel = new JLabel("$playerNameLabel", JLabel.CENTER);
		playerScoreLabel = new JLabel("$playerScoreLabel", JLabel.CENTER);
		questionLabel = new JLabel("$questionLabel", JLabel.CENTER);
		
		answerAButton = new JButton("$answerAButton");
		answerAButton.setActionCommand("0");
		answerAButton.addActionListener(answerListener);	
		
		answerBButton = new JButton("$answerBButton");
		answerBButton.setActionCommand("1");
		answerBButton.addActionListener(answerListener);
		
		answerCButton = new JButton("$answerCButton");
		answerCButton.setActionCommand("2");
		answerCButton.addActionListener(answerListener);
		
		answerDButton = new JButton("$answerDButton");
		answerDButton.setActionCommand("3");
		answerDButton.addActionListener(answerListener);

		fiftyFiftyButton = new JButton("Use 50/50");
		fiftyFiftyButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (question != null && player != null && player.getHasFiftyFifty())
				{
					int hidden = 0;
					while (hidden < 2)
					{
						int considering = ThreadLocalRandom.current().nextInt(0, 4);
						System.out.println("Hidden: " + hidden + ", Considering: " + considering);
						if (considering == question.getCorrectAnswer())
							continue;
						
						switch (considering)
						{
							case 0:
								if (!answerAButton.isEnabled())
									continue;
								answerAButton.setEnabled(false);
								hidden++;
								break;
							case 1:
								if (!answerBButton.isEnabled())
									continue;
								answerBButton.setEnabled(false);
								hidden++;
								break;
							case 2:
								if (!answerCButton.isEnabled())
									continue;
								answerCButton.setEnabled(false);
								hidden++;
								break;
							case 3:
								if (!answerDButton.isEnabled())
									continue;
								answerDButton.setEnabled(false);
								hidden++;
								break;
						}
					}
					player.setHasFiftyFifty(false);
					fiftyFiftyButton.setEnabled(false);
				}
			}			
		});
		
		askTheAudianceButton = new JButton("Ask the audiance"); 
		leaveButton = new JButton("Take $playerScoreLabel and leave..");

		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//TODO: Consider making these settings less explicit by allowing them to flow down where the value doesn't change

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(playerNameLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,20,20,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(playerScoreLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,5,40,5);
		c.anchor = GridBagConstraints.CENTER;
		container.add(questionLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,5,5);
		c.anchor = GridBagConstraints.CENTER;
		container.add(answerAButton, c);

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(answerBButton, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,40,5);
		c.anchor = GridBagConstraints.CENTER;
		container.add(answerCButton, c);

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,40,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(answerDButton, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,5,5);
		c.anchor = GridBagConstraints.CENTER;
		container.add(fiftyFiftyButton, c);		

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(askTheAudianceButton, c);	
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,20,20,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(leaveButton, c);		
	}

	public void setQuestion(Question question)
	{
		this.question = question;
		
		questionLabel.setText(question.getMessage());
		
		String[] answers = question.getAnswers();
		
		answerAButton.setText(answers[0]);
		answerBButton.setText(answers[1]);
		answerCButton.setText(answers[2]);
		answerDButton.setText(answers[3]);
	}
	
	public void setPlayer(PlayerState player)
	{
		this.player = player;

		playerNameLabel.setText(player.getName());
		playerScoreLabel.setText("£" + player.getScore());

		fiftyFiftyButton.setEnabled(player.getHasFiftyFifty());
		askTheAudianceButton.setEnabled(player.getHasAskAudiance());
		leaveButton.setText("Take £" + player.getScore() + " and leave..");
	}

	public void setSelectionHandler(SelectionHandler<Integer> selectionHandler)
	{
		this.selectionHandler = selectionHandler;
	}
}
