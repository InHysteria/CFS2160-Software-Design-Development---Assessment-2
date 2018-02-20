package com.inhysterics.zillionaire;

import java.awt.Dimension;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.inhysterics.zillionaire.ui.CategoryInterface;
import com.inhysterics.zillionaire.ui.ConfigInterface;
import com.inhysterics.zillionaire.ui.GameInterface;
import com.inhysterics.zillionaire.ui.PlayerInterface;
import com.inhysterics.zillionaire.ui.PlayerMessageInterface;
import com.inhysterics.zillionaire.ui.QuestionInterface;
import com.inhysterics.zillionaire.ui.QuestionSetInterface;

public class Zillionaire implements Runnable 
{
	
	//TODO: Go clean up/work out how best to actually do it, the XML parsing for question sets.
	//TODO: Take alllllllll these hardcoded strings and put em in a strings resource etc;
	public static void main(String args[])
	{
		if (args.length > 0 && args[0].toString().equals("/generatemanifest"))
		{
			System.out.println("Creating manifest for files in " + Paths.get(QuestionSetService.QUESTION_SET_LOCAL_PATH).toAbsolutePath().toString());
			QuestionSetService.generateLocalQuestionManifest();
			return;
		}
		
		Zillionaire zillionaire = new Zillionaire();
		SwingUtilities.invokeLater(zillionaire);
	}

	@Override
	public void run() 
	{
        GameInterface frame = new GameInterface();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.pack();
        frame.setVisible(true);
	}
	
}
