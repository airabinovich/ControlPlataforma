package View;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import Controller.Controller;
import Model.GradoDeLibertad;
import Model.ModelObserver;
import Model.PlatformModel;


public abstract class View extends JFrame{
	private static final long serialVersionUID = 1L;
	protected JFreeChart pitchGraph,yawGraph,rollGraph;
	protected DefaultCategoryDataset pitchData,yawData,rollData;
	protected DefaultCategoryDataset pitchSetPointData,yawSetPointData,rollSetPointData;
	protected DefaultCategoryDataset pitchErrorData,yawErrorData,rollErrorData;
	protected JSlider pitchSlider, yawSlider, rollSlider;
	protected static int pitchtime,yawtime,rolltime;
	protected Controller controller;
	protected JTextField  newPitch, newYaw, newRoll;
	protected JButton pidButton, setPointButton, getPointButton ,setPIDButton, getPIDButton;
	protected JTextField 	newKpPitch,newKiPitch,newKdPitch,
							newKpYaw,newKiYaw,newKdYaw,
							newKpRoll,newKiRoll,newKdRoll;
	protected CategoryPlot pitchPlot,yawPlot,rollPlot;
	protected DefaultCategoryItemRenderer yawRenderer,pitchRenderer,rollRenderer;
	protected DefaultCategoryItemRenderer yawSetPointRenderer,rollSetPointRenderer,pitchSetPointRenderer;
	protected DefaultCategoryItemRenderer yawErrorRenderer,rollErrorRenderer,pitchErrorRenderer;
	
	
	public View(String title, PlatformModel model){
		super(title);
		
		pitchtime = 0;
		yawtime = 0;
		rolltime = 0;
		
		
		pitchData = new DefaultCategoryDataset();
		yawData = new DefaultCategoryDataset();
		rollData = new DefaultCategoryDataset();
		
		
		pitchSetPointData = new DefaultCategoryDataset();
		yawSetPointData = new DefaultCategoryDataset();
		rollSetPointData = new DefaultCategoryDataset();
		
		pitchErrorData = new DefaultCategoryDataset();
		yawErrorData = new DefaultCategoryDataset();
		rollErrorData = new DefaultCategoryDataset();
		
		pitchGraph = ChartFactory.createLineChart("Pitch", "Tiempo", "Grados", pitchData);
		yawGraph = ChartFactory.createLineChart("Yaw", "Tiempo", "Grados", yawData);
		rollGraph = ChartFactory.createLineChart("Roll", "Tiempo", "Grados", rollData);
		
		yawPlot= yawGraph.getCategoryPlot();
		pitchPlot= pitchGraph.getCategoryPlot();
		rollPlot= rollGraph.getCategoryPlot();
		
		pitchGraph.getLegend().setPosition(RectangleEdge.RIGHT);
		yawGraph.getLegend().setPosition(RectangleEdge.RIGHT);
		rollGraph.getLegend().setPosition(RectangleEdge.RIGHT);
//		pitchGraph.removeLegend();
//		yawGraph.removeLegend();
//		rollGraph.removeLegend();
		
		Font font = new Font("Plot", Font.PLAIN, 7);
		
		pitchPlot.getDomainAxis().setTickLabelFont(font);
		yawPlot.getDomainAxis().setTickLabelFont(font);
		rollPlot.getDomainAxis().setTickLabelFont(font);
		
		yawPlot.setDataset(1,yawSetPointData);
		pitchPlot.setDataset(1,pitchSetPointData);
		rollPlot.setDataset(1,rollSetPointData);
		
		yawPlot.setDataset(2,yawErrorData);
		pitchPlot.setDataset(2,pitchErrorData);
		rollPlot.setDataset(2,rollErrorData);
		
		yawRenderer= new DefaultCategoryItemRenderer();
		yawRenderer.setSeriesShapesVisible(0, false);
		pitchRenderer= new DefaultCategoryItemRenderer();
		pitchRenderer.setSeriesShapesVisible(0, false);
		rollRenderer= new DefaultCategoryItemRenderer();
		rollRenderer.setSeriesShapesVisible(0, false);
		yawSetPointRenderer= new DefaultCategoryItemRenderer();
		yawSetPointRenderer.setSeriesShapesVisible(0, false);
		pitchSetPointRenderer= new DefaultCategoryItemRenderer();
		pitchSetPointRenderer.setSeriesShapesVisible(0, false);
		rollSetPointRenderer= new DefaultCategoryItemRenderer();
		rollSetPointRenderer.setSeriesShapesVisible(0, false);
		yawErrorRenderer= new DefaultCategoryItemRenderer();
		yawErrorRenderer.setSeriesShapesVisible(0, false);
		pitchErrorRenderer= new DefaultCategoryItemRenderer();
		pitchErrorRenderer.setSeriesShapesVisible(0, false);
		rollErrorRenderer= new DefaultCategoryItemRenderer();
		rollErrorRenderer.setSeriesShapesVisible(0, false);
		
		yawPlot.setRenderer(0,yawRenderer);
		yawPlot.setRenderer(1,yawSetPointRenderer);
		yawPlot.setRenderer(2,yawErrorRenderer);
		pitchPlot.setRenderer(0,pitchRenderer);
		pitchPlot.setRenderer(1,pitchSetPointRenderer);
		pitchPlot.setRenderer(2,pitchErrorRenderer);
		rollPlot.setRenderer(0,rollRenderer);
		rollPlot.setRenderer(1,rollSetPointRenderer);
		rollPlot.setRenderer(2,rollErrorRenderer);
		
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
	
	public float getNewKp(GradoDeLibertad grado) throws NumberFormatException{
		switch(grado){
			case PITCH:
				return Float.parseFloat(newKpPitch.getText());
			case YAW:
				return Float.parseFloat(newKpYaw.getText());
			default:
				return Float.parseFloat(newKpRoll.getText());
		}
	}
	
	public float getNewKi(GradoDeLibertad grado) throws NumberFormatException{
		switch(grado){
			case PITCH:
				return Float.parseFloat(newKiPitch.getText());
			case YAW:
				return Float.parseFloat(newKiYaw.getText());
			default:
				return Float.parseFloat(newKiRoll.getText());
		}
	}
	
	public float getNewKd(GradoDeLibertad grado) throws NumberFormatException{
		switch(grado){
			case PITCH:
				return Float.parseFloat(newKdPitch.getText());
			case YAW:
				return Float.parseFloat(newKdYaw.getText());
			default:
				return Float.parseFloat(newKdRoll.getText());
		}
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
	
	public void updatePID(GradoDeLibertad grado, float kp,float ki,float kd){
		JTextField auxKp, auxKi, auxKd;
		switch(grado){
		case PITCH:
			auxKp = newKpPitch;
			auxKi = newKiPitch;
			auxKd = newKdPitch;
			break;
		case YAW:
			auxKp = newKpYaw;
			auxKi = newKiYaw;
			auxKd = newKdYaw;
			break;
		default:
			auxKp = newKpRoll;
			auxKi = newKiRoll;
			auxKd = newKdRoll;
			break;
	}
		auxKp.setText(Float.toString(kp));
		auxKi.setText(Float.toString(ki));
		auxKd.setText(Float.toString(kd));
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
	
	public abstract void updatePitch(float newPitch, float setPointPitch);
	public abstract void updateRoll(float newRoll, float setPointRoll);
	public abstract void updateYaw(float newYaw, float setPointYaw) ;
	public abstract void updateX(float newX);
	public abstract void updateY(float newY);
	public abstract void updateZ(float newZ);
	public abstract void updateMotorAngles(float newMotorAngles[]);

}