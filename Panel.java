import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Panel extends JPanel {
	public Panel() {
		super();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					ShortestPaths.addPoint(e.getX(), e.getY());
				}
				else {
					ShortestPaths.finishFigure();
				}
			}
		});
		
		
		
//		addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println("panel");
//				if (e.getKeyCode() == KeyEvent.VK_UP && !ShortestPaths.reading) {
//					System.out.println("space");
//					ShortestPaths.makeVisibilityGraph();
//				}
//			}
//		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		
		
		if (ShortestPaths.graphConstructed || ShortestPaths.visibilityGraphRunning) {
		//if (ShortestPaths.visibilityGraphRunning) {
			g.setColor(Color.BLUE);
			for (Point p : ShortestPaths.allPoints) {
				for (Point q : p.adjList) {
					g.drawLine(p.x, p.y, q.x, q.y);
				}
			}
//			g.setColor(Color.ORANGE);
//			g.drawLine(ShortestPaths.currCenter.x, ShortestPaths.currCenter.y, ShortestPaths.currPoint.x, ShortestPaths.currPoint.y);
//
//			g.setColor(Color.GREEN);
//			g.fillOval(ShortestPaths.currCenter.x-6, ShortestPaths.currCenter.y-6, 12, 12);
		}
		
		
		g.setColor(Color.BLACK);
		for (Point p : ShortestPaths.allPoints) {
			g.fillOval(p.x-4, p.y-4, 8, 8);
		}
		for (Line l : ShortestPaths.allLines) {
			g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
		}
		

		if (ShortestPaths.visibilityGraphRunning) {
			g.setColor(Color.ORANGE);
			g.drawLine(ShortestPaths.currCenter.x, ShortestPaths.currCenter.y, ShortestPaths.currPoint.x, ShortestPaths.currPoint.y);

			g.setColor(Color.GREEN);
			g.fillOval(ShortestPaths.currCenter.x-6, ShortestPaths.currCenter.y-6, 12, 12);
		}
		
		if (ShortestPaths.dijkstraRunning) {
			g.setColor(Color.RED);
			for (Line l : ShortestPaths.dijkstraTree) {
				g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
			}
		}
	}
	
}
