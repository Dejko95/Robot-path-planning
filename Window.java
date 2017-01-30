import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Window extends JFrame {
	public Window(Panel panel) {
		super();
		setSize(800, 600);
		setTitle("Shortest path among obstacles");
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		JPanel tmp = new JPanel();
		tmp.setBorder(new EmptyBorder(15, 15, 15, 15));
		tmp.setLayout(new BorderLayout());
		tmp.add(panel, BorderLayout.CENTER);
		getContentPane().add(tmp, BorderLayout.CENTER);
		getContentPane().add(new Menu(), BorderLayout.NORTH);
	}
}
