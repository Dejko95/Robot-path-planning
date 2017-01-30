import java.io.File;
import java.io.FileNotFoundException;
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
	static boolean dijkstraFinished = false;
	
	public static void addPoint(int x, int y) {
		Point p = new Point(x,y);
		System.out.println(x + "," + y);
		
		Figure figure;
		if (!reading) {
			figure = new Figure();
			figures.add(figure);
			reading = true;
			Menu.disableAll();
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
		}
		
		figure.addNextPoint(p);
		allPoints.add(p);
		panel.repaint();
	}
	
	public static void addStartPoint(int x, int y) {
		startPoint = new Point(x, y);
		allPoints.set(0, startPoint);
		panel.repaint();
	}
	
	public static void addEndPoint(int x, int y) {
		endPoint = new Point(x, y);
		allPoints.set(1, endPoint);
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
		
		reading = false;
		
		panel.repaint();

		Menu.enableAll();
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
	
	public static void loadFile() {
		allLines = new ArrayList<>();
		figures = new ArrayList<>();
		allPoints = new ArrayList<>();
		allPoints.add(startPoint);
		allPoints.add(endPoint);
		
		try {
			sc = new Scanner(new File("example.in"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m = sc.nextInt();
		 		
 		for (int i=0; i<m; i++) {
 			int n = sc.nextInt();
 			Figure figure = new Figure();
 			for (int j=0; j<n; j++) {
 				int x = sc.nextInt();
 				int y = sc.nextInt();
 				Point p = new Point(x,y);
 				figure.addNextPoint(p);
 				allPoints.add(p);
 			}
 			for (int j=0; j<n-1; j++) {
 				Point u = figure.getPointAt(j);
 				Point v = figure.getPointAt(j+1);
 				Line l = new Line(u, v);
 				allLines.add(l);
 				u.addEdge(l);
 				u.next = v;
 				v.addEdge(l);
 				v.prev = u;
 			}
 			Point u = figure.getPointAt(n-1);
 			Point v = figure.getPointAt(0);
 			Line l = new Line(u, v);
 			allLines.add(l);
 			u.addEdge(l);
 			u.next = v;
 			v.addEdge(l);
 			v.prev = u;
 		}
 		
 		//read start point
 		int x = sc.nextInt();
 		int y = sc.nextInt();
 		startPoint = new Point(x,y);
 		allPoints.set(0, startPoint);
 		
 		//read end point
 		x = sc.nextInt();
 		y = sc.nextInt();
 		endPoint = new Point(x,y);
 		allPoints.set(1, endPoint);
	}
}
