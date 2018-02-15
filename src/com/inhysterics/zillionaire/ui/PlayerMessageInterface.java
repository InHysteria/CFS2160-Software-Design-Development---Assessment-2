package com.inhysterics.zillionaire.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;

public class PlayerMessageInterface extends JFrame 
{
	
	protected JLabel playerNameLabel;
	protected JLabel playerScoreLabel;
	protected JLabel messageLabel;
	protected JButton okButton;

	protected String message;
	protected PlayerState player;
	
	public PlayerMessageInterface()
	{
		InitializeComponent();
		
		this.setSize(720, 480);		
	}
	
	protected void InitializeComponent()
	{
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());

		playerNameLabel = new JLabel("$playerNameLabel", JLabel.CENTER);
		playerScoreLabel = new JLabel("$playerScoreLabel", JLabel.CENTER);
		messageLabel = new JLabel("$playerScoreLabel", JLabel.CENTER);
		okButton = new JButton("OK");
		
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
		c.weighty = 0;
		c.insets = new Insets(5,20,5,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(playerScoreLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(5,20,5,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(messageLabel, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5,20,20,20);
		c.anchor = GridBagConstraints.CENTER;
		container.add(okButton, c);
		yRow++;
	}
	
	public void setPlayer(PlayerState player)
	{
		this.player = player;

		playerNameLabel.setText(player.getName());
		playerScoreLabel.setText("£" + player.getScore());
	}
	
	public void setMessage(String message)
	{
		this.message = message;
		
		messageLabel.setText(message);
	}
}
