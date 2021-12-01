package de.ecconia.winfrasor.components.tabpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.Colors;
import de.ecconia.winfrasor.components.dnd.drag.DragHandler;
import de.ecconia.winfrasor.components.dnd.drag.DragListener;

/**
 * A Tab entry in the TabPanes header.
 */
public class TabPaneEntry extends JComponent implements DragListener
{
	//TODO: Import/make global:
	private final int tabHeight = 30;
	
	private final TabPaneHeader header;
	private final TabData tab;
	private boolean active;
	private boolean hovered;
	
	public TabPaneEntry(TabData tab, TabPaneHeader header)
	{
		this.tab = tab;
		this.header = header;
		
		addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				hovered = false;
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovered = true;
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				Point p = new Point(e.getX(), e.getY());
				if(e.getButton() == MouseEvent.BUTTON2 || isClosePressed(p))
				{
					header.closed(TabPaneEntry.this);
					return;
				}
				
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					header.clicked(TabPaneEntry.this);
					return;
				}
			}
		});
		
		setBorder(new LineBorder(Color.black, 1));
		
		setFont(UIManager.getFont("Panel.font"));
		FontMetrics metrics = getFontMetrics(getFont());
		String title = tab.getTitle();
		int titleWidth = metrics.stringWidth(title);
		int tabWidth = 10 + titleWidth + tabHeight;
		setPreferredSize(new Dimension(tabWidth, tabHeight));
		
		new DragHandler(this, this, tab);
	}
	
	private boolean isClosePressed(Point p)
	{
		int inner = tabHeight - 10;
		int x = p.x - (getWidth() - inner);
		int y = p.y - (getHeight() - inner);
		
		return x > 0 && y > 0 && x <= inner - 5 && y <= inner - 5;
	}
	
	@Override
	public void paint(Graphics g)
	{
		paintBorder(g);
		
		FontMetrics metrics = getFontMetrics(getFont());
		int titleHeight = metrics.getAscent();
		String title = tab.getTitle();
		int titleWidth = metrics.stringWidth(title);
		
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		
		int tabWidth = 10 + titleWidth + tabHeight;
		
		g.setColor(active ? Colors.selectedBG : Colors.unselectedBG);
		if(hovered)
		{
			g.setColor(Colors.hoverBG);
		}
		g.fillRect(x, y, tabWidth, tabHeight);
		
		g.setColor(Colors.text);
		g.drawString(title, x + 10, y + tabHeight / 2 + titleHeight / 2 - 1);
		
		g.setColor(Color.black);
		int x1 = x + tabWidth - (tabHeight - 10);
		int y1 = y + 10;
		int x2 = x + tabWidth - 10;
		int y2 = y + tabHeight - 10;
		
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y1, x1, y2);
		
		setPreferredSize(new Dimension(tabWidth, tabHeight));
	}
	
	public void setActive(boolean b)
	{
		active = b;
		repaint();
	}
	
	public TabData getTab()
	{
		return tab;
	}
	
	@Override
	public void dragStart()
	{
		setVisible(false);
		header.repaint();
	}
	
	@Override
	public void dragEnd()
	{
		setVisible(true);
		header.repaint();
	}
	
	@Override
	public void dragSuccessful()
	{
		header.closed(this);
	}
}
