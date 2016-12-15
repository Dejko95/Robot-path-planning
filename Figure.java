
import java.util.ArrayList;

public class Figure {
	ArrayList<Point> points;
	
	public Figure() {
		points = new ArrayList<>();
	}
	
	public void addNextPoint(Point p) {
		points.add(p);
	}
	
	public Point getPointAt(int index) {
		return points.get(index);
	}
}
