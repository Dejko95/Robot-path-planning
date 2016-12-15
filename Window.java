import javax.swing.JFrame;

public class Window extends JFrame {
	public Window() {
		super();
		setSize(800, 600);
		setTitle("Shortest path among obstacles");
		setVisible(true);
		setLocationRelativeTo(null);
		
		getContentPane().add(new Panel());
	}
}
