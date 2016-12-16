import java.util.ArrayList;
import java.util.Collections;

public class VisibilityGraph implements Runnable {

	public VisibilityGraph() {
		ShortestPaths.visibilityGraphRunning = true;
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		for (Point sPoint : ShortestPaths.allPoints) {
			ShortestPaths.currCenter = sPoint;
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			ShortestPaths.panel.repaint();
//			System.out.println("---");
			
			Structure str = new SArray();
			
			//init structure with all lines that cross horizontal line from point
			for (Line line : ShortestPaths.allLines) {
				if (line.intersectHalfLine(sPoint)) {
					str.addLine(line);
				}
			}
			
			ArrayList<Point> otherPoints = new ArrayList<>();
			for (Point point : ShortestPaths.allPoints) {
				if (point != sPoint) otherPoints.add(point);
			}
			
			Collections.sort(otherPoints);
			
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
				ShortestPaths.currPoint = point;
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ShortestPaths.panel.repaint();
				
				boolean colinear = false;
				if (index > 0 && Point.colinear(sPoint, otherPoints.get(index-1), ShortestPaths.currPoint)) {
					colinear = true;
				}
				
				if (inside && point == sPoint.prev) inside = false;
				
				if (!inside && !colinear) {
					Line firstLine = str.minimum();
					if (firstLine == null || !firstLine.intersectWithLine(new Line(sPoint, point))) {
						sPoint.connectWith(point);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ShortestPaths.panel.repaint();
					}
				}
				for (int i=0; i<point.adjEdges.size(); i++) {
					Point adj;
					Line edge = point.adjEdges.get(i);
					if (edge.a == point) adj = edge.b;
					else adj = edge.a;
					if (Line.isLeftTurn(sPoint, point, adj)) {
						str.addLine(edge);
					}
					else if (Line.isRightTurn(sPoint, point, adj)) {
						str.removeLine(edge);
					}
				}
				
				if (!inside && point == sPoint.next) inside = true;
			}
		}
		
		ShortestPaths.graphConstructed = true;
		ShortestPaths.panel.repaint();

		ShortestPaths.visibilityGraphRunning = false;
		
		ShortestPaths.runDijkstra();
	}

}
