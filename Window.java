import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Window extends JFrame {
	public Window(Panel panel) {
		super();
		setSize(800, 600);
		setTitle("Shortest path among obstacles");
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP && !ShortestPaths.reading) {
					System.out.println("space");
					ShortestPaths.makeVisibilityGraph();
				}
			}
		});
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		//SgetContentPane().add(new Menu(), BorderLayout.EAST);
	}
}
