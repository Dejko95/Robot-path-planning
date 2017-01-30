public class Line implements Comparable<Line>{
	Point a;
	Point b;
	
	public Line(Point a, Point b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean intersectHalfLine(Point p) {
		if (p.y >= Math.max(a.y, b.y)) return false; ///////not allowed
		if (p.y <= Math.min(a.y, b.y)) return false; ///////to go through point only
		if (a.y < b.y) return isLeftTurn(a,b,p);
		if (a.y > b.y) return isRightTurn(a,b,p);
		return false;
	}
	
	public static boolean isLeftTurn(Point a, Point b, Point p) {
		Point v1 = new Point(b.x-a.x, b.y-a.y);
		Point v2 = new Point(p.x-b.x, p.y-b.y);
		
		return determinant(v1, v2) > 0;
	}
	
	public static boolean isRightTurn(Point a, Point b, Point p) {
		Point v1 = new Point(b.x-a.x, b.y-a.y);
		Point v2 = new Point(p.x-b.x, p.y-b.y);
		
		return determinant(v1, v2) < 0;
	}
	
	public static int determinant(Point v1, Point v2) {
		return v1.x * v2.y - v1.y * v2.x;
	}
	
	public boolean intersectWithLine(Line l) {
		return ((isLeftTurn(a, b, l.a) && isRightTurn(a, b, l.b)) || (isRightTurn(a, b, l.a) && isLeftTurn(a, b, l.b)))
			&& ((isLeftTurn(l.a, l.b, a) && isRightTurn(l.a, l.b,b)) || (isRightTurn(l.a, l.b, a) && isLeftTurn(l.a, l.b,b)));		
	}
	
	public static boolean xor(Boolean x, boolean y) {
		return (( x || y ) && !( x && y ));
	}
	
	static PointDouble projection(Line line, Point p) {
		Point vect = new Point(line.b.x - line.a.x, line.b.y - line.a.y);
		double k = (double)scalarProduct(new Point(p.x - line.a.x, p.y - line.a.y), vect) / (double)scalarProduct(vect, vect);
		PointDouble proj = new PointDouble(k * vect.x + line.a.x, k * vect.y + line.a.y);
		return proj;
	}
	
	double distanceFromPoint(Point s, Point p) {
		PointDouble projS = projection(this, s);
		PointDouble projA = projection(new Line(s, p), a);
		PointDouble projB = projection(new Line(s, p), b);
		
		double area = dist(a.x - b.x, a.y - b.y) * dist(s.x - projS.x, s.y - projS.y);
		double dist = area / (dist(b.x - projB.x, b.y - projB.y) + dist(a.x - projA.x, a.y - projA.y));
		
		return dist;
	}
	
	static int scalarProduct(Point a, Point b) {
		return a.x * b.x + a.y * b.y;
	}
	
	static double dist(double x, double y) {
		return Math.sqrt(x*x + y*y);
	}

	@Override
	public int compareTo(Line o) {
		Point s = ShortestPaths.currCenter;
		Point p = ShortestPaths.currPoint;
		if (o.distanceFromPoint(s, p) < this.distanceFromPoint(s, p)) {
			return 1;
		}
		else return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
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
		Line other = (Line) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
}
