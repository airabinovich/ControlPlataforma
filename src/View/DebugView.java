package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;

import Model.PlatformModel;

public class DebugView extends View {
	private static final int displayTime = 10;
	private static final long serialVersionUID = 1L;
	private Container contentpane;
	private JLabel	pitchText, yawText, rollText,
					kpPitchText, kiPitchText, kdPitchText,
					kpYawText, kiYawText, kdYawText,
					kpRollText, kiRollText, kdRollText,
					activePIDText;
	
	private JLabel motorText[];
	private JTextField motorValue[];
	public DebugView(PlatformModel model) {
		
		super("Control de Plataforma - Debug Mode", model);
		
		motorText = new JLabel[6];
		motorValue = new JTextField[6];
		for(int i = 0; i < 6; i++){
			motorText[i] = new JLabel("M"+Integer.toString(i+1)+" =");
			motorValue[i] = new JTextField("0°");
		}
		
		createView();
	}

	protected void createView() {
		super.createView();
		contentpane = this.getContentPane();
		
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.lightGray);
		
		this.setResizable(false);
		this.setBounds(200, 50, 1000, 700);
		
		int alto = this.getHeight(), ancho = this.getWidth();
	
		JPanel	pitchPanel = new ChartPanel(pitchGraph),
				yawPanel = new ChartPanel(yawGraph),
				rollPanel = new ChartPanel(rollGraph);
		
		pitchPanel.setBounds(0, 0, ancho/2, alto/3);
		yawPanel.setBounds(0, alto/3, ancho/2, alto/3);
		rollPanel.setBounds(0, 2*alto/3, ancho/2, alto/3);
		
		Point 	pitchBase = new Point(ancho * 3/6, 0),
				yawBase = new Point(ancho * 4/6, 0),
				rollBase = new Point(ancho * 5/6,0),
				midPoint = pitchBase;
		
		pitchText = new JLabel("Pitch:");
		yawText = new JLabel("Yaw:");
		rollText = new JLabel("Roll:");
		
		pitchText.setBounds(pitchBase.x + 5, pitchBase.y , 100, 30);
		yawText.setBounds(yawBase.x + 5, yawBase.y, 100, 30);
		rollText.setBounds(rollBase.x + 5, rollBase.y, 100, 30);
		
		pitchSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		yawSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		rollSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		
		pitchSlider.setBounds(pitchBase.x + 50, pitchText.getY() + pitchText.getHeight() + 5, 50, 250);
		yawSlider.setBounds(yawBase.x + 50, yawText.getY() + yawText.getHeight() + 5, 50, 250);
		rollSlider.setBounds(rollBase.x + 50, rollText.getY() + rollText.getHeight() + 5, 50, 250);
		
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
		
		newPitch.setBounds(pitchBase.x + 10,pitchSlider.getY() + pitchSlider.getHeight() + 10 , 100, 30);
		newYaw.setBounds(yawBase.x +10 , yawSlider.getY() + yawSlider.getHeight() + 10, 100, 30);
		newRoll.setBounds(rollBase.x +10,rollSlider.getY() + rollSlider.getHeight() + 10, 100, 30);
		
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
		
		kpPitchText = new JLabel("Kp = ");
		kiPitchText = new JLabel("Ki = ");
		kdPitchText = new JLabel("Kd = ");
		kpYawText = new JLabel("Kp = ");
		kiYawText = new JLabel("Ki = ");
		kdYawText = new JLabel("Kd = ");
		kpRollText = new JLabel("Kp = ");
		kiRollText= new JLabel("Ki = ");
		kdRollText = new JLabel("Kd = ");
		activePIDText = new JLabel("PID desactivado");		
		
		kpPitchText.setBounds(pitchBase.x + 10, newPitch.getY() + newPitch.getHeight() + 10 , 50, 30);
		kiPitchText.setBounds(pitchBase.x + 10, kpPitchText.getY() + kpPitchText.getHeight() + 10 , 50, 30);
		kdPitchText.setBounds(pitchBase.x + 10, kiPitchText.getY() + kiPitchText.getHeight() + 10 , 50, 30);
		
		kpYawText.setBounds(yawBase.x + 10, newYaw.getY() + newYaw.getHeight() + 10 , 50, 30);
		kiYawText.setBounds(yawBase.x + 10, kpYawText.getY() + kpYawText.getHeight() + 10 , 50, 30);
		kdYawText.setBounds(yawBase.x + 10, kiYawText.getY() + kiYawText.getHeight() + 10 , 50, 30);
		
		kpRollText.setBounds(rollBase.x + 10, newRoll.getY() + newRoll.getHeight() + 10 , 50, 30);
		kiRollText.setBounds(rollBase.x + 10, kpRollText.getY() + kpRollText.getHeight() + 10 , 50, 30);
		kdRollText.setBounds(rollBase.x + 10, kiRollText.getY() + kiRollText.getHeight() + 10 , 50, 30);
		
		newKpPitch.setBounds(kpPitchText.getX() + kpPitchText.getWidth(), kpPitchText.getY(), 50, 30);
		newKiPitch.setBounds(kiPitchText.getX() + kiPitchText.getWidth(), kiPitchText.getY(), 50, 30);
		newKdPitch.setBounds(kdPitchText.getX() + kdPitchText.getWidth(), kdPitchText.getY(), 50, 30);
		
		newKpYaw.setBounds(kpYawText.getX() + kpYawText.getWidth(), kpYawText.getY(), 50, 30);
		newKiYaw.setBounds(kiYawText.getX() + kiYawText.getWidth(), kiYawText.getY(), 50, 30);
		newKdYaw.setBounds(kdYawText.getX() + kdYawText.getWidth(), kdYawText.getY(), 50, 30);
		
		newKpRoll.setBounds(kpRollText.getX() + kpRollText.getWidth(), kpRollText.getY(), 50, 30);
		newKiRoll.setBounds(kiRollText.getX() + kiRollText.getWidth(), kiRollText.getY(), 50, 30);
		newKdRoll.setBounds(kdRollText.getX() + kdRollText.getWidth(), kdRollText.getY(), 50, 30);
		
		newKpPitch.setText("1.0");
		newKiPitch.setText("0.0");
		newKdPitch.setText("0.0");
		
		newKpYaw.setText("1.0");
		newKiYaw.setText("0.0");
		newKdYaw.setText("0.0");
		
		newKpRoll.setText("1.0");
		newKiRoll.setText("0.0");
		newKdRoll.setText("0.0");
		
		JSeparator 	separador1 = new JSeparator(SwingConstants.HORIZONTAL),
					separador2 = new JSeparator(SwingConstants.VERTICAL),
					separador3 = new JSeparator(SwingConstants.VERTICAL);
		separador1.setBounds(ancho/2, newKdPitch.getY() +  newKdPitch.getHeight() + 10, ancho/2, 1);
		separador2.setBounds(yawBase.x, 0, 1, separador1.getY());
		separador3.setBounds(rollBase.x, 0, 1, separador1.getY());
		fondo.add(separador1);
		fondo.add(separador2);
		fondo.add(separador3);
		
		pidButton.setBounds(midPoint.x + 10, separador1.getY() + 10, 100, 30);
		activePIDText.setBounds(pidButton.getX() + pidButton.getWidth() + 10, pidButton.getY(), 150,30);
		
		getPointButton.setBounds(midPoint.x + 10, pidButton.getY() + pidButton.getHeight() + 10, 100, 30);
		setPointButton.setBounds(getPointButton.getX() + getPointButton.getWidth() + 10, getPointButton.getY(), 100, 30);
		
		getPIDButton.setBounds(midPoint.x + 10, getPointButton.getY() + getPointButton.getHeight() + 10, 100, 30);
		setPIDButton.setBounds(getPIDButton.getX() + getPIDButton.getWidth() + 10, getPIDButton.getY(), 100, 30);
		/////////////////////////////////////////////////
		
		Point motorLabelsRef = new Point(ancho * 13/16, separador1.getY() + 10);
		motorText[0].setBounds(motorLabelsRef.x, motorLabelsRef.y, 50, 30);
		motorValue[0].setBounds(motorText[0].getX() + motorText[0].getWidth(), motorText[0].getY(), 100, 30);
		contentpane.add(motorText[0]);
		contentpane.add(motorValue[0]);
		for(int i = 1; i< motorText.length ; i++){
			motorText[i].setBounds(motorText[i-1].getX(),motorText[i-1].getY()+motorText[i-1].getHeight()+10, 50, 30);
			motorValue[i].setBounds(motorText[i].getX() + motorText[i].getWidth(), motorText[i].getY(), 100, 30);
			contentpane.add(motorText[i]);
			contentpane.add(motorValue[i]);
		}
		
		/////////////////////////////////////////////////
		contentpane.add(pitchPanel);
		contentpane.add(yawPanel);
		contentpane.add(rollPanel);
		contentpane.add(newPitch);
		contentpane.add(newYaw);
		contentpane.add(newRoll);
		contentpane.add(pidButton);
		contentpane.add(setPointButton);
		contentpane.add(getPointButton);
		contentpane.add(setPIDButton);
		contentpane.add(getPIDButton);
		contentpane.add(pitchText);
		contentpane.add(yawText);
		contentpane.add(rollText);
		contentpane.add(pitchSlider);
		contentpane.add(yawSlider);
		contentpane.add(rollSlider);
		contentpane.add(newKpPitch);
		contentpane.add(newKiPitch);
		contentpane.add(newKdPitch);
		contentpane.add(newKpYaw);
		contentpane.add(newKiYaw);
		contentpane.add(newKdYaw);
		contentpane.add(newKpRoll);
		contentpane.add(newKiRoll);
		contentpane.add(newKdRoll);
		contentpane.add(kpPitchText);
		contentpane.add(kiPitchText);
		contentpane.add(kdPitchText);
		contentpane.add(kpYawText);
		contentpane.add(kiYawText);
		contentpane.add(kdYawText);
		contentpane.add(kpRollText);
		contentpane.add(kiRollText);
		contentpane.add(kdRollText);
		contentpane.add(activePIDText);
		contentpane.add(separador1);
		contentpane.add(separador2);
		contentpane.add(separador3);
		
		contentpane.add(fondo);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
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

	@Override
	public void updatePitch(float newPitch, float setPointPitch) {
		if(pitchtime>displayTime) pitchData.removeValue("pitch", (Integer)(pitchtime-displayTime));
		pitchData.addValue(newPitch, "pitch", (Integer)(++pitchtime));
		pitchSetPointData.addValue(setPointPitch, "setPoint", (Integer)(pitchtime));

	}

	@Override
	public void updateRoll(float newRoll, float setPointRoll) {
		if(rolltime>displayTime) rollData.removeValue("roll", (Integer)(rolltime-displayTime));
		rollData.addValue(newRoll, "roll", (Integer)(++rolltime));
		rollSetPointData.addValue(setPointRoll, "setPoint", (Integer)(rolltime));
	}

	@Override
	public void updateYaw(float newYaw, float setPointYaw) {
		if(yawtime>displayTime) yawData.removeValue("yaw", (Integer)(yawtime-displayTime));
		yawData.addValue(newYaw, "yaw", (Integer)(++yawtime));
		yawSetPointData.addValue(setPointYaw, "setPoint", (Integer)(yawtime));
	}

	@Override
	public void updateX(float newX) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateY(float newY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateZ(float newZ) {
		// TODO Auto-generated method stub

	}

	
	public void togglePID(boolean state){
		activePIDText.setText(state ? "PID Activado" : "PID Desactivado");
	}
	
	public void updatePos(float pitch,float yaw, float roll){
		pitchSlider.setValue((int)(pitch*10));
		yawSlider.setValue((int)(yaw*10));
		rollSlider.setValue((int)(roll*10));
	}
	
}
