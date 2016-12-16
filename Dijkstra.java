import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class Dijkstra implements Runnable {
	
	class State implements Comparable<State> {
		Point point;
		Point parent;
		double dist;
		@Override
		public int compareTo(State s) {
			return (int)(dist - s.dist);
		}
	}
	

	PriorityQueue<State> pq = new PriorityQueue<>();
	
	public Dijkstra() {	
		
		ShortestPaths.dijkstraRunning = true;
		
		State st = new State();
		st.point = ShortestPaths.startPoint;
		st.parent = ShortestPaths.startPoint;
		st.dist = 0.0;
		pq.add(st);
		for (Point p : ShortestPaths.allPoints) {
			ShortestPaths.dist.put(p, Double.MAX_VALUE);
		}
		ShortestPaths.dist.put(ShortestPaths.startPoint, 0.0);
		Thread th = new Thread(this);
		th.start();
			
	}
	
	@Override
	public void run() {
		while (pq.size() > 0) {
			State st = pq.poll();
			Point p =st.point;
			System.out.println("Point: " + p.x + "," + p.y);
			Double d = st.dist;
			if (Math.abs(ShortestPaths.dist.get(p) - d) > 0.000001) continue;
			ShortestPaths.dijkstraTree.add(new Line(p, st.parent));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ShortestPaths.panel.repaint();
//			try {
//				panel.repaint();
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			for (Point q : p.adjList) {
				if (ShortestPaths.dist.get(q) > d + p.distanceFrom(q)) {
					st = new State();
					st.point = q;
					st.parent = p;
					st.dist = d + p.distanceFrom(q);
					ShortestPaths.dist.put(q, st.dist);
					pq.add(st);
				}
			}
		}

		ShortestPaths.dijkstraRunning = false;
	}
		
}
