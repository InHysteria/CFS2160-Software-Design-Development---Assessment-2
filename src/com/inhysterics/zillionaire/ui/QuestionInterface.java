package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.inhysterics.zillionaire.GameService;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;
import com.inhysterics.zillionaire.SelectionHandler;
import com.inhysterics.zillionaire.Zillionaire;


@SuppressWarnings("serial")
public class QuestionInterface extends JPanel 
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
	protected ATAInterface askTheAudiance;
	protected JPanel askTheAudianceContainer;
	
	protected JButton leaveButton;
	
	protected ArrayList<Integer> answerRandomisationMapping = new ArrayList<Integer>();
		
	protected Question question;
	protected PlayerState player;

	protected SelectionHandler<Integer> selectionHandler;
	
	
	public QuestionInterface()
	{
		for (int i = 0; i < 4; i++)
			answerRandomisationMapping.add(i);
		
		InitializeComponent();
	}
		
	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
		
		ActionListener answerListener = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (selectionHandler != null)
				{
					int answer = Integer.parseInt(e.getActionCommand());					
					if (answer < 0)
						selectionHandler.OnSelectionMade(answer);
					else
						selectionHandler.OnSelectionMade(answerRandomisationMapping.get(answer));
				}
			}			
		};
		

		playerNameLabel = new JLabel("$playerNameLabel", JLabel.CENTER);
		playerScoreLabel = new JLabel("$playerScoreLabel", JLabel.CENTER);
		questionLabel = new JLabel("$questionLabel", JLabel.CENTER);		
		
		answerAButton = new JButton("A");
		answerAButton.setActionCommand("0");
		answerAButton.addActionListener(answerListener);	
		
		answerBButton = new JButton("B");
		answerBButton.setActionCommand("1");
		answerBButton.addActionListener(answerListener);
		
		answerCButton = new JButton("C");
		answerCButton.setActionCommand("2");
		answerCButton.addActionListener(answerListener);
		
		answerDButton = new JButton("D");
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
						if (answerRandomisationMapping.get(considering) == question.getCorrectAnswer())
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
		askTheAudianceButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (question != null && player != null && player.getHasAskAudiance())
				{
					askTheAudiance.setVisible(true);
					player.setHasAskAudiance(false);
					askTheAudianceButton.setEnabled(false);
				}
			}			
		});
		askTheAudiance = new ATAInterface();	
		askTheAudiance.setVisible(false);
		askTheAudianceContainer = new JPanel();
		askTheAudianceContainer.setLayout(new BorderLayout());
		askTheAudianceContainer.add(askTheAudiance, BorderLayout.EAST);
		
		ImageIcon imageIcon = new ImageIcon(Zillionaire.IMG_QUESTION_LARGE);
		JLabel imageLabel = new JLabel("",JLabel.CENTER);
		imageLabel.setIcon(imageIcon);
		askTheAudianceContainer.add(imageLabel, BorderLayout.CENTER);
		
		
		leaveButton = new JButton("Take $playerScoreLabel and leave..");
		leaveButton.setActionCommand("-1");
		leaveButton.addActionListener(answerListener);

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
		this.add(playerNameLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,5,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(playerScoreLabel, c);
		yRow++;
		
		c.gridx = 0; 
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1; 
		c.weighty = 1;
		c.insets = new Insets(5,5,5,25); 
		c.anchor = GridBagConstraints.NORTH; 
		this.add(askTheAudianceContainer, c);
		yRow++;

		c.gridx = 0; 
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1; 
		c.weighty = 0;
		c.insets = new Insets(5,5,5,5); 
		c.anchor = GridBagConstraints.CENTER; 
		this.add(questionLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,5,5);
		c.anchor = GridBagConstraints.CENTER;
		this.add(answerAButton, c);

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,20);
		c.anchor = GridBagConstraints.CENTER;
		this.add(answerBButton, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,40,5);
		c.anchor = GridBagConstraints.CENTER;
		this.add(answerCButton, c);

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,40,20);
		c.anchor = GridBagConstraints.CENTER;
		this.add(answerDButton, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,5,5);
		c.anchor = GridBagConstraints.CENTER;
		this.add(fiftyFiftyButton, c);		

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,20);
		c.anchor = GridBagConstraints.CENTER;
		this.add(askTheAudianceButton, c);	
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,20,20,20);
		c.anchor = GridBagConstraints.CENTER;
		this.add(leaveButton, c);		
	}

	public void setQuestion(Question question)
	{		
		this.question = question;
		

		Collections.shuffle(answerRandomisationMapping);
		String[] answers = question.getAnswers();
		_resetAskTheAudiance();			
		
		questionLabel.setText(question.getMessage());

		answerAButton.setText("A: " + answers[answerRandomisationMapping.get(0)]);
		answerAButton.setEnabled(true);
		answerBButton.setText("B: " + answers[answerRandomisationMapping.get(1)]);
		answerBButton.setEnabled(true);
		answerCButton.setText("C: " + answers[answerRandomisationMapping.get(2)]);
		answerCButton.setEnabled(true);
		answerDButton.setText("D: " + answers[answerRandomisationMapping.get(3)]);
		answerDButton.setEnabled(true);
		
		askTheAudiance.setVisible(false);
	}
	
	public void setPlayer(PlayerState player)
	{
		this.player = player;

		playerNameLabel.setText(player.getName());
		playerScoreLabel.setText(Zillionaire.CurrencySymbol + player.getScore());

		fiftyFiftyButton.setEnabled(player.getHasFiftyFifty());
		askTheAudianceButton.setEnabled(player.getHasAskAudiance());
		leaveButton.setText("Take " + Zillionaire.CurrencySymbol + player.getScore() + " and leave..");
	}

	public void setSelectionHandler(SelectionHandler<Integer> selectionHandler)
	{
		this.selectionHandler = selectionHandler;
	}
	
	private void _resetAskTheAudiance()
	{
		askTheAudiance.setPrecents(GameService.getQuestionAskTheAudiance(answerRandomisationMapping));
	}
}
