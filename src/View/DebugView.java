package View;


import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Model.PlatformModel;

public class DebugView extends View {

	private static final long serialVersionUID = 1L;
	private JLabel	
					kpPitchText, kiPitchText, kdPitchText,
					kpYawText, kiYawText, kdYawText,
					kpRollText, kiRollText, kdRollText;
	
	private JLabel	
					kpXText, kiXText, kdXText,
					kpYText, kiYText, kdYText,
					kpZText, kiZText, kdZText;

	
	private JLabel motorTextYPR[];
	private JTextField motorValueYPR[];
	private JLabel motorTextXYZ[];
	private JTextField motorValueXYZ[];
	private JButton getPIDButton;
	private JButton setPIDButton;
	public DebugView(PlatformModel model) {
		
		super("Control de Plataforma - Debug Mode", model);
		
		motorTextYPR = new JLabel[6];
		motorValueYPR = new JTextField[6];
		motorTextXYZ = new JLabel[6];
		motorValueXYZ = new JTextField[6];
		for(int i = 0; i < 6; i++){
			motorTextYPR[i] = new JLabel("M"+Integer.toString(i+1)+" =");
			motorValueYPR[i] = new JTextField("0°");
			motorTextXYZ[i] = new JLabel("M"+Integer.toString(i+1)+" =");
			motorValueXYZ[i] = new JTextField("0°");
		}
		
		createView();
	}

	protected void createView() {
		super.createView();
		
		
	}
	protected void createYPRTab(){
		super.createYPRTab();
		kpPitchText = new JLabel("Kp = ");
		kiPitchText = new JLabel("Ki = ");
		kdPitchText = new JLabel("Kd = ");
		kpYawText = new JLabel("Kp = ");
		kiYawText = new JLabel("Ki = ");
		kdYawText = new JLabel("Kd = ");
		kpRollText = new JLabel("Kp = ");
		kiRollText= new JLabel("Ki = ");
		kdRollText = new JLabel("Kd = ");
		setPIDButton = new JButton("Set PID");
		getPIDButton = new JButton("Get PID");
		
		getPIDButton.setBounds(midPoint.x + 10, getPointButtonYPR.getY() + getPointButtonYPR.getHeight() + 10, 100, 30);
		setPIDButton.setBounds(getPIDButton.getX() + getPIDButton.getWidth() + 10, getPIDButton.getY(), 100, 30);
		
		kpPitchText.setBounds(firstVariableBase.x + 10, getPIDButton.getY() + getPIDButton.getHeight() + 10 , 50, 30);
		kiPitchText.setBounds(firstVariableBase.x + 10, kpPitchText.getY() + kpPitchText.getHeight() + 10 , 50, 30);
		kdPitchText.setBounds(firstVariableBase.x + 10, kiPitchText.getY() + kiPitchText.getHeight() + 10 , 50, 30);
		
		kpYawText.setBounds(secondVariableBase.x + 10, kpPitchText.getY(), 50, 30);
		kiYawText.setBounds(secondVariableBase.x + 10, kpYawText.getY() + kpYawText.getHeight() + 10 , 50, 30);
		kdYawText.setBounds(secondVariableBase.x + 10, kiYawText.getY() + kiYawText.getHeight() + 10 , 50, 30);
		
		kpRollText.setBounds(thirdVariableBase.x + 10, kpPitchText.getY(), 50, 30);
		kiRollText.setBounds(thirdVariableBase.x + 10, kpRollText.getY() + kpRollText.getHeight() + 10 , 50, 30);
		kdRollText.setBounds(thirdVariableBase.x + 10, kiRollText.getY() + kiRollText.getHeight() + 10 , 50, 30);
		
		newKpPitch.setBounds(kpPitchText.getX() + kpPitchText.getWidth(), kpPitchText.getY(), 50, 30);
		newKiPitch.setBounds(kiPitchText.getX() + kiPitchText.getWidth(), kiPitchText.getY(), 50, 30);
		newKdPitch.setBounds(kdPitchText.getX() + kdPitchText.getWidth(), kdPitchText.getY(), 50, 30);
		
		newKpYaw.setBounds(kpYawText.getX() + kpYawText.getWidth(), kpYawText.getY(), 50, 30);
		newKiYaw.setBounds(kiYawText.getX() + kiYawText.getWidth(), kiYawText.getY(), 50, 30);
		newKdYaw.setBounds(kdYawText.getX() + kdYawText.getWidth(), kdYawText.getY(), 50, 30);
		
		newKpRoll.setBounds(kpRollText.getX() + kpRollText.getWidth(), kpRollText.getY(), 50, 30);
		newKiRoll.setBounds(kiRollText.getX() + kiRollText.getWidth(), kiRollText.getY(), 50, 30);
		newKdRoll.setBounds(kdRollText.getX() + kdRollText.getWidth(), kdRollText.getY(), 50, 30);
		
		newKpPitch.setText("0.1");
		newKiPitch.setText("0.0");
		newKdPitch.setText("0.0");
		
		newKpYaw.setText("0.1");
		newKiYaw.setText("0.0");
		newKdYaw.setText("0.0");
		
		newKpRoll.setText("0.1");
		newKiRoll.setText("0.0");
		newKdRoll.setText("0.0");
		
		/////////////////////////////////////////////////
		
		for(int i = 0; i< motorTextYPR.length ; i++){
			float xPosIndex=i;
			if((i%2)==0){
				motorTextYPR[i].setBounds((int)(firstVariableBase.x+(ancho-firstVariableBase.x)*(xPosIndex/motorTextYPR.length))+10, kdPitchText.getY()+ kdPitchText.getHeight()+30, 50, 20);
				motorValueYPR[i].setBounds(motorTextYPR[i].getX() + motorTextYPR[i].getWidth(), motorTextYPR[i].getY(), 50, 20);
			}
			else{
				motorTextYPR[i].setBounds(motorTextYPR[i-1].getX(),motorTextYPR[i-1].getY()+motorTextYPR[i-1].getHeight()+10, 50, 20);
				motorValueYPR[i].setBounds(motorTextYPR[i].getX() + motorTextYPR[i].getWidth(), motorTextYPR[i].getY(), 50, 20);
			}
			YPRPanel.add(motorTextYPR[i]);
			YPRPanel.add(motorValueYPR[i]);	
		}
	
		/////////////////////////////////////////////////
		
		YPRPanel.add(kpPitchText);
		YPRPanel.add(kiPitchText);
		YPRPanel.add(kdPitchText);
		YPRPanel.add(kpYawText);
		YPRPanel.add(kiYawText);
		YPRPanel.add(kdYawText);
		YPRPanel.add(kpRollText);
		YPRPanel.add(kiRollText);
		YPRPanel.add(kdRollText);
		YPRPanel.add(setPIDButton);
		YPRPanel.add(getPIDButton);
	}
	protected void createXYZTab(){
		super.createXYZTab();
		kpXText = new JLabel("Kp = ");
		kiXText = new JLabel("Ki = ");
		kdXText = new JLabel("Kd = ");
		kpYText = new JLabel("Kp = ");
		kiYText = new JLabel("Ki = ");
		kdYText = new JLabel("Kd = ");
		kpZText = new JLabel("Kp = ");
		kiZText= new JLabel("Ki = ");
		kdZText = new JLabel("Kd = ");
		setPIDButton = new JButton("Set PID");
		getPIDButton = new JButton("Get PID");
		
		getPIDButton.setBounds(midPoint.x + 10, getPointButtonXYZ.getY() + getPointButtonXYZ.getHeight() + 10, 100, 30);
		setPIDButton.setBounds(getPIDButton.getX() + getPIDButton.getWidth() + 10, getPIDButton.getY(), 100, 30);
		
		kpXText.setBounds(firstVariableBase.x + 10, getPIDButton.getY() + getPIDButton.getHeight() + 10 , 50, 30);
		kiXText.setBounds(firstVariableBase.x + 10, kpXText.getY() + kpXText.getHeight() + 10 , 50, 30);
		kdXText.setBounds(firstVariableBase.x + 10, kiXText.getY() + kiXText.getHeight() + 10 , 50, 30);
		
		kpYText.setBounds(secondVariableBase.x + 10, kpXText.getY(), 50, 30);
		kiYText.setBounds(secondVariableBase.x + 10, kpYText.getY() + kpYText.getHeight() + 10 , 50, 30);
		kdYText.setBounds(secondVariableBase.x + 10, kiYText.getY() + kiYText.getHeight() + 10 , 50, 30);
		
		kpZText.setBounds(thirdVariableBase.x + 10, kpXText.getY(), 50, 30);
		kiZText.setBounds(thirdVariableBase.x + 10, kpZText.getY() + kpZText.getHeight() + 10 , 50, 30);
		kdZText.setBounds(thirdVariableBase.x + 10, kiZText.getY() + kiZText.getHeight() + 10 , 50, 30);
		
		newKpX.setBounds(kpXText.getX() + kpXText.getWidth(), kpXText.getY(), 50, 30);
		newKiX.setBounds(kiXText.getX() + kiXText.getWidth(), kiXText.getY(), 50, 30);
		newKdX.setBounds(kdXText.getX() + kdXText.getWidth(), kdXText.getY(), 50, 30);
		
		newKpY.setBounds(kpYText.getX() + kpYText.getWidth(), kpYText.getY(), 50, 30);
		newKiY.setBounds(kiYText.getX() + kiYText.getWidth(), kiYText.getY(), 50, 30);
		newKdY.setBounds(kdYText.getX() + kdYText.getWidth(), kdYText.getY(), 50, 30);
		
		newKpZ.setBounds(kpZText.getX() + kpZText.getWidth(), kpZText.getY(), 50, 30);
		newKiZ.setBounds(kiZText.getX() + kiZText.getWidth(), kiZText.getY(), 50, 30);
		newKdZ.setBounds(kdZText.getX() + kdZText.getWidth(), kdZText.getY(), 50, 30);
		
		newKpX.setText("1.0");
		newKiX.setText("0.0");
		newKdX.setText("0.0");
		
		newKpY.setText("1.0");
		newKiY.setText("0.0");
		newKdY.setText("0.0");
		
		newKpZ.setText("1.0");
		newKiZ.setText("0.0");
		newKdZ.setText("0.0");
		
		/////////////////////////////////////////////////
		for(int i = 0; i< motorTextXYZ.length ; i++){
			float xPosIndex=i;
			if((i%2)==0){
				motorTextXYZ[i].setBounds((int)(firstVariableBase.x+(ancho-firstVariableBase.x)*(xPosIndex/motorTextXYZ.length))+10, kdPitchText.getY()+ kdPitchText.getHeight()+30, 50, 20);
				motorValueXYZ[i].setBounds(motorTextXYZ[i].getX() + motorTextXYZ[i].getWidth(), motorTextXYZ[i].getY(), 50, 20);
			}
			else{
				motorTextXYZ[i].setBounds(motorTextXYZ[i-1].getX(),motorTextXYZ[i-1].getY()+motorTextXYZ[i-1].getHeight()+10, 50, 20);
				motorValueXYZ[i].setBounds(motorTextXYZ[i].getX() + motorTextXYZ[i].getWidth(), motorTextXYZ[i].getY(), 50, 20);
			}
			XYZPanel.add(motorTextXYZ[i]);
			XYZPanel.add(motorValueXYZ[i]);	
		}
	
		/////////////////////////////////////////////////
		
		XYZPanel.add(kpXText);
		XYZPanel.add(kiXText);
		XYZPanel.add(kdXText);
		XYZPanel.add(kpYText);
		XYZPanel.add(kiYText);
		XYZPanel.add(kdYText);
		XYZPanel.add(kpZText);
		XYZPanel.add(kiZText);
		XYZPanel.add(kdZText);
		XYZPanel.add(setPIDButton);
		XYZPanel.add(getPIDButton);
	}


	@Override
	public void addSetPIDButtonListener(ActionListener listener) {
		setPIDButton.addActionListener(listener);
	}
	
	@Override
	public void addGetPIDButtonListener(ActionListener listener){
		getPIDButton.addActionListener(listener);
	}

	
	public void updatePos(float pitch,float yaw, float roll){

	}
	
	@Override
	public void updateMotorAngles(float newMotorAngles[]) {
		for (int i=0; i<newMotorAngles.length;i++){
			motorValueYPR[i].setText(Float.toString(newMotorAngles[i]));
			motorValueXYZ[i].setText(Float.toString(newMotorAngles[i]));
		}
	}
	
}
