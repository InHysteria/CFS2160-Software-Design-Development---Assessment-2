package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.inhysterics.zillionaire.CONSTANTS;
import com.inhysterics.zillionaire.Question;

@SuppressWarnings("serial")
public class QuestionInterface extends JPanel {

	protected JLabel contentLabel;
	protected JPanel answerPanel;
	protected JButton[] answerButtons;
	
	private Question currentQuestion;
	
	public QuestionInterface() {

		
		this.setLayout(new BorderLayout(3,4));
		
		contentLabel = new JLabel("", SwingConstants.CENTER);
		answerPanel = new JPanel();
		answerPanel.setLayout(new GridBagLayout());
		answerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));		
		answerButtons = new JButton[4];
		
		for (int i=0; i < answerButtons.length; i++)
			answerPanel.add(answerButtons[i] = new JButton(), CONSTANTS.BAG_CASCADE_CONSTRAINT);

		this.add(contentLabel, BorderLayout.CENTER);
		this.add(answerPanel, BorderLayout.SOUTH);
		
	}

	
	public Question getQuestion() {
		return currentQuestion;
	}

	public void setQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
		
		contentLabel.setText(currentQuestion.getMessage());

		for (int i = 0; i < 4; i++)
			answerButtons[i].setText(currentQuestion.getAnswers()[i]);
	}

}
