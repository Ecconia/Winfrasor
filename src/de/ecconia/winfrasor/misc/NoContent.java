package de.ecconia.winfrasor.misc;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.ecconia.winfrasor.components.Colors;

@SuppressWarnings("serial")
public class NoContent extends JPanel
{
	public NoContent(String content)
	{
		setBackground(Colors.contentBG);
		setBorder(new DebugBorder(Color.yellow));
		
		setMinimumSize(new Dimension(50, 50));
		
		add(new JLabel(content));
	}
}
