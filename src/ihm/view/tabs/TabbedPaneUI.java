package ihm.view.tabs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class TabbedPaneUI extends BasicTabbedPaneUI {

	public static final String CLOSE_WIDTH = "     ";
	private int x1 = 0;
	private int x2 = 0;
	private int y1 = 0;
	private int y2 = 0;

	@Override
	protected void installListeners() {
		super.installListeners();
		tabPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (contains(e.getPoint()))
				{
					JTabbedPane tabPane = (JTabbedPane) e.getSource();
					int tabIndex = tabForCoordinate(tabPane, e.getX(), e.getY());
					tabPane.remove(tabIndex);
					TabsManager.getManager().close();
				}
			}

		});
	}

	@Override
	protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
	        Rectangle textRect) {
		super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
		Graphics2D vGraphics = (Graphics2D) g;
		vGraphics.setPaint(Color.WHITE);
		vGraphics.fillOval(textRect.x + textRect.width - 8, textRect.y + textRect.height - 15, textRect.height,
		        textRect.height);
		vGraphics.setPaint(Color.BLACK);
		vGraphics.drawString("✕", textRect.x + textRect.width - 5, textRect.y + textRect.height - 3);

		x1 = textRect.x + textRect.width - 8;
		x2 = x1 + textRect.height;
		y1 = textRect.y + textRect.height - 15;
		y2 = y1 + textRect.height;
	}

	public boolean contains(Point aPoint) {
		return x1 <= aPoint.getX() && x2 >= aPoint.getX() && y1 <= aPoint.getY() && y2 >= aPoint.getY();
	}

}