package com.inhysterics.zillionaire.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.Category;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.SelectionHandler;

public class CategoryInterface extends JPanel {

	protected JLabel playerNameLabel;
	protected JLabel playerScoreLabel;
	protected JLabel instructionLabel;
	protected DefaultListModel<Category> categoryListModel;
	protected JList<Category> categoryList;
	protected JScrollPane categoryListScroller;
	protected JButton selectButton;
	
	protected PlayerState player;
	protected Category[] categories;
	
	protected SelectionHandler<Category> selectionHandler;
	
	public CategoryInterface()
	{
		InitializeComponent();	
	}
	
	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
		
		playerNameLabel = new JLabel("$playerNameLabel", JLabel.CENTER);
		playerScoreLabel = new JLabel("$playerScoreLabel", JLabel.CENTER);
		instructionLabel = new JLabel("Please select a category for your question..", JLabel.LEFT);		

		categoryListModel = new DefaultListModel<Category>();
		categoryList = new JList<Category>(categoryListModel);
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		categoryList.setLayoutOrientation(JList.VERTICAL);
		categoryList.setFixedCellWidth(Short.MAX_VALUE); 

		categoryListScroller = new JScrollPane(categoryList);
		categoryListScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		categoryListScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (selectionHandler != null)
				{
					Category selectedCategory = categoryList.getSelectedValue();
					if (selectedCategory != null)
						selectionHandler.OnSelectionMade(selectedCategory);
					else
						JOptionPane.showMessageDialog(null, "Please select a category.");
				}
			}			
		});
		
		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		//TODO: Consider making these settings less explicit by allowing them to flow down where the value doesn't change
		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(playerNameLabel, c);
		yRow++;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,20,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(playerScoreLabel, c);
		yRow++;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(instructionLabel, c);		
		yRow++;
		
		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,20,0,20);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(categoryListScroller, c);
		yRow++;		

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,20,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(selectButton, c);		
		yRow++;
	}
	
	public void setCategories(Category ... categories)
	{
		this.categories = categories;
		
		categoryListModel.clear();
		for (Category category : categories)
			categoryListModel.addElement(category);
	}

	public void setPlayer(PlayerState player)
	{
		this.player = player;

		playerNameLabel.setText(player.getName());
		playerScoreLabel.setText("£" + player.getScore());
	}

	public void setSelectionHandler(SelectionHandler<Category> selectionHandler)
	{
		this.selectionHandler = selectionHandler;
	}
}

