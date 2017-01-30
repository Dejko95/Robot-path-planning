import java.util.ArrayList;

public class SArray implements Structure {
	ArrayList<Line> lines;
	
	public SArray() {
		lines = new ArrayList<>();
	}
	
	@Override
	public void addLine(Line l) {
		if (!lines.contains(l))
			lines.add(l);
	}

	@Override
	public void removeLine(Line l) {
		lines.remove(l);
	}

	@Override
	public Line minimum() {
		if (lines.size() == 0) return null;
		Line min = lines.get(0);
		for (Line line : lines) {
			if (line.compareTo(min) < 0) {
				min = line;
			}
		}
		
		return min;
	}

}
