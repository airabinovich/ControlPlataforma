package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;

public class DebugView extends View {

	private static final long serialVersionUID = 1L;
	private Container contentpane;
	
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
		this.setBounds(200, 50, 1000, 600);
		
		int alto = this.getHeight(), ancho = this.getWidth();
		
		JPanel	pitchPanel = new ChartPanel(pitchGraph),
				yawPanel = new ChartPanel(yawGraph),
				rollPanel = new ChartPanel(rollGraph);
		
		pitchPanel.setBounds(0, 0, ancho/2, alto/3);
		yawPanel.setBounds(0, alto/3, ancho/2, alto/3);
		rollPanel.setBounds(0, 2*alto/3, ancho/2, alto/3);
		
		newPitch.setBounds(ancho/2 + 10,(int) (alto * 11./12), 100, 30);
		newYaw.setBounds(ancho/2 + 160, (int) (alto * 11./12), 100, 30);
		newRoll.setBounds(ancho/2 + 320, (int) (alto * 11./12), 100, 30);
		
		pitchSlider = new JSlider(SwingConstants.VERTICAL, -50, 50, 0);
		yawSlider = new JSlider(SwingConstants.VERTICAL, -50, 50, 0);
		rollSlider = new JSlider(SwingConstants.VERTICAL, -50, 50, 0);
		
		pitchSlider.setMinorTickSpacing(1);
		pitchSlider.setMajorTickSpacing(10);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setPaintLabels(true);
		
		yawSlider.setMinorTickSpacing(1);
		yawSlider.setMajorTickSpacing(10);
		yawSlider.setPaintTicks(true);
		yawSlider.setPaintLabels(true);
		
		rollSlider.setMinorTickSpacing(1);
		rollSlider.setMajorTickSpacing(10);
		rollSlider.setPaintTicks(true);
		rollSlider.setPaintLabels(true);
		
		pitchSlider.setBounds(newPitch.getX() + newPitch.getWidth()/3, newPitch.getY() - 320, 50, 300);
		yawSlider.setBounds(newYaw.getX() + newYaw.getWidth()/3, newYaw.getY() - 320, 50, 300);
		rollSlider.setBounds(newRoll.getX() + newRoll.getWidth()/3, newRoll.getY() - 320, 50, 300);
		
		pitchSlider.setBackground(Color.lightGray);
		yawSlider.setBackground(Color.lightGray);
		rollSlider.setBackground(Color.lightGray);
		
		JLabel	pitchText = new JLabel("Pitch"),
				yawText = new JLabel("Yaw"),
				rollText = new JLabel("Roll"),
				kpText = new JLabel("Kp"),
				kiText = new JLabel("Ki"),
				kdText = new JLabel("Kp");
		
		pitchText.setBounds(newPitch.getX() + newPitch.getWidth() + 10, newPitch.getY(), 100, 30);
		yawText.setBounds(newYaw.getX() + newYaw.getWidth() + 10, newYaw.getY(), 100, 30);
		rollText.setBounds(newRoll.getX() + newRoll.getWidth() + 10, newRoll.getY(), 100, 30);
		
		
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
		contentpane.add(pitchSlider);
		contentpane.add(yawSlider);
		contentpane.add(rollSlider);
		
		contentpane.add(fondo);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
