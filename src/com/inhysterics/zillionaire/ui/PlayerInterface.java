package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Question;

@SuppressWarnings("serial")
public class PlayerInterface extends JFrame {
	
	private PlayerState playerState;
	protected JLabel playerDisplay;
	protected QuestionInterface questionDisplay;
	
	public PlayerInterface() {
		
		Container pane = this.getContentPane();
		
		pane.setLayout(new BorderLayout(3,4));
		
		playerDisplay = new JLabel("", SwingConstants.CENTER);
		
		questionDisplay = new QuestionInterface();
		questionDisplay.setQuestion(new Question("This is an example question.",  new String[] { "DEBUG: 0","DEBUG: 1","DEBUG: 2","DEBUG: 3" }, 0, 0));
		
		pane.add(playerDisplay, BorderLayout.NORTH);
		pane.add(questionDisplay, BorderLayout.CENTER);
		
		this.setSize(450,300);
		this.setTitle("Who wants to be a Zillionaire!");
	}

	public PlayerState getPlayer() {
		return playerState;
	}

	public void setPlayer(PlayerState playerState) {
		this.playerState = playerState;
		
		playerDisplay.setText(playerState.getName() + ": Question " + playerState.getQuestionNo());
	}	
}
