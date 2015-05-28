package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;

public class DebugView extends View {

	private static final long serialVersionUID = 1L;
	private Container contentpane;
	private JLabel	pitchText, yawText, rollText, kpText, kiText, kdText, activePIDText;
	public DebugView() {
		
		super("Control de Plataforma - Debug Mode");
		createView();
	}

	protected void createView() {
		super.createView();
		contentpane = this.getContentPane();
		
		JPanel fondo = new JPanel();
		fondo.setBackground(Color.lightGray);
		
		this.setResizable(false);
		this.setBounds(200, 50, 850, 600);
		
		int alto = this.getHeight(), ancho = this.getWidth();
		
		JPanel	pitchPanel = new ChartPanel(pitchGraph),
				yawPanel = new ChartPanel(yawGraph),
				rollPanel = new ChartPanel(rollGraph);
		
		pitchPanel.setBounds(0, 0, ancho/2, alto/3);
		yawPanel.setBounds(0, alto/3, ancho/2, alto/3);
		rollPanel.setBounds(0, 2*alto/3, ancho/2, alto/3);
		
		newPitch.setBounds(4*ancho/8 + 10,alto * 11/12, 100, 30);
		newYaw.setBounds(11*ancho/16 ,alto * 11/12, 100, 30);
		newRoll.setBounds(14*ancho/16,alto * 11/12, 100, 30);
		
		newPitch.setText("0.0");
		newYaw.setText("0.0");
		newRoll.setText("0.0");
		
		pitchSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		yawSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		rollSlider = new JSlider(SwingConstants.VERTICAL, -100, 100, 0);
		
		//pitchSlider.setMinorTickSpacing(1);
		pitchSlider.setMajorTickSpacing(1);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setPaintLabels(true);
		
		yawSlider.setMinorTickSpacing(1);
		yawSlider.setMajorTickSpacing(2);
		yawSlider.setPaintTicks(true);
		yawSlider.setPaintLabels(true);
		
		rollSlider.setMinorTickSpacing(1);
		rollSlider.setMajorTickSpacing(2);
		rollSlider.setPaintTicks(true);
		rollSlider.setPaintLabels(true);
		
		pitchSlider.setBounds(newPitch.getX() + newPitch.getWidth()/3, newPitch.getY() - 320, 50, 300);
		yawSlider.setBounds(newYaw.getX() + newYaw.getWidth()/3, newYaw.getY() - 320, 50, 300);
		rollSlider.setBounds(newRoll.getX() + newRoll.getWidth()/3, newRoll.getY() - 320, 50, 300);
		
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
		
		newKp = new JTextField();
		newKi = new JTextField();
		newKd = new JTextField();
		
		pitchText = new JLabel("Pitch:");
		yawText = new JLabel("Yaw:");
		rollText = new JLabel("Roll:");
		kpText = new JLabel("Kp = ");
		kiText = new JLabel("Ki = ");
		kdText = new JLabel("Kp = ");
		activePIDText = new JLabel("PID desactivado");		
		
		pitchText.setBounds(newPitch.getX(), pitchSlider.getY() - 30, 100, 30);
		yawText.setBounds(newYaw.getX(), yawSlider.getY() - 30, 100, 30);
		rollText.setBounds(newRoll.getX(), rollSlider.getY() - 30, 100, 30);
		
		kpText.setBounds(ancho/2 +10, alto/12 , 50, 30);
		kiText.setBounds(ancho/2 +10, alto/6 , 50, 30);
		kdText.setBounds(ancho/2 +10, alto/4 , 50, 30);
		
		newKp.setBounds(kpText.getX() + kpText.getWidth(), kpText.getY(), 100, 30);
		newKi.setBounds(kiText.getX() + kiText.getWidth(), kiText.getY(), 100, 30);
		newKd.setBounds(kdText.getX() + kdText.getWidth(), kdText.getY(), 100, 30);
		
		newKp.setText("1.0");
		newKi.setText("0.0");
		newKd.setText("0.0");
		
		pidButton.setBounds(ancho/2 + 10, alto/48, 100, 30);
		activePIDText.setBounds(pidButton.getX() + pidButton.getWidth() + 10, pidButton.getY(), 150,30);
		
		getPointButton.setBounds(ancho*23/32, alto/4, 100, 30);
		setPointButton.setBounds(getPointButton.getX() + getPointButton.getWidth() + 10, getPointButton.getY(), 100, 30);
		
		getPIDButton.setBounds(ancho*23/32, alto/12, 100, 30);
		setPIDButton.setBounds(getPIDButton.getX() + getPIDButton.getWidth() + 10, getPIDButton.getY(), 100, 30);
		
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
		contentpane.add(newKp);
		contentpane.add(newKi);
		contentpane.add(newKd);
		contentpane.add(kpText);
		contentpane.add(kiText);
		contentpane.add(kdText);
		contentpane.add(activePIDText);
		
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
	
	public void togglePID(boolean state){
		activePIDText.setText(state ? "PID Activado" : "PID Desactivado");
	}
	
	public void updatePos(float pitch,float yaw, float roll){
		pitchSlider.setValue((int)(pitch*10));
		yawSlider.setValue((int)(yaw*10));
		rollSlider.setValue((int)(roll*10));
	}
	
}
