package View;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import Controller.Controller;

public abstract class View extends JFrame implements ModelObserver{
	private static final long serialVersionUID = 1L;
	protected JFreeChart pitchGraph,yawGraph,rollGraph;
	protected DefaultCategoryDataset pitchData,yawData,rollData;
	protected JSlider pitchSlider, yawSlider, rollSlider;
	protected static int pitchtime,yawtime,rolltime;
	protected Controller controller;
	protected JTextField  newPitch, newYaw, newRoll;
	protected JButton pidButton, setPointButton, setPIDButton;
	protected JTextField newKp,newKi,newKd;
	
	public View(String title){
		super(title);
		
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
	}
	
	protected void createView(){
		
	}
	void displayErrorMessage(String errorMessage){
		JOptionPane.showMessageDialog(this, errorMessage);
	}
	
	public float getNewPitch() throws NumberFormatException{
		return Integer.parseInt(newPitch.getText());
	}
	
	public float getNewYaw() throws NumberFormatException{
		return Integer.parseInt(newYaw.getText());
	}
	
	public float getNewRoll() throws NumberFormatException{
		return Integer.parseInt(newRoll.getText());
	}
	
	public float getNewKp() throws NumberFormatException{
		return Integer.parseInt(newKp.getText());
	}
	
	public float getNewKi() throws NumberFormatException{
		return Integer.parseInt(newKi.getText());
	}
	
	public float getNewKd() throws NumberFormatException{
		return Integer.parseInt(newKd.getText());
	}
	
	public void addPIDButtonListener(ActionListener listener){
		pidButton.addActionListener(listener);
	}
	
	public void addSetPointButtonListener(ActionListener listener){
		setPointButton.addActionListener(listener);
	}

	public void addPIDSetButtonListener(ActionListener listener) {
		setPIDButton.addActionListener(listener);
	}
}