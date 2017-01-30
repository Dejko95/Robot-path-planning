import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.omg.CORBA.ShortHolder;

public class Menu extends JPanel {
	public static final int NONE = 0,
							DRAW_POLYGONS = 1,
							START_POINT = 2,
							END_POINT = 3,
							SOLVING = 4;
	
	static JButton newPolygon;
	static JButton addStart;
	static JButton addEnd;
	static JButton solve;
	static JButton load;
	static JSpinner speed;
	
	static int state = NONE;
	static int speedValue = 5;
	static SpinnerListModel speedModel;
	
	public Menu() {
		super();
		setPreferredSize(new Dimension(120, 120));
		setLayout(new GridLayout(2, 3, 0, 0));
		initComponents();
	}
	
	private void initComponents() {
		newPolygon = new JButton("Draw polygons");
		addButton(newPolygon, DRAW_POLYGONS);
	
		addStart = new JButton("Set start point");
		addButton(addStart, START_POINT);
		
		addEnd = new JButton("Set end point");
		addButton(addEnd, END_POINT);
		
		load = new JButton("Load");
		addButton(load, NONE);
		
		JPanel speedPanel = new JPanel();
		add(speedPanel);
		speedPanel.setLayout(null);
		JLabel lbl = new JLabel("Algorithm speed");
		lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		lbl.setBounds(20, 10, 140, 40);
		speedPanel.add(lbl);
		Integer values[] = {1,2,3,4,5,6,7,8,9,10};
		speedModel = new SpinnerListModel(values);
		speedModel.setValue(values[4]);
		speed = new JSpinner(speedModel);
		speed.setBounds(160, 15, 80, 35);
		speed.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				speedValue = (Integer)speedModel.getValue();
			}
		});
		
		speedPanel.add(speed);
		
		solve = new JButton("Solve");
		addButton(solve, SOLVING);
	}
	
	private void addButton(JButton btn, int newState) {
		JPanel pnl = new JPanel();
		add(pnl);
		
		pnl.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		pnl.setLayout(new BorderLayout());
		pnl.add(btn, BorderLayout.CENTER);
		btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				state = newState;
				if (state == SOLVING) {
					disableAll();
					ShortestPaths.makeVisibilityGraph();
				}
				if (btn.getText().equals("Load")) {
					ShortestPaths.loadFile();
					ShortestPaths.panel.repaint();
				}
			}
		});
	}
	
	static void disableAll() {
		newPolygon.setEnabled(false);
		addStart.setEnabled(false);
		addEnd.setEnabled(false);
		solve.setEnabled(false);
		load.setEnabled(false);
	}
	
	static void enableAll() {
		newPolygon.setEnabled(true);
		addStart.setEnabled(true);
		addEnd.setEnabled(true);
		solve.setEnabled(true);
		load.setEnabled(true);
	}
	
	static void setSpeed(int val) {
		speedModel.setValue((Integer)val);
		speedValue = val;
	}
}
