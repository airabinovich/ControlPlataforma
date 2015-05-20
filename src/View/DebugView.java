package View;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


import Controller.Controller;
import Model.CommunicationModel;

public class DebugView extends View {

	
	private JButton pidButton, setPointButton;
	private JTextField  newPitch, newYaw, newRoll;
	private Container contentpane;
	
	public DebugView(Controller controller,CommunicationModel model) {
		this.controller = controller;
		this.model = model;
		
		pitchtime = 0;
		yawtime = 0;
		rolltime = 0;
		
		pitchData = new DefaultCategoryDataset();
		yawData = new DefaultCategoryDataset();
		rollData = new DefaultCategoryDataset();
		
		pitchGraph = ChartFactory.createLineChart("Pitch", "Tiempo", "Grados", pitchData);
		yawGraph = ChartFactory.createLineChart("Yaw", "Tiempo", "Grados", yawData);
		rollGraph = ChartFactory.createLineChart("Roll", "Tiempo", "Grados", rollData);
		
		pidButton = new JButton("PID");
		setPointButton = new JButton("Set Point");
		
		newPitch = new JTextField();
		newYaw = new JTextField();
		newRoll = new JTextField();
		
		ventana = new JFrame("Control de Plataforma - Debug Mode");
		
		createView();
	}

	private void createView() {
		
		contentpane = ventana.getContentPane();
		
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.lightGray);
		
		ventana.setResizable(false);
		ventana.setBounds(200, 50, 1000, 600);
		
		int alto = ventana.getHeight(), ancho = ventana.getWidth();
		
		JPanel	pitchPanel = new ChartPanel(pitchGraph),
				yawPanel = new ChartPanel(yawGraph),
				rollPanel = new ChartPanel(rollGraph);
		
		pitchPanel.setBounds(0, 0, ancho/2, alto/3);
		yawPanel.setBounds(0, alto/3, ancho/2, alto/3);
		rollPanel.setBounds(0, 2*alto/3, ancho/2, alto/3);
		
		newPitch.setBounds(ancho/2 + 10, alto/6 + 40, 150, 30);
		newYaw.setBounds(ancho/2 + 10, alto/6 + 80, 150, 30);
		newRoll.setBounds(ancho/2 + 10, alto/6 + 120, 150, 30);
		
		JTextField	pitchText = new JTextField("Pitch"),
					yawText = new JTextField("Yaw"),
					rollText = new JTextField("Roll");
		
		pitchText.setBounds(newPitch.getX() + newPitch.getWidth() + 10, newPitch.getY(), 100, 30);
		yawText.setBounds(newYaw.getX() + newYaw.getWidth() + 10, newYaw.getY(), 100, 30);
		rollText.setBounds(newRoll.getX() + newRoll.getWidth() + 10, newRoll.getY(), 100, 30);
		
		pitchText.setEditable(false);
		yawText.setEditable(false);
		rollText.setEditable(false);
		
		pidButton.setBounds(ancho/2 + 120, alto/6, 100, 30);
		setPointButton.setBounds(ancho/2 + 10, alto/6, 100, 30);
		
		contentpane.add(pitchPanel);
		contentpane.add(yawPanel);
		contentpane.add(rollPanel);
		contentpane.add(newPitch);
		contentpane.add(newYaw);
		contentpane.add(newRoll);
		contentpane.add(pidButton);
		contentpane.add(setPointButton);
		contentpane.add(pitchText);
		contentpane.add(yawText);
		contentpane.add(rollText);
		contentpane.add(fondo);
		
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void updatePitch(float newPitch) {
		pitchData.addValue(newPitch, "pitch", (Integer)(++pitchtime));

	}

	@Override
	public void updateRoll(float newRoll) {
		rollData.addValue(newRoll, "roll", (Integer)(++rolltime));
	}

	@Override
	public void updateYaw(float newYaw) {
		yawData.addValue(newYaw, "yaw", (Integer)(++yawtime));
	}

	@Override
	public void updateAccelX() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccelY() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccelZ() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHeight() {
		// TODO Auto-generated method stub

	}

}
