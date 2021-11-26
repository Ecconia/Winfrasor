package de.ecconia.winfrasor.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.ecconia.winfrasor.components.Colors;

/**
 * A class just for the original example. It has some example content.
 *
 * Be aware, that most Swing Text fields and areas do steal the drop focus!
 *  Can be prevented by calling'{object}.getDropTarget().setActive(false);'
 *  Not a good solution, but maybe enough for your usecase.
 *  If you know a better solution let me know.
 */
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
