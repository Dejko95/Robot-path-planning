import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Panel extends JPanel {
	public Panel() {
		super();
		
		setBorder(new LineBorder(Color.GRAY));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (Menu.state == Menu.DRAW_POLYGONS) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						ShortestPaths.addPoint(e.getX(), e.getY());
					}
					else {
						ShortestPaths.finishFigure();
					}
				}
				else if (Menu.state == Menu.START_POINT) {
					ShortestPaths.addStartPoint(e.getX(), e.getY());
				}
				else if (Menu.state == Menu.END_POINT) {
					ShortestPaths.addEndPoint(e.getX(), e.getY());
				}
			}
		});

	}
	
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponents(g1);
		Graphics2D g = (Graphics2D)g1;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (ShortestPaths.graphConstructed || ShortestPaths.visibilityGraphRunning) {
			g.setColor(Color.BLUE);
			g.setStroke(new BasicStroke(1));
			for (Point p : ShortestPaths.allPoints) {
				try {
					for (Point q : p.adjList) {
						g.drawLine(p.x, p.y, q.x, q.y);
					}
				} catch (Exception e) {
				}
			}
		}
		

		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		for (Point p : ShortestPaths.allPoints) {
			g.fillOval(p.x-4, p.y-4, 8, 8);
		}
		for (Line l : ShortestPaths.allLines) {
			g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
		}
		

		if (ShortestPaths.visibilityGraphRunning) {

			g.setStroke(new BasicStroke(1));
			g.setColor(Color.ORANGE);
			g.drawLine(ShortestPaths.currCenter.x, ShortestPaths.currCenter.y, ShortestPaths.currPoint.x, ShortestPaths.currPoint.y);
			g.fillOval(ShortestPaths.currPoint.x-6, ShortestPaths.currPoint.y-6, 12, 12);

			g.setColor(Color.GREEN);
			g.fillOval(ShortestPaths.currCenter.x-6, ShortestPaths.currCenter.y-6, 12, 12);
			
			Line l = VisibilityGraph.firstLine;
			if (l != null) {
				g.setColor(Color.RED);
				g.setStroke(new BasicStroke(3));
				g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
			}
		}
		
		if (ShortestPaths.dijkstraRunning) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(2));
			
			for (Line l : ShortestPaths.dijkstraTree) {
				g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
			}
		}
		
		if (ShortestPaths.dijkstraFinished) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(3));
			
			Point p = ShortestPaths.endPoint;
			for (int i=ShortestPaths.dijkstraTree.size()-1; i>=0; i--) {
				Line l = ShortestPaths.dijkstraTree.get(i);
				if (l.a == p) {
					g.drawLine(l.a.x, l.a.y, l.b.x, l.b.y);
					p = l.b;
				}
			}
		}
	}
	
}
