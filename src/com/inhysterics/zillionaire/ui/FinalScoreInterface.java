package com.inhysterics.zillionaire.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.GameState;
import com.inhysterics.zillionaire.PlayerState;

public class FinalScoreInterface extends JPanel
{
	protected JLabel captionLabel;
	protected JLabel thankYouLabel;
	protected DefaultListModel<PlayerState> playerListModel;
	protected JList<PlayerState> playerList;
	protected JScrollPane playerListScroller;
	protected JButton selectButton;
	
	protected GameState game;
	
	protected ActionListener selectionHandler;

	public FinalScoreInterface()
	{
		InitializeComponent();
	}

	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
		
		captionLabel = new JLabel("Final scores are..", JLabel.CENTER);	
		thankYouLabel = new JLabel("Thank you for playing, who wants to be a Zillionaire!.", JLabel.CENTER);

		playerListModel = new DefaultListModel<PlayerState>();
		playerList = new JList<PlayerState>(playerListModel);
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);

		DefaultListCellRenderer renderer = (DefaultListCellRenderer)playerList.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		playerList.setSelectionModel(new DefaultListSelectionModel()
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        super.setSelectionInterval(-1, -1);
		    }
		});
		
		playerListScroller = new JScrollPane(playerList);
		playerListScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		playerListScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		selectButton = new JButton("Return to main menu");
		
		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(captionLabel, c);		
		yRow++;
		
		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,20,0,20);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(playerListScroller, c);
		yRow++;		

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,0,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(thankYouLabel, c);		
		yRow++;

		c.gridy = yRow;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,20,40,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(selectButton, c);		
		yRow++;
	}
	
	public void setGame(GameState game)
	{
		this.game = game;
		
		playerListModel.clear();		
		
		PlayerState[] players = game.getPlayers();
		Arrays.sort(players, Collections.reverseOrder());
		for (PlayerState player : players)
			playerListModel.addElement(player);
		
	}
	
	public void setSelectionHandler(ActionListener selectionHandler)
	{
		selectButton.removeActionListener(selectionHandler);
		
		this.selectionHandler = selectionHandler;
		
		selectButton.addActionListener(selectionHandler);
	}
}
