package com.inhysterics.zillionaire.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.SelectionHandler;
import com.inhysterics.zillionaire.Zillionaire;

/**
 * An interface used to provide a list of players and also welcome the players to the game.
 */
@SuppressWarnings("serial")
public class ConfigInterface extends JPanel
{	
	protected ImageIcon logoImageIcon;	
	protected JLabel logoLabel;
	protected JLabel welcomeLabel;
	protected JLabel instructionalLabel;
	protected DefaultListModel<PlayerState> playerListModel;
	protected JList<PlayerState> playerList;
	protected JScrollPane playerPane;
	protected JTextField nameInput;
	protected JButton nameAddButton;
	protected JButton continueButton;
	
	protected SelectionHandler<PlayerState[]> selectionHandler;
	
	public ConfigInterface()
	{
		InitializeComponent();		
	}
	
	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());

		logoImageIcon = new ImageIcon(Zillionaire.IMG_LOGO_LARGE);
		logoLabel = new JLabel("",JLabel.CENTER);
		logoLabel.setIcon(logoImageIcon);
		
		welcomeLabel = new JLabel("Welcome to, Who Wants To Be A Zillionaire!", JLabel.CENTER);
		instructionalLabel = new JLabel("To being please add at least one player using the box below.", JLabel.CENTER);
		playerListModel = new DefaultListModel<PlayerState>();
		playerList = new JList<PlayerState>(playerListModel);

		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);
		playerList.setSelectionModel(new DefaultListSelectionModel()
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        super.setSelectionInterval(-1, -1);
		    }
		});

		playerPane = new JScrollPane(playerList);
		playerPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		playerPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		ActionListener addPlayerAction = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				if (nameInput.getText().equals(""))
					return;
				
				PlayerState newPlayer = new PlayerState();
				newPlayer.setName(nameInput.getText());
				nameInput.setText("");
				playerListModel.addElement(newPlayer);
			}
		};		
		
		nameInput = new JTextField();
		nameInput.addActionListener(addPlayerAction);
		nameAddButton = new JButton("+");
		nameAddButton.setFont(new Font(nameAddButton.getFont().getFontName(),Font.BOLD,8));
		nameAddButton.addActionListener(addPlayerAction);
		continueButton = new JButton("Play with these players");
		continueButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				int playerSize = playerListModel.getSize(); 
				if (playerSize <= 0)
				{
					JOptionPane.showMessageDialog(null, "You must add at least one player.");
					return;
				}
				
				
				PlayerState[] players = new PlayerState[playerSize];
				playerListModel.copyInto(players);
				
				if (selectionHandler != null)
					selectionHandler.OnSelectionMade(players);
			}			
		});
		
		int yRow = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = yRow;
	    c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(20,20,5,20);
		c.anchor = GridBagConstraints.NORTH;
		this.add(logoLabel, c);		
		yRow++;

		c.gridy = yRow;
		c.insets = new Insets(5,20,5,20);
		this.add(welcomeLabel, c);		
		yRow++;

		c.gridy = yRow;
		this.add(instructionalLabel, c);		
		yRow++;

		c.gridy = yRow;
	    c.gridwidth = 1;
		c.insets = new Insets(20,100,5,2);
		this.add(nameInput, c);	

		c.gridx = 1;
	    c.weightx = 0;
		c.insets = new Insets(20,0,5,100);
		this.add(nameAddButton, c);		
		yRow++;

		c.gridx = 0;
		c.gridy = yRow;
	    c.gridwidth = 2;
		c.weighty = 0;
	    c.weightx = 1;
		c.insets = new Insets(0,100,0,100);
		this.add(playerPane, c);		
		yRow++;
		
		c.gridy = yRow;
		c.weighty = 0;
		c.insets = new Insets(5,100,20,100);
		this.add(continueButton, c);		
		yRow++;
	}

	public void setSelectionHandler(SelectionHandler<PlayerState[]> selectionHandler) 
	{
		this.selectionHandler = selectionHandler;
	}	
	
	public void clear()
	{
		playerListModel.clear();
	}
}


