package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import Controller.Controller;
import Model.GradoDeLibertad;
import Model.PlatformModel;


public abstract class View extends JFrame{
	private static final long serialVersionUID = 1L;
	protected static final int displayTime = 30;
	protected JFreeChart pitchGraph,yawGraph,rollGraph;
	protected DefaultCategoryDataset pitchData,yawData,rollData;
	protected DefaultCategoryDataset pitchSetPointData,yawSetPointData,rollSetPointData;
	protected DefaultCategoryDataset pitchErrorData,yawErrorData,rollErrorData;
	protected JSlider pitchSlider, yawSlider, rollSlider;
	protected static int pitchtime,yawtime,rolltime;
	protected Controller controller;
	protected JTextField  newPitch, newYaw, newRoll;
	protected JButton pidButtonYPR, setPointButtonYPR, getPointButtonYPR ;
	protected JTextField 	newKpPitch,newKiPitch,newKdPitch,
							newKpYaw,newKiYaw,newKdYaw,
							newKpRoll,newKiRoll,newKdRoll;
	protected JTextField 	newKpX,newKiX,newKdX,
							newKpY,newKiY,newKdY,
							newKpZ,newKiZ,newKdZ;
	protected CategoryPlot pitchPlot,yawPlot,rollPlot;
	protected DefaultCategoryItemRenderer yawRenderer,pitchRenderer,rollRenderer;
	protected DefaultCategoryItemRenderer yawSetPointRenderer,rollSetPointRenderer,pitchSetPointRenderer;
	protected DefaultCategoryItemRenderer yawErrorRenderer,rollErrorRenderer,pitchErrorRenderer;
	protected JTabbedPane tabbedPane;
	protected JPanel topPanel;
	protected JPanel YPRPanel;
	protected JPanel XYZPanel;
	private JLabel	pitchText, yawText, rollText,activeYPRPIDText;
	protected Point firstVariableBase ,secondVariableBase ,thirdVariableBase ,midPoint;
	protected int alto,ancho;
	private JLabel xText, yText,zText;
	private JSlider xSlider, ySlider, zSlider;
	protected JTextField newX,newY,newZ;
	private JLabel activeXYZPIDText;
	protected JButton pidButtonXYZ, getPointButtonXYZ, setPointButtonXYZ;
	
	protected JFreeChart xGraph,yGraph,zGraph;
	protected DefaultCategoryDataset xData,yData,zData;
	protected DefaultCategoryDataset xSetPointData,ySetPointData,zSetPointData;
	protected DefaultCategoryDataset xErrorData,yErrorData,zErrorData;
	protected static int xtime,ytime,ztime;
	protected CategoryPlot xPlot,yPlot,zPlot;
	protected DefaultCategoryItemRenderer yRenderer,xRenderer,zRenderer;
	protected DefaultCategoryItemRenderer ySetPointRenderer,zSetPointRenderer,xSetPointRenderer;
	protected DefaultCategoryItemRenderer yErrorRenderer,zErrorRenderer,xErrorRenderer;
	public View(String title, PlatformModel model){
		super(title);
	}
	
	protected void createView(){
		this.setBounds(0, 0, 1200, 700);
		this.setResizable(false);
		this.setUndecorated(true);
	    alto = this.getHeight(); 
	    ancho = this.getWidth();
		topPanel= new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add( topPanel );
		
		YPRPanel = new JPanel();
		YPRPanel.setBounds(this.getBounds());
		YPRPanel.setLayout( null );
		
		XYZPanel = new JPanel();
		XYZPanel.setBounds(this.getBounds());
		XYZPanel.setLayout( null );
		
		topPanel.setBounds(this.getBounds());
		tabbedPane = new JTabbedPane();
		
		createYPRTab();
		createXYZTab();
		
		tabbedPane.addTab("YPR",YPRPanel);
		tabbedPane.addTab("XYZ",XYZPanel);
		topPanel.add(tabbedPane,BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
private void createXYZGraphs(){
	xtime = 0;
	ytime = 0;
	ztime = 0;
	
	xData = new DefaultCategoryDataset();
	yData = new DefaultCategoryDataset();
	zData = new DefaultCategoryDataset();
		
	xSetPointData = new DefaultCategoryDataset();
	ySetPointData = new DefaultCategoryDataset();
	zSetPointData = new DefaultCategoryDataset();
	
	xErrorData = new DefaultCategoryDataset();
	yErrorData = new DefaultCategoryDataset();
	zErrorData = new DefaultCategoryDataset();
	
	xGraph = ChartFactory.createLineChart("X", "Tiempo", "Grados", xData);
	yGraph = ChartFactory.createLineChart("Y", "Tiempo", "Grados", yData);
	zGraph = ChartFactory.createLineChart("Z", "Tiempo", "Grados", zData);
	
	yPlot= yGraph.getCategoryPlot();
	xPlot= xGraph.getCategoryPlot();
	zPlot= zGraph.getCategoryPlot();
	
	xGraph.getLegend().setPosition(RectangleEdge.RIGHT);
	yGraph.getLegend().setPosition(RectangleEdge.RIGHT);
	zGraph.getLegend().setPosition(RectangleEdge.RIGHT);
	
	Font font = new Font("Plot", Font.PLAIN, 7);
	
	xPlot.getDomainAxis().setTickLabelFont(font);
	yPlot.getDomainAxis().setTickLabelFont(font);
	zPlot.getDomainAxis().setTickLabelFont(font);
	
	yPlot.setDataset(1,ySetPointData);
	xPlot.setDataset(1,xSetPointData);
	zPlot.setDataset(1,zSetPointData);
	
	yPlot.setDataset(2,yErrorData);
	xPlot.setDataset(2,xErrorData);
	zPlot.setDataset(2,zErrorData);
	
	yRenderer= new DefaultCategoryItemRenderer();
	yRenderer.setSeriesShapesVisible(0, false);
	xRenderer= new DefaultCategoryItemRenderer();
	xRenderer.setSeriesShapesVisible(0, false);
	zRenderer= new DefaultCategoryItemRenderer();
	zRenderer.setSeriesShapesVisible(0, false);
	ySetPointRenderer= new DefaultCategoryItemRenderer();
	ySetPointRenderer.setSeriesShapesVisible(0, false);
	xSetPointRenderer= new DefaultCategoryItemRenderer();
	xSetPointRenderer.setSeriesShapesVisible(0, false);
	zSetPointRenderer= new DefaultCategoryItemRenderer();
	zSetPointRenderer.setSeriesShapesVisible(0, false);
	yErrorRenderer= new DefaultCategoryItemRenderer();
	yErrorRenderer.setSeriesShapesVisible(0, false);
	xErrorRenderer= new DefaultCategoryItemRenderer();
	xErrorRenderer.setSeriesShapesVisible(0, false);
	zErrorRenderer= new DefaultCategoryItemRenderer();
	zErrorRenderer.setSeriesShapesVisible(0, false);
	
	yPlot.setRenderer(0,yRenderer);
	yPlot.setRenderer(1,ySetPointRenderer);
	yPlot.setRenderer(2,yErrorRenderer);
	xPlot.setRenderer(0,xRenderer);
	xPlot.setRenderer(1,xSetPointRenderer);
	xPlot.setRenderer(2,xErrorRenderer);
	zPlot.setRenderer(0,zRenderer);
	zPlot.setRenderer(1,zSetPointRenderer);
	zPlot.setRenderer(2,zErrorRenderer);
}
private void createYPRGraphs(){
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
}
protected void createYPRTab(){
		createYPRGraphs();
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.lightGray);
		
		pidButtonYPR = new JButton("PID");
		setPointButtonYPR = new JButton("Set Point");
		getPointButtonYPR = new JButton("Get Point");

		newPitch = new JTextField();
		newYaw = new JTextField();
		newRoll = new JTextField();
		
		JPanel	pitchPanel = new ChartPanel(pitchGraph),
				yawPanel = new ChartPanel(yawGraph),
				rollPanel = new ChartPanel(rollGraph);
		
		pitchPanel.setBounds(0, 0,  (int)(ancho*2/3), alto/3);
		yawPanel.setBounds(0, alto/3, (int)(ancho*2/3), alto/3);
		rollPanel.setBounds(0, 2*alto/3, (int)(ancho*2/3), alto/3);
		
		firstVariableBase = new Point(ancho * 6/9, 0);
		secondVariableBase = new Point(ancho * 7/9, 0);
		thirdVariableBase = new Point(ancho * 8/9,0);
		midPoint = firstVariableBase;
		
		pitchText = new JLabel("Pitch:");
		yawText = new JLabel("Yaw:");
		rollText = new JLabel("Roll:");
		
		pitchText.setBounds(firstVariableBase.x + 5, firstVariableBase.y , 100, 30);
		yawText.setBounds(secondVariableBase.x + 5, secondVariableBase.y, 100, 30);
		rollText.setBounds(thirdVariableBase.x + 5, thirdVariableBase.y, 100, 30);
		
		pitchSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		yawSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		rollSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		
		pitchSlider.setBounds(firstVariableBase.x + 50, pitchText.getY() + pitchText.getHeight() + 5, 50, 250);
		yawSlider.setBounds(secondVariableBase.x + 50, yawText.getY() + yawText.getHeight() + 5, 50, 250);
		rollSlider.setBounds(thirdVariableBase.x + 50, rollText.getY() + rollText.getHeight() + 5, 50, 250);
		
		pitchSlider.setMajorTickSpacing(1);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setPaintLabels(true);
		
		yawSlider.setMajorTickSpacing(1);
		yawSlider.setPaintTicks(true);
		yawSlider.setPaintLabels(true);
		
		rollSlider.setMajorTickSpacing(1);
		rollSlider.setPaintTicks(true);
		rollSlider.setPaintLabels(true);
		
		java.util.Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
		for(int i=-10;i<=10;i+=2){
			labelTable.put(new Integer(i*10), new JLabel(Integer.toString(i)));
		}
		
		pitchSlider.setLabelTable(labelTable);
		yawSlider.setLabelTable(labelTable);
		rollSlider.setLabelTable(labelTable);
		
		pitchSlider.setBackground(Color.lightGray);
		yawSlider.setBackground(Color.lightGray);
		rollSlider.setBackground(Color.lightGray);
		
		newPitch.setBounds(firstVariableBase.x + 10,pitchSlider.getY() + pitchSlider.getHeight() + 10 , 100, 30);
		newYaw.setBounds(secondVariableBase.x +10 , yawSlider.getY() + yawSlider.getHeight() + 10, 100, 30);
		newRoll.setBounds(thirdVariableBase.x +10,rollSlider.getY() + rollSlider.getHeight() + 10, 100, 30);
		
		newPitch.setText("0.0");
		newYaw.setText("0.0");
		newRoll.setText("0.0");
		
		newKpPitch = new JTextField();
		newKiPitch = new JTextField();
		newKdPitch = new JTextField();
		newKpYaw = new JTextField();
		newKiYaw = new JTextField();
		newKdYaw = new JTextField();
		newKpRoll = new JTextField();
		newKiRoll = new JTextField();
		newKdRoll = new JTextField();
		
		activeYPRPIDText = new JLabel("PID desactivado");		
		
		JSeparator 	separador1 = new JSeparator(SwingConstants.HORIZONTAL),
					separador2 = new JSeparator(SwingConstants.VERTICAL),
					separador3 = new JSeparator(SwingConstants.VERTICAL);
		separador1.setBounds(ancho/2, newPitch.getY() + 40, ancho/2, 1);
		separador2.setBounds(secondVariableBase.x, 0, 1, separador1.getY());
		separador3.setBounds(thirdVariableBase.x, 0, 1, separador1.getY());
		fondo.add(separador1);
		fondo.add(separador2);
		fondo.add(separador3);
		
		pidButtonYPR.setBounds(midPoint.x + 10, separador1.getY() + 10, 100, 30);
		activeYPRPIDText.setBounds(pidButtonYPR.getX() + pidButtonYPR.getWidth() + 10, pidButtonYPR.getY(), 150,30);
		
		getPointButtonYPR.setBounds(midPoint.x + 10, pidButtonYPR.getY() + pidButtonYPR.getHeight() + 10, 100, 30);
		setPointButtonYPR.setBounds(getPointButtonYPR.getX() + getPointButtonYPR.getWidth() + 10, getPointButtonYPR.getY(), 100, 30);
		
		YPRPanel.add(pitchPanel);
		YPRPanel.add(yawPanel);
		YPRPanel.add(rollPanel);
		YPRPanel.add(newPitch);
		YPRPanel.add(newYaw);
		YPRPanel.add(newRoll);
		YPRPanel.add(pidButtonYPR);
		YPRPanel.add(setPointButtonYPR);
		YPRPanel.add(getPointButtonYPR);

		YPRPanel.add(pitchText);
		YPRPanel.add(yawText);
		YPRPanel.add(rollText);
		YPRPanel.add(pitchSlider);
		YPRPanel.add(yawSlider);
		YPRPanel.add(rollSlider);
		YPRPanel.add(newKpPitch);
		YPRPanel.add(newKiPitch);
		YPRPanel.add(newKdPitch);
		YPRPanel.add(newKpYaw);
		YPRPanel.add(newKiYaw);
		YPRPanel.add(newKdYaw);
		YPRPanel.add(newKpRoll);
		YPRPanel.add(newKiRoll);
		YPRPanel.add(newKdRoll);

		YPRPanel.add(activeYPRPIDText);
		YPRPanel.add(separador1);
		YPRPanel.add(separador2);
		YPRPanel.add(separador3);	
		YPRPanel.add(fondo);
		
		
		pitchSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                newPitch.setText(String.valueOf(pitchSlider.getValue()/10.));
            }
        });
		
        newPitch.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
            	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
	                String typed = newPitch.getText();
	                System.out.println("ENTER PITCH: "+newPitch.getText());
	                try{
		                float value = Float.parseFloat(typed);
		                pitchSlider.setValue(Math.round(value*10));
	                }catch(NumberFormatException ex){
	                	ex.printStackTrace();
	                }
            	}else System.out.println("NO ENTER");
            }
        });
        
        yawSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                newYaw.setText(String.valueOf(yawSlider.getValue()/10.));
            }
        });
		
        newYaw.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
            	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
	                String typed = newYaw.getText();
	                System.out.println("ENTER YAW: "+newYaw.getText());
	                try{
		                float value = Float.parseFloat(typed);
		                yawSlider.setValue(Math.round(value*10));
	                }catch(NumberFormatException ex){
	                	ex.printStackTrace();
	                }
            	}else System.out.println("NO ENTER");
            }
        });
        
        rollSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                newRoll.setText(String.valueOf(rollSlider.getValue()/10.));
            }
        });
		
        newRoll.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
            	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
	                String typed = newRoll.getText();
	                System.out.println("ENTER ROLL: "+newRoll.getText());
	                try{
		                float value = Float.parseFloat(typed);
		                rollSlider.setValue(Math.round(value*10));
	                }catch(NumberFormatException ex){
	                	ex.printStackTrace();
	                }
            	}else System.out.println("NO ENTER");
            }
        });
	
	}

protected void createXYZTab(){	
	createXYZGraphs();
	JPanel fondo = new JPanel();
	fondo.setBackground(Color.lightGray);
	
	pidButtonXYZ = new JButton("PID");
	setPointButtonXYZ = new JButton("Set Point");
	getPointButtonXYZ = new JButton("Get Point");

	newX = new JTextField();
	newY = new JTextField();
	newZ = new JTextField();
	
	JPanel	xPanel = new ChartPanel(xGraph),
			yPanel = new ChartPanel(yGraph),
			zPanel = new ChartPanel(zGraph);
	
	xPanel.setBounds(0, 0,  (int)(ancho*2/3), alto/3);
	yPanel.setBounds(0, alto/3, (int)(ancho*2/3), alto/3);
	zPanel.setBounds(0, 2*alto/3, (int)(ancho*2/3), alto/3);
	
	firstVariableBase = new Point(ancho * 6/9, 0);
	secondVariableBase = new Point(ancho * 7/9, 0);
	thirdVariableBase = new Point(ancho * 8/9,0);
	midPoint = firstVariableBase;
	
	xText = new JLabel("X:");
	yText = new JLabel("Y:");
	zText = new JLabel("Z:");
	
	xText.setBounds(firstVariableBase.x + 5, firstVariableBase.y , 100, 30);
	yText.setBounds(secondVariableBase.x + 5, secondVariableBase.y, 100, 30);
	zText.setBounds(thirdVariableBase.x + 5, thirdVariableBase.y, 100, 30);
	
	xSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
	ySlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
	zSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
	
	xSlider.setBounds(firstVariableBase.x + 50, xText.getY() + xText.getHeight() + 5, 50, 250);
	ySlider.setBounds(secondVariableBase.x + 50, yText.getY() + yText.getHeight() + 5, 50, 250);
	zSlider.setBounds(thirdVariableBase.x + 50, zText.getY() + zText.getHeight() + 5, 50, 250);
	
	xSlider.setMajorTickSpacing(1);
	xSlider.setPaintTicks(true);
	xSlider.setPaintLabels(true);
	
	ySlider.setMajorTickSpacing(1);
	ySlider.setPaintTicks(true);
	ySlider.setPaintLabels(true);
	
	zSlider.setMajorTickSpacing(1);
	zSlider.setPaintTicks(true);
	zSlider.setPaintLabels(true);
	
	java.util.Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<Integer,JLabel>();
	for(int i=-10;i<=10;i+=2){
		labelTable.put(new Integer(i*10), new JLabel(Integer.toString(i)));
	}
	
	xSlider.setLabelTable(labelTable);
	ySlider.setLabelTable(labelTable);
	zSlider.setLabelTable(labelTable);
	
	xSlider.setBackground(Color.lightGray);
	ySlider.setBackground(Color.lightGray);
	zSlider.setBackground(Color.lightGray);
	
	newX.setBounds(firstVariableBase.x + 10,xSlider.getY() + xSlider.getHeight() + 10 , 100, 30);
	newY.setBounds(secondVariableBase.x +10 , ySlider.getY() + ySlider.getHeight() + 10, 100, 30);
	newZ.setBounds(thirdVariableBase.x +10,zSlider.getY() + zSlider.getHeight() + 10, 100, 30);
	
	newX.setText("0.0");
	newY.setText("0.0");
	newZ.setText("0.0");
	
	newKpX = new JTextField();
	newKiX = new JTextField();
	newKdX = new JTextField();
	newKpY = new JTextField();
	newKiY = new JTextField();
	newKdY = new JTextField();
	newKpZ = new JTextField();
	newKiZ = new JTextField();
	newKdZ = new JTextField();
	
	activeXYZPIDText = new JLabel("PID desactivado");		
	
	JSeparator 	separador1 = new JSeparator(SwingConstants.HORIZONTAL),
				separador2 = new JSeparator(SwingConstants.VERTICAL),
				separador3 = new JSeparator(SwingConstants.VERTICAL);
	separador1.setBounds(ancho/2, newX.getY() + 40, ancho/2, 1);
	separador2.setBounds(secondVariableBase.x, 0, 1, separador1.getY());
	separador3.setBounds(thirdVariableBase.x, 0, 1, separador1.getY());
	fondo.add(separador1);
	fondo.add(separador2);
	fondo.add(separador3);
	
	pidButtonXYZ.setBounds(midPoint.x + 10, separador1.getY() + 10, 100, 30);
	activeXYZPIDText.setBounds(pidButtonXYZ.getX() + pidButtonXYZ.getWidth() + 10, pidButtonXYZ.getY(), 150,30);
	
	getPointButtonXYZ.setBounds(midPoint.x + 10, pidButtonXYZ.getY() + pidButtonYPR.getHeight() + 10, 100, 30);
	setPointButtonXYZ.setBounds(getPointButtonXYZ.getX() + getPointButtonXYZ.getWidth() + 10, getPointButtonXYZ.getY(), 100, 30);
	
	XYZPanel.add(xPanel);
	XYZPanel.add(yPanel);
	XYZPanel.add(zPanel);
	XYZPanel.add(newX);
	XYZPanel.add(newY);
	XYZPanel.add(newZ);
	XYZPanel.add(pidButtonXYZ);
	XYZPanel.add(setPointButtonXYZ);
	XYZPanel.add(getPointButtonXYZ);

	XYZPanel.add(xText);
	XYZPanel.add(yText);
	XYZPanel.add(zText);
	XYZPanel.add(xSlider);
	XYZPanel.add(ySlider);
	XYZPanel.add(zSlider);
	XYZPanel.add(newKpX);
	XYZPanel.add(newKiX);
	XYZPanel.add(newKdX);
	XYZPanel.add(newKpY);
	XYZPanel.add(newKiY);
	XYZPanel.add(newKdY);
	XYZPanel.add(newKpZ);
	XYZPanel.add(newKiZ);
	XYZPanel.add(newKdZ);

	XYZPanel.add(activeXYZPIDText);
	XYZPanel.add(separador1);
	XYZPanel.add(separador2);
	XYZPanel.add(separador3);	
	XYZPanel.add(fondo);
	
	
	xSlider.addChangeListener(new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            newX.setText(String.valueOf(xSlider.getValue()/10.));
        }
    });
	
    newX.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent ke) {
        	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                String typed = newX.getText();
                System.out.println("ENTER X: "+newX.getText());
                try{
	                float value = Float.parseFloat(typed);
	                xSlider.setValue(Math.round(value*10));
                }catch(NumberFormatException ex){
                	ex.printStackTrace();
                }
        	}else System.out.println("NO ENTER");
        }
    });
    
    ySlider.addChangeListener(new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            newY.setText(String.valueOf(ySlider.getValue()/10.));
        }
    });
	
    newY.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent ke) {
        	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                String typed = newY.getText();
                System.out.println("ENTER Y: "+newY.getText());
                try{
	                float value = Float.parseFloat(typed);
	                ySlider.setValue(Math.round(value*10));
                }catch(NumberFormatException ex){
                	ex.printStackTrace();
                }
        	}else System.out.println("NO ENTER");
        }
    });
    
    zSlider.addChangeListener(new ChangeListener(){
        @Override
        public void stateChanged(ChangeEvent e) {
            newZ.setText(String.valueOf(zSlider.getValue()/10.));
        }
    });
	
    newZ.addKeyListener(new KeyAdapter(){
        @Override
        public void keyReleased(KeyEvent ke) {
        	if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                String typed = newZ.getText();
                System.out.println("ENTER Z: "+newZ.getText());
                try{
	                float value = Float.parseFloat(typed);
	                zSlider.setValue(Math.round(value*10));
                }catch(NumberFormatException ex){
                	ex.printStackTrace();
                }
        	}else System.out.println("NO ENTER");
        }
    });

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
		pidButtonYPR.addActionListener(listener);
		pidButtonXYZ.addActionListener(listener);
	}
	
	public void addSetPointButtonListener(ActionListener listener){
		setPointButtonYPR.addActionListener(listener);
		setPointButtonXYZ.addActionListener(listener);
	}
	
	public void addGetPointButtonListener(ActionListener listener){
		getPointButtonYPR.addActionListener(listener);
		getPointButtonXYZ.addActionListener(listener);
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
	
	public void updatePos(float pitch,float yaw, float roll, float x, float y, float z){
		newPitch.setText(Float.toString(pitch));
		newYaw.setText(Float.toString(yaw));
		newRoll.setText(Float.toString(roll));
		newX.setText(Float.toString(x));
		newY.setText(Float.toString(y));
		newZ.setText(Float.toString(z));
		
		pitchSlider.setValue((int)(pitch*10));
		yawSlider.setValue((int)(yaw*10));
		rollSlider.setValue((int)(roll*10));
		xSlider.setValue((int)(x*10));
		ySlider.setValue((int)(y*10));
		zSlider.setValue((int)(z*10));
	}


	
	public void togglePID(boolean state){
		activeYPRPIDText.setText(state ? "PID Activado" : "PID Desactivado");
	}
	public void updatePitch(float newPitch, float setPointPitch){
		if(pitchtime>displayTime){
			pitchData.removeValue("pitch", (Integer)(pitchtime-displayTime));
			pitchSetPointData.removeValue("setPoint", (Integer)(pitchtime-displayTime));
			pitchErrorData.removeValue("Error", (Integer)(pitchtime-displayTime));
		
		}
		pitchData.addValue(newPitch, "pitch", (Integer)(++pitchtime));
		pitchSetPointData.addValue(setPointPitch, "setPoint", (Integer)(pitchtime));
		pitchErrorData.addValue(setPointPitch-newPitch,"Error",(Integer)(pitchtime));
	}
	public void updateRoll(float newRoll, float setPointRoll){
		if(rolltime>displayTime){
			rollData.removeValue("roll", (Integer)(rolltime-displayTime));
			rollSetPointData.removeValue("setPoint", (Integer)(rolltime-displayTime));
			rollErrorData.removeValue("Error", (Integer)(rolltime-displayTime));
		}
		rollData.addValue(newRoll, "roll", (Integer)(++rolltime));
		rollSetPointData.addValue(setPointRoll, "setPoint", (Integer)(rolltime));
		rollErrorData.addValue(setPointRoll-newRoll,"Error",(Integer)(rolltime));
	}
	
	public void updateYaw(float newYaw, float setPointYaw){
		if(yawtime>displayTime){
			yawData.removeValue("yaw", (Integer)(yawtime-displayTime));
			yawSetPointData.removeValue("setPoint", (Integer)(yawtime-displayTime));
			yawErrorData.removeValue("Error", (Integer)(yawtime-displayTime));
		}
		yawData.addValue(newYaw, "yaw", (Integer)(++yawtime));
		yawSetPointData.addValue(setPointYaw, "setPoint", (Integer)(yawtime));
		yawErrorData.addValue(setPointYaw-newYaw,"Error",(Integer)(yawtime));
	}
	public void updateX(float newX, float setPointX){
		if(xtime>displayTime){
			xData.removeValue("x", (Integer)(xtime-displayTime));
			xSetPointData.removeValue("setPoint", (Integer)(xtime-displayTime));
			xErrorData.removeValue("Error", (Integer)(xtime-displayTime));
		
		}
		xData.addValue(newX, "x", (Integer)(++xtime));
		xSetPointData.addValue(setPointX, "setPoint", (Integer)(xtime));
		xErrorData.addValue(setPointX-newX,"Error",(Integer)(xtime));
	}
	public void updateY(float newY, float setPointY){
		if(ytime>displayTime){
			yData.removeValue("y", (Integer)(ytime-displayTime));
			ySetPointData.removeValue("setPoint", (Integer)(ytime-displayTime));
			yErrorData.removeValue("Error", (Integer)(ytime-displayTime));
		}
		yData.addValue(newY, "y", (Integer)(++ytime));
		ySetPointData.addValue(setPointY, "setPoint", (Integer)(ytime));
		yErrorData.addValue(setPointY-newY,"Error",(Integer)(ytime));
	
	}
	public void updateZ(float newZ, float setPointZ){
		if(ztime>displayTime){
			zData.removeValue("z", (Integer)(ztime-displayTime));
			zSetPointData.removeValue("setPoint", (Integer)(ztime-displayTime));
			zErrorData.removeValue("Error", (Integer)(ztime-displayTime));
		}
		zData.addValue(newZ, "z", (Integer)(++ztime));
		zSetPointData.addValue(setPointZ, "setPoint", (Integer)(ztime));
		zErrorData.addValue(setPointZ-newZ,"Error",(Integer)(ztime));
		
	}
	public abstract void updateMotorAngles(float newMotorAngles[]);

	public abstract void addSetPIDButtonListener(ActionListener listener);

	public abstract void addGetPIDButtonListener(ActionListener listener);

	public float getNewX() {
		return Float.parseFloat(newX.getText());
	}

	public float getNewY() {
		return Float.parseFloat(newY.getText());
	}

	public float getNewZ() {
		return Float.parseFloat(newZ.getText());
	}

}