import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ShortestPaths {
	static int m;
	static Scanner sc;
	static Point currCenter;
	static Point currPoint;
	static ArrayList<Figure> figures = new ArrayList<>();
	static ArrayList<Line> allLines = new ArrayList<>();
	static ArrayList<Point> allPoints = new ArrayList<>();
	static boolean reading = false;
	static Window window;
	static Panel panel;
	static boolean graphConstructed = false;
	static Point startPoint = new Point(0, 0);
	static Point endPoint = new Point(750, 550);
	static HashMap<Point, Double> dist = new HashMap<>();
	static ArrayList<Line> dijkstraTree = new ArrayList<>();
	static boolean visibilityGraphRunning = false;
	static boolean dijkstraRunning = false;
	
	public static void addPoint(int x, int y) {
		Point p = new Point(x,y);
		
		Figure figure;
		if (!reading) {
			figure = new Figure();
			figures.add(figure);
			reading = true;
		}
		else {
			figure = figures.get(figures.size()-1);
			Point q = figure.getPointAt(figure.points.size()-1);
			Line l = new Line(p, q);
			allLines.add(l);
			q.addEdge(l);
			q.next = p;
			p.addEdge(l);
			p.prev = q;
			//panel.drawLine(p, figure.getPointAt(figure.points.size()-1));
		}
		//panel.drawPoint(p);
		
		figure.addNextPoint(p);
		allPoints.add(p);
		panel.repaint();
	}
	
	public static void finishFigure() {
		if (!reading) return;
		
		Figure figure = figures.get(figures.size()-1);
		Point u = figure.getPointAt(figure.points.size()-1);
		Point v = figure.getPointAt(0);
		Line l = new Line(u, v);
		allLines.add(l);
		u.addEdge(l);
		u.next = v;
		v.addEdge(l);
		v.prev = u;
		
		System.out.println(allLines.size());
		
		reading = false;
		
		panel.repaint();
	}

	public static void main(String[] args) {
		allPoints.add(startPoint);
		allPoints.add(endPoint);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				panel = new Panel();
				window = new Window(panel);
			}
		});
	}
	
	public static void makeVisibilityGraph() {
		new VisibilityGraph();
	}
	
	public static void runDijkstra() {
		new Dijkstra();
	}

}
