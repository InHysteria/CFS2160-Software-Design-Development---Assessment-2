package com.inhysterics.zillionaire.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.inhysterics.zillionaire.CONSTANTS;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.QuestionSet;

@SuppressWarnings("serial")
public class ConfigInterface extends JFrame {

	protected QuestionSet chosenQuestionSet;
	protected ArrayList<PlayerState> players;
	
	protected JLabel captionLabel;
	protected JPanel optionPanel;
	
	public ConfigInterface() {		
		this.setSize(450,300);
		this.setTitle("Who wants to be a Zillionaire!");
		
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		
		captionLabel = new JLabel("", SwingConstants.CENTER);
		optionPanel = new JPanel();
		optionPanel.setLayout(new GridBagLayout());
		optionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		content.add(captionLabel, BorderLayout.CENTER);
		content.add(optionPanel, BorderLayout.SOUTH);		
		
		showPage(0);
	}
	
	protected void showPage(int page) {
		optionPanel.removeAll();
		switch (page) {
			case 0: //Menu
				captionLabel.setText("$PROMOTIONAL_IMAGE_HERE");
				JButton instructionButton = new JButton("Please select a ruleset from below.");
				JComboBox<String> rulesetList = new JComboBox<String>();
				
				final File rulesetDirectory = new File("./questionsets/");
				for (final File file : rulesetDirectory.listFiles()) {
					if (file.getPath().endsWith(".questionset"))
					{
						rulesetList.addItem(file.getName());
					}
				}
				instructionButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						ArrayList<Exception> errors = new ArrayList<Exception>();
						chosenQuestionSet = QuestionSet.Builder.CreateQuestionSet("./questionsets/" + rulesetList.getSelectedItem().toString(), errors); //TODO: 1, make this less magical. 2, Stop relying on the text in the box matching filename.
						showPage(1);
						
						for (Exception ex : errors)
						{
							System.out.println(ex.getClass().getName() + ": " + ex.getMessage());
							for (StackTraceElement trace : ex.getStackTrace())
								System.out.println("    " + trace.getLineNumber() + ": " + trace.getClassName() + "." + trace.getMethodName());
						}
					}
					
				});
				
				
				optionPanel.add(instructionButton, CONSTANTS.BAG_CASCADE_CONSTRAINT);
				optionPanel.add(rulesetList, CONSTANTS.BAG_CASCADE_CONSTRAINT);				
				break;
			case 1: //Player Creation
				
				captionLabel.setText("Please add as many players as are required.");
				JTextField field = new JTextField();
				
				break;
		}
		
	}

}
