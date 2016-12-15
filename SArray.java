import java.util.ArrayList;

public class SArray implements Structure {
	ArrayList<Line> lines;
	
	public SArray() {
		lines = new ArrayList<>();
	}
	
	@Override
	public void addLine(Line l) {
		// TODO Auto-generated method stub
		if (!lines.contains(l))
			lines.add(l);
	}

	@Override
	public void removeLine(Line l) {
		// TODO Auto-generated method stub
		lines.remove(l);
	}

	@Override
	public Line minimum() {
		if (lines.size() == 0) return null;
		Line min = lines.get(0);
		for (Line line : lines) {
			Point s = ShortestPaths.currCenter;
			Point p = ShortestPaths.currPoint;
			if (line.distanceFromPoint(s, p) < min.distanceFromPoint(s, p)) {
				min = line;
			}
		}
		
		return min;
	}

}
