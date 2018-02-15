package com.inhysterics.zillionaire.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.GameState;
import com.inhysterics.zillionaire.PlayerState;

public class PlayerInterface extends JFrame {

	protected JLabel captionLabel;
	protected JLabel instructionLabel;
	protected DefaultListModel<String> categoryListModel;
	protected JList<String> categoryList;
	protected JScrollPane categoryListScroller;
	protected JButton selectButton;
	
	protected GameState game;
	
	protected ActionListener continueListener;
	
	public PlayerInterface()
	{
		InitializeComponent();
		
		this.setSize(720, 480);		
	}
	
	protected void InitializeComponent()
	{
		Container container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		
		captionLabel = new JLabel("Current scores are..", JLabel.CENTER);	
		instructionLabel = new JLabel("The next player in the rotation is %s", JLabel.CENTER);

		categoryListModel = new DefaultListModel<String>();
		categoryList = new JList<String>(categoryListModel);
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		categoryList.setLayoutOrientation(JList.VERTICAL);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer)categoryList.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		categoryList.setSelectionModel(new DefaultListSelectionModel()
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        super.setSelectionInterval(-1, -1);
		    }
		});
		
		categoryListScroller = new JScrollPane(categoryList);
		categoryListScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		categoryListScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		selectButton = new JButton("%s is ready");
		
		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(captionLabel, c);		
		yRow++;
		
		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,20,0,20);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		container.add(categoryListScroller, c);
		yRow++;		

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(instructionLabel, c);		
		yRow++;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,40,20);
		c.anchor = GridBagConstraints.NORTH;
		container.add(selectButton, c);		
		yRow++;
	}
	
	public void setGame(GameState game)
	{
		this.game = game;
		
		PlayerState nextPlayer = game.getCurrentPlayer();
		instructionLabel.setText(String.format("The next player in the rotation is %s", nextPlayer.getName()));
		selectButton.setText(String.format("%s is ready", nextPlayer.getName()));
		
		PlayerState[] players = game.getPlayers();
		Arrays.sort(players, Collections.reverseOrder());
		for (PlayerState player : players)
			categoryListModel.addElement(player.getName() + " - £" + player.getScore());
		
	}
	
	public void setContinueListener(ActionListener continueListener)
	{
		selectButton.removeActionListener(continueListener);
		
		this.continueListener = continueListener;
		
		selectButton.addActionListener(continueListener);
	}
}

