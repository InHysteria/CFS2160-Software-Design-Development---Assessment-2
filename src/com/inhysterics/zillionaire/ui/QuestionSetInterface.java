package com.inhysterics.zillionaire.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.QuestionSet;
import com.inhysterics.zillionaire.QuestionSetRemote;
import com.inhysterics.zillionaire.QuestionSetService;
import com.inhysterics.zillionaire.SelectionHandler;

@SuppressWarnings("serial")
public class QuestionSetInterface extends JPanel
{
	protected JLabel localLabel;
	protected JButton localButton;
	protected DefaultListModel<QuestionSet> localQuestionSetListModel;
	protected JList<QuestionSet> localQuestionSetList;
	protected JScrollPane localQuestionSetScroller;
	
	protected JLabel remoteLabel;
	protected JButton remoteButton;
	protected DefaultListModel<QuestionSetRemote> remoteQuestionSetListModel;
	protected JList<QuestionSetRemote> remoteQuestionSetList;
	protected JScrollPane remoteQuestionSetScroller;
	protected JButton selectButton;
	
	protected SelectionHandler<QuestionSet[]> selectionHandler;
	
	public QuestionSetInterface()
	{
		InitializeComponent();
		PopulateLists();
	}
	
	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
	

		localLabel = new JLabel("Avaliable locally", JLabel.LEFT);	
		
		localQuestionSetListModel = new DefaultListModel<QuestionSet>();
		localQuestionSetList = new JList<QuestionSet>(localQuestionSetListModel);
		localQuestionSetList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		localQuestionSetList.setLayoutOrientation(JList.VERTICAL);
		localQuestionSetList.setFixedCellWidth(Short.MAX_VALUE); 

		localQuestionSetScroller = new JScrollPane(localQuestionSetList);
		localQuestionSetScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		localQuestionSetScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		localButton = new JButton("Play with selected question sets");	
		localButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{				
				QuestionSet[] sets = localQuestionSetList.getSelectedValuesList().toArray(new QuestionSet[0]);
				int question_count = 0;
				for (QuestionSet set : sets)
					question_count += set.getQuestions().length;
				
				if (question_count == 0)
				{
					JOptionPane.showMessageDialog(null, "You must select at least one question set.");
					return;
				}
						
				if (JOptionPane.showConfirmDialog(
						null, 
						"You have selected " + sets.length + " for a total of " + question_count + " questions.\r\n\r\nDo you want to play with these questions?", 
						"Play with selected question sets?", 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE)
						
						== JOptionPane.NO_OPTION)
					return;
				
				if (selectionHandler != null)	
					selectionHandler.OnSelectionMade(sets);
			}			
		});

		remoteLabel = new JLabel("Avaliable online", JLabel.LEFT);	
		
		remoteQuestionSetListModel = new DefaultListModel<QuestionSetRemote>();
		remoteQuestionSetListModel.addElement(new QuestionSetRemote());
		remoteQuestionSetList = new JList<QuestionSetRemote>(remoteQuestionSetListModel);
		remoteQuestionSetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		remoteQuestionSetList.setLayoutOrientation(JList.VERTICAL);
		remoteQuestionSetList.setFixedCellWidth(Short.MAX_VALUE); 

		remoteQuestionSetScroller = new JScrollPane(remoteQuestionSetList);
		remoteQuestionSetScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		remoteQuestionSetScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		remoteButton = new JButton("Download");
		remoteButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (remoteQuestionSetList.isSelectionEmpty())
					JOptionPane.showMessageDialog(null, "Please select a file to download.");
				else
				{
					remoteQuestionSetList.getSelectedValue().download();
					PopulateLists();
				}
			}			
		});
		
		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		
		//TODO: Consider making these settings less explicit by allowing them to flow down where the value doesn't change

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(remoteLabel, c);
		
		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(localLabel, c);
		yRow++;
		
		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 1;
		c.insets = new Insets(0,20,5,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(remoteQuestionSetScroller, c);

		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 1;
		c.insets = new Insets(0,20,5,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(localQuestionSetScroller, c);
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,20,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(remoteButton, c);
		
		c.gridx = 1;
		c.gridy = yRow;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.weighty = 0;
		c.insets = new Insets(5,20,20,20);
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		this.add(localButton, c);
	}
	
	public void PopulateLists()
	{
		QuestionSet[] localFiles = QuestionSetService.getLocalQuestionSets();
		QuestionSetRemote[] remoteFiles = QuestionSetService.getRemoteQuestionSets();
		
		localQuestionSetListModel.clear();
		for (QuestionSet set : localFiles)
			localQuestionSetListModel.addElement(set);
		
		remoteQuestionSetListModel.clear();
		for (QuestionSetRemote set : remoteFiles)
			remoteQuestionSetListModel.addElement(set);
			
	}

	public void setSelectionHandler(SelectionHandler<QuestionSet[]> selectionHandler)
	{
		this.selectionHandler = selectionHandler;
	}
}
