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
	protected JButton pidButton, setPointButton, getPointButton ,setPIDButton, getPIDButton;
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
		getPointButton = new JButton("Get Point");
		setPIDButton = new JButton("Set PID");
		getPIDButton = new JButton("Get PID");
		
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
		return Float.parseFloat(newPitch.getText());
	}
	
	public float getNewYaw() throws NumberFormatException{
		return Float.parseFloat(newYaw.getText());
	}
	
	public float getNewRoll() throws NumberFormatException{
		return Float.parseFloat(newRoll.getText());
	}
	
	public float getNewKp() throws NumberFormatException{
		return Float.parseFloat(newKp.getText());
	}
	
	public float getNewKi() throws NumberFormatException{
		return Float.parseFloat(newKi.getText());
	}
	
	public float getNewKd() throws NumberFormatException{
		return Float.parseFloat(newKd.getText());
	}
	
	public void addPIDButtonListener(ActionListener listener){
		pidButton.addActionListener(listener);
	}
	
	public void addSetPointButtonListener(ActionListener listener){
		setPointButton.addActionListener(listener);
	}
	
	public void addGetPointButtonListener(ActionListener listener){
		getPointButton.addActionListener(listener);
	}

	public void addSetPIDButtonListener(ActionListener listener) {
		setPIDButton.addActionListener(listener);
	}
	
	public void updatePID(float kp,float ki,float kd){
		newKp.setText(Float.toString(kp));
		newKi.setText(Float.toString(ki));
		newKd.setText(Float.toString(kd));
	}
	
	public void updatePos(float pitch,float yaw, float roll){
		newPitch.setText(Float.toString(pitch));
		newYaw.setText(Float.toString(yaw));
		newRoll.setText(Float.toString(roll));
		
		pitchSlider.setValue((int)(pitch));
		yawSlider.setValue((int)(yaw));
		rollSlider.setValue((int)(roll));
	}

	public void addGetPIDButtonListener(ActionListener listener){
		getPIDButton.addActionListener(listener);
	}
}