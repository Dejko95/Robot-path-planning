import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class SHeap implements Structure {

	class State implements Comparable<State> {
		Line line;
		double dist;
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
			return line.equals(((State)obj).line);
		}
	}
	
	PriorityQueue<State> pq = new PriorityQueue<>();
	HashMap<Line, Double> dist = new HashMap<>();
	
	@Override
	public void addLine(Line l) {
		State st = new State();
		st.line = l;
		st.dist = l.distanceFromPoint(ShortestPaths.currCenter, ShortestPaths.currPoint);
		pq.add(st);
		dist.put(st.line, st.dist);
	}

	@Override
	public void removeLine(Line l) {
		// TODO Auto-generated method stub
		dist.remove(l);
		State st = new State();
		st.line = l;
		pq.remove(st);
	}

	@Override
	public Line minimum() {
		while (!pq.isEmpty() && (!dist.containsKey(pq.peek().line) || pq.peek().dist != dist.get(pq.peek().line))) {
			pq.poll();
		}
		if (pq.isEmpty()) return null;
		for (State state : pq) {
			if (dist.containsKey(state.line) && state.line.distanceFromPoint(ShortestPaths.currCenter, ShortestPaths.currPoint)+0.00001 < pq.peek().line.distanceFromPoint(ShortestPaths.currCenter, ShortestPaths.currPoint)) {
				Line l = pq.peek().line;
				l = state.line;
				while (2>1) {
				}
			}
		}
		return pq.peek().line;
	}

}
