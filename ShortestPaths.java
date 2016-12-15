import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ShortestPaths {
	static int m;
	static Scanner sc;
	static Point currCenter;
	static Point currPoint;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		
		m = sc.nextInt();
		
		ArrayList<Figure> figures = new ArrayList<>();
		ArrayList<Line> allLines = new ArrayList<>();
		ArrayList<Point> allPoints = new ArrayList<>();
		
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
		Point p = new Point(x,y);
		allPoints.add(p);
		
		//read end point
		//x = sc.nextInt();
		//y = sc.nextInt();
		//p = new Point(x,y);
		//allPoints.add(p);
		
		for (Point sPoint : allPoints) {
		//{	
			//Point sPoint = allPoints.get(0);
			currCenter = sPoint;
			
			Structure str = new SArray();
			
			//init structure with all lines that cross horizontal line from point
			for (Line line : allLines) {
				if (line.intersectHalfLine(sPoint)) {
					str.addLine(line);
				}
			}
			
			ArrayList<Point> otherPoints = new ArrayList<>();
			for (Point point : allPoints) {
				if (point != sPoint) otherPoints.add(point);
			}
			
			Collections.sort(otherPoints);
			
			
			for (int i=0; i<otherPoints.size()-1; i++) {
				Point p1 = otherPoints.get(i);
				Point p2 = otherPoints.get(i+1);
				if (Point.colinear(sPoint, p1, p2)) {
					System.out.println("(" + p1.x + "," + p1.y + ") i (" + p2.x + "," + p2.y +") su kolinearne");
				}
			}
			
			boolean inside = false;
			for (Point point: otherPoints) {
				if (point == sPoint.next) {
					break;
				}
				if (point == sPoint.prev) {
					inside = true;
					break;
				}
			}
			
			//for (Point point : otherPoints) {
			for (int index=0; index<otherPoints.size(); index++) {
				Point point = otherPoints.get(index);
				currPoint = point;
				
				boolean colinear = false;
				if (index > 0 && Point.colinear(sPoint, otherPoints.get(index-1), currPoint)) {
					colinear = true;
				}
				
				if (inside && point == sPoint.prev) inside = false;
				
				//System.out.println("Obradjuje se " + point.x + " " + point.y);
				//System.out.println("inside: " + inside);
				if (!inside && !colinear) {
					//System.out.print("Lista edgeva: ");
					//for (Line line : ((SArray)str).lines) {
					//	System.out.print("(" + line.a.x + "," + line.a.y + "  " + line.b.x + "," + line.b.y + "), ");
					//}
					//System.out.println();
					Line firstLine = str.minimum();
					if (firstLine == null || !firstLine.intersectWithLine(new Line(sPoint, point))) {
						sPoint.connectWith(point);
						//System.out.println("VIDI SE");
					}
				}
				for (int i=0; i<point.adjEdges.size(); i++) {
					Point adj;
					Line edge = point.adjEdges.get(i);
					if (edge.a == point) adj = edge.b;
					else adj = edge.a;
					if (Line.isLeftTurn(sPoint, point, adj)) {
						//System.out.println("pre " + ((SArray)str).lines.size());
						//System.out.println("ubacivanje----(" + edge.a.x + "," + edge.a.y + "  " + edge.b.x + "," + edge.b.y + ")----------");
						str.addLine(edge);
						//System.out.println("posle " + ((SArray)str).lines.size());
					}
					else if (Line.isRightTurn(sPoint, point, adj)) {
						//System.out.println("pre " + ((SArray)str).lines.size());
						//System.out.println("izbacivanje----(" + edge.a.x + "," + edge.a.y + "  " + edge.b.x + "," + edge.b.y + ")----------");
						str.removeLine(edge);
						//System.out.println("posle " + ((SArray)str).lines.size());
					}
				}
				
				if (!inside && point == sPoint.next) inside = true;
			}
			System.out.println("Vidljive iz " + sPoint.x + "," + sPoint.y + ":");
			for (Point point : sPoint.adjList) {
				System.out.println(point.x + ", " + point.y);
			}
			System.out.println();
		}
		
		
	}

}
