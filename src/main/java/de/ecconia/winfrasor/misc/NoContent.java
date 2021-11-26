package de.ecconia.winfrasor.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.ecconia.winfrasor.components.Colors;

public class NoContent extends JPanel
{
	public NoContent(String content)
	{
		setBackground(Colors.contentBG);
		setBorder(new DebugBorder(Color.yellow));
		
		setMinimumSize(new Dimension(50, 50));
		
		setLayout(new BorderLayout());
		add(new JLabel(content), BorderLayout.NORTH);
		JTextArea area = new JTextArea();
		area.getDropTarget().setActive(false);
		add(area);
		add(new JLabel(content), BorderLayout.SOUTH);
	}
}
