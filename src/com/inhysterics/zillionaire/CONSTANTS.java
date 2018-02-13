package com.inhysterics.zillionaire;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class CONSTANTS {

	public static final GridBagConstraints BAG_CASCADE_CONSTRAINT;
	
	static 
	{
		BAG_CASCADE_CONSTRAINT = new GridBagConstraints();
		BAG_CASCADE_CONSTRAINT.fill = GridBagConstraints.HORIZONTAL;
		BAG_CASCADE_CONSTRAINT.weightx = 1;
		BAG_CASCADE_CONSTRAINT.gridx = 0;
		BAG_CASCADE_CONSTRAINT.insets = new Insets(2,0,0,0);
	}
}
