import java.util.ArrayList;
import java.util.Collections;

public class VisibilityGraph implements Runnable {

	static Line firstLine = null;
	
	public VisibilityGraph() {
		ShortestPaths.visibilityGraphRunning = true;
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		for (Point sPoint : ShortestPaths.allPoints) {
			
			ShortestPaths.currCenter = sPoint;
			
			Structure str = new SHeap();
			
			ShortestPaths.currPoint = new Point(9999, sPoint.y);
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
			
			for (int index=0; index<otherPoints.size(); index++) {
				firstLine = null;
				Point point = otherPoints.get(index);
				ShortestPaths.currPoint = point;
				
				if (Menu.speedValue < 10) {
					try {
						Thread.sleep((10-Menu.speedValue) * 20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				ShortestPaths.panel.repaint();
				
				boolean colinear = false;
				if (index > 0 && Point.colinear(sPoint, otherPoints.get(index-1), ShortestPaths.currPoint)) {
					colinear = true;
				}
				
				if (inside && point == sPoint.prev) inside = false;
				
				if (!inside && !colinear) {
					firstLine = str.minimum();
					if (firstLine == null || !firstLine.intersectWithLine(new Line(sPoint, point))) {
						sPoint.connectWith(point);
					}
					try {
						Thread.sleep((10-Menu.speedValue) * 20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ShortestPaths.panel.repaint();
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
