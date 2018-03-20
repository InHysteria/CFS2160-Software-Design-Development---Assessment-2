package com.inhysterics.zillionaire.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.inhysterics.zillionaire.GameService;
import com.inhysterics.zillionaire.PlayerState;
import com.inhysterics.zillionaire.Zillionaire;

@SuppressWarnings("serial")
public class FinalScoreInterface extends JPanel
{
	protected JLabel captionLabel;
	protected JLabel thankYouLabel;
	protected DefaultListModel<String> playerListModel;
	protected JList<String> playerList;
	protected JScrollPane playerListScroller;
	protected JButton selectButton;
	
	protected ActionListener selectionHandler;

	public FinalScoreInterface()
	{
		InitializeComponent();
	}

	protected void InitializeComponent()
	{
		this.setLayout(new GridBagLayout());
		
		captionLabel = new JLabel("Final scores are..", JLabel.CENTER);	
		thankYouLabel = new JLabel("Thank you for playing, who wants to be a Zillionaire!", JLabel.CENTER);

		playerListModel = new DefaultListModel<String>();
		playerList = new JList<String>(playerListModel);
		playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		playerList.setLayoutOrientation(JList.VERTICAL);		
		playerList.setSelectionModel(new DefaultListSelectionModel()
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        super.setSelectionInterval(-1, -1);
		    }
		});
		playerList.setCellRenderer(new DefaultListCellRenderer() {

			@SuppressWarnings("rawtypes")
			@Override
		    public Component getListCellRendererComponent(
		            JList list,
		            Object value,
		            int index,
		            boolean isSelected,
		            boolean cellHasFocus)
		    {
		        JLabel label = (JLabel)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
	            Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	            label.setHorizontalAlignment(CENTER);
	            label.setFont(font);
	            return label;				
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
	
	public void reset()
	{		
		playerListModel.clear();
		PlayerState[] players = GameService.getPlayers();
		HashSet<Integer> checkpoints = new HashSet<Integer>();
		for (int checkpoint : GameService.getCheckpoints())
			checkpoints.add(checkpoint);
		

		String[] playerNames = new String[players.length];
		for (int i = 0; i < players.length; i++)
			playerNames[i] = players[i].getName();
		
		int max = 16;
		int padding_left = (Integer.toString(GameService.getScoreForQuestion(max-1))).length();
		int padding_right = String.join(", ", playerNames).length();
		
		
		
		for (int i = max-1; i >= 0; i--)
		{
			ArrayList<String> playersAtI = new ArrayList<String>();
			for (PlayerState player : players)
				if (player.getQuestionNo() == i)
					playersAtI.add(player.getName());
			
			playerListModel.addElement(String.format(
				"%s%s%s: %4$-"+padding_right+"s",
				new String(new char[padding_left-(Integer.toString(GameService.getScoreForQuestion(i))).length()]).replace('\0', ' '),
				Zillionaire.CurrencySymbol,
				GameService.getScoreForQuestion(i),
				String.join(", ", playersAtI)
			));
			if (checkpoints.contains(i))
				playerListModel.addElement(" ");
		}		
	}
	
	public void setSelectionHandler(ActionListener selectionHandler)
	{
		selectButton.removeActionListener(selectionHandler);
		
		this.selectionHandler = selectionHandler;
		
		selectButton.addActionListener(selectionHandler);
	}
}
