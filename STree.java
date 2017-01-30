import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class STree implements Structure {

	class State implements Comparable<State> {
		Line line;
		@Override
		public int compareTo(State s) {
			if (this.equals(s)) {
				return 0;
			}
			Point a = ShortestPaths.currCenter;
			Point b = ShortestPaths.currPoint;
			if (Math.abs(line.distanceFromPoint(a, b) - s.line.distanceFromPoint(a,b)) > 0.00001)
				return (int)(line.distanceFromPoint(a, b) - s.line.distanceFromPoint(a,b));
			Point p,q,z;
			if (line.a == s.line.a || line.a == s.line.b) {
				p = line.b;
				z = line.a;
			}
			else {
				p = line.a;
				z = line.b;
			}
			if (s.line.a == line.a || s.line.a == line.b) q = s.line.b;
			else q = s.line.a;

			if (Line.isLeftTurn(p, z, q)) {
				return -1;
			}
			else return 1;
		}
		@Override
		public boolean equals(Object obj) {
			Line l  = ((State)obj).line;
			return (l.a.x == line.a.x && l.a.y == line.a.y && l.b.x == line.b.x && l.b.y == line.b.y);
			//return line.equals(((State)obj).line);
		}
	}
	
	TreeMap<State, Line> tree = new TreeMap<>();
	
	@Override
	public void addLine(Line l) {
		State st = new State();
		st.line = l;
		tree.put(st, l);
	}

	@Override
	public void removeLine(Line l) {
		State st = new State();
		st.line = l;
		st = new State();
		st.line = new Line(l.b, l.a);
	}

	@Override
	public Line minimum() {
		if (tree.isEmpty()) return null;
		return tree.firstKey().line;
	}

}
