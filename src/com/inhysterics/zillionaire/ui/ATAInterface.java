package com.inhysterics.zillionaire.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ATAInterface extends JPanel
{		
	public float[] percents = new float[] { 0.25f, 0.5f, 0.15f, 0.10f };
	public int[] displays = new int[] { 25,25,25,25 };
	
	public ATAInterface()
	{
		Dimension sizes = new Dimension(220, 280);
		setMinimumSize(sizes);
		setMaximumSize(sizes);
		setPreferredSize(sizes);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		paintComponent((Graphics2D) g);
	}
	
	public void paintComponent(Graphics2D g)
	{		
		float width = getWidth();
		float height = getHeight();
		
		Stroke thickStroke = new BasicStroke(3.0f);
		Stroke thinStroke = new BasicStroke(1.0f);

		Font regularFont = g.getFont();
		Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		Font slightlyLessBoldFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
		
		GradientPaint backgroundPaint = new GradientPaint(
				width/4, 0, Color.getHSBColor(140f/255f,54f/255f,215f/255f),
				width/2, height/2, Color.WHITE,
				true
		);
		
		g.setBackground(Color.BLACK);
		
		
		//Debug
		g.translate(5, 5);		
		g.scale((width-10)/width, (height-10)/height);
		
		//Background
		g.setPaint(backgroundPaint);
		g.fill(new RoundRectangle2D.Float(0, 0, width, height, 50, 50));
		g.setColor(Color.BLACK);
		g.setStroke(thinStroke);


		//Captions at the bottom
		g.setColor(Color.DARK_GRAY);	
		g.setFont(boldFont);
		for (int i = 0; i < 4; i++)
			g.drawString(
				Character.toString((char)(65+i)), 
				2 + ((width-20)/4) * (i+0.5f), 
				height - 10);
		
		//Captions at the top
		g.setFont(slightlyLessBoldFont);
		for (int i = 0; i < 4; i++)
			g.drawString(
				displays[i] + "%", 
				(((width-20)/4) * (i+0.5f)) - 8, 
				30);
		
		g.setFont(regularFont);
		g.setColor(Color.BLACK);	
		
		//Grid lines
		g.setColor(Color.getHSBColor(0, 0, 0.6f));
		for (int i = 0; i < 11; i++)
			g.draw(new Line2D.Float(
				0, 40 + ((height-80)/10)*i,
				width, 40 + ((height-80)/10)*i)
			);
		
		//Bars
		g.setColor(Color.getHSBColor(139f/255f,72f/255f,159f/255f));
		for (int i = 0; i < 4; i++)
		{
			float size = (height-80)*percents[i];
			g.fill(new Rectangle2D.Float(
				(((width-20)/4) * (i+0.5f)), height - (40 + size), 
				20, size)
			);
		}	
	
		//Border
		g.setStroke(thickStroke);
		g.draw(new RoundRectangle2D.Float(0, 0, width, height, 50, 50));	
		g.setColor(Color.BLACK);			
	}
	
	public void setPrecents(float[] percents)
	{
		this.percents = percents;

		displays[0] = 100; //Ensures that the values always add to 100 despite dropping precision.
		for (int i = 1; i < percents.length; i++)
		{
			displays[i] = (int)(percents[i]*100);
			displays[0] -= displays[i];
		}
		
		
		repaint();
	}
}
