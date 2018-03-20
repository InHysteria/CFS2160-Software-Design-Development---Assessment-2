package com.inhysterics.zillionaire;

import java.awt.Dimension;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.inhysterics.zillionaire.ui.GameInterface;

/**
 * Main class for the "Who wants to be a Zillionaire!" game.
 * Holds a few static links to resources but is mainly used to instantiate the GameInterface.
 * 
 * If provided with "/generatemanifest" as it's first argument, instead it will create a manifest
 * of all local question sets, suitable for use on the remote server where the download able
 * question sets reside.
 *  
 * @see GameInterface 
 */
public class Zillionaire implements Runnable 
{
	/**
	 * A URL pointing at the embedded "Who wants to be a Zillionaire!" logo.
	 */
	public static final URL IMG_LOGO_LARGE = Zillionaire.class.getResource("/res/logo_large.png");

	/**
	 * A URL pointing at a smaller version of the embedded "Who wants to be a Zillionaire!" logo.
	 */
	public static final URL IMG_LOGO_SMALL = Zillionaire.class.getResource("/res/logo_small.png");

	/**
	 * A URL pointing at a svg version of the embedded "Who wants to be a Zillionaire!" logo.
	 */
	public static final URL IMG_LOGO_SVG = Zillionaire.class.getResource("/res/logo.svg");

	/**
	 * A URL pointing at the embedded background image for the questions interface.
	 */
	public static final URL IMG_QUESTION_LARGE = Zillionaire.class.getResource("/res/question_large.jpg");
	
	/**
	 * Stores which currency symbol is used for points.
	 */
	public static String CurrencySymbol = Currency.getInstance(Locale.UK).getSymbol();
	

	/**
	 * The entry point of the program.
	 * 
	 * @param args Arguments passed to the program.
	 */
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

	/**
	 * Loads the main GameInterface and displays it. 
	 * 
	 * @see GameInterface
	 */
	@Override
	public void run() 
	{
        GameInterface frame = new GameInterface();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}
	
}
