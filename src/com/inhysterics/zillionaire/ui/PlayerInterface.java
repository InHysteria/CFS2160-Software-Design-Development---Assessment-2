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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.GameState;
import com.inhysterics.zillionaire.PlayerState;

public class PlayerInterface extends JPanel {

	protected JLabel captionLabel;
	protected JLabel instructionLabel;
	protected DefaultListModel<PlayerState> playerListModel;
	protected JList<PlayerState> playerList;
	protected JScrollPane playerListScroller;
	protected JButton selectButton;
	
	protected GameState game;
	
	protected ActionListener selectionHandler;
	
	public PlayerInterface()
	{
		InitializeComponent();	
	}
	public PlayerInterface(GameState game)
	{
		this();
		setGame(game);
	}
	
	@SuppressWarnings("serial")
	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
		
		captionLabel = new JLabel("Current scores are..", JLabel.CENTER);	
		instructionLabel = new JLabel("The next player in the rotation is %s", JLabel.CENTER);

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
		
		selectButton = new JButton("%s is ready");
		
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
		this.add(instructionLabel, c);		
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
		
		PlayerState nextPlayer = game.getCurrentPlayer();
		instructionLabel.setText(String.format("The next player in the rotation is %s", nextPlayer.getName()));
		selectButton.setText(String.format("%s is ready", nextPlayer.getName()));
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

