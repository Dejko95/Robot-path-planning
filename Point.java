import java.util.ArrayList;

public class Point implements Comparable<Point> {
	int x;
	int y;
	
	ArrayList<Point> adjList = new ArrayList<>();
	ArrayList<Line> adjEdges = new ArrayList<>();
	Point prev;
	Point next;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void connectWith(Point p) {
		adjList.add(p);
	}
	
	public void addEdge(Line l) {
		adjEdges.add(l);
	}

	@Override
	public int compareTo(Point p) {
		// TODO Auto-generated method stub
		Point s = ShortestPaths.currCenter;
		
		int dy = y - s.y;
		int dx = x - s.x;
		
		int dy1 = p.y - s.y;
		int dx1 = p.x - s.x;
		
		int kv = 0, kv1 = 0;
		
		if (dx > 0 && dy >=0) kv = 0;
		else if (dx <= 0 && dy > 0) kv = 1;
		else if (dx < 0 && dy <= 0 ) kv = 2;
		else if (dx >= 0 && dy < 0 ) kv = 3;
		
		if (dx1 > 0 && dy1 >=0) kv1 = 0;
		else if (dx1 <= 0 && dy1 > 0) kv1 = 1;
		else if (dx1 < 0 && dy1 <= 0 ) kv1 = 2;
		else if (dx1 >= 0 && dy1 < 0 ) kv1 = 3;
		
		if (kv != kv1) return kv - kv1;
		
		//u istom su kvadrantu
		if (dy * dx1 - dy1 * dx != 0) 		//nisu kolinearne -> gledamo ugao
			return dy * dx1 - dy1 * dx; 
		else								//jesu kolinearne -> gledamo rastojanje (kvadrat)
			return (dx*dx + dy*dy) - (dx1*dx1 + dy1*dy1);
	}
	
	static boolean colinear(Point s, Point a, Point b) {
		int ax = a.x - s.x;
		int ay = a.y - s.y;
		int bx = b.x - s.x;
		int by = b.y - s.y;
		
		return ax * by == bx * ay;
	}
	
	public double distanceFrom(Point q) {
		return Math.sqrt((x - q.x) * (x - q.x) + (y - q.y) * (y - q.y));
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
