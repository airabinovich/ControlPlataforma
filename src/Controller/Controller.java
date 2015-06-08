package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.ModelObserver;
import Model.PlatformModel;
import Model.GradoDeLibertad;
import Model.TwoWaySerialComm;
import View.DebugView;
import View.UserView;
import View.View;

public class Controller implements ModelObserver{
	protected View view;
	protected PlatformModel model;
	private PIDButtonListener PIDListener;
	private SetPointButtonListener setPointListener;
	private GetPointButtonListener getPointListener;
	private GetPIDButtonListener getPIDListener;
	private SetPIDButtonListener setPIDListener;
	
	protected TwoWaySerialComm comunicacion;
	
	public Controller(PlatformModel model){
		this.model = model;
		this.view = new UserView(model);
		PIDListener = new PIDButtonListener();
		getPIDListener = new GetPIDButtonListener();
		setPIDListener = new SetPIDButtonListener();
		setPointListener = new SetPointButtonListener();
		getPointListener = new GetPointButtonListener();
		
		comunicacion = new TwoWaySerialComm(this.model);
		comunicacion.connect();
		model.attachObserver(this);
	}
	
	public Controller(PlatformModel model, boolean DebugMode){
		this.model = model;
		this.view = DebugMode ? new DebugView(model) : new UserView(model);
		PIDListener = new PIDButtonListener();
		setPointListener = new SetPointButtonListener();
		getPointListener = new GetPointButtonListener();
		getPIDListener = new GetPIDButtonListener();
		setPIDListener = new SetPIDButtonListener();
		
		comunicacion = new TwoWaySerialComm(this.model);
		comunicacion.connect();
		model.attachObserver(this);
	}
	
	class PIDButtonListener implements ActionListener{

		public PIDButtonListener() {
			view.addPIDButtonListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				model.setPIDActive(!model.isPIDActive());
				((DebugView)(view)).togglePID(model.isPIDActive());
				
				comunicacion.sendMessage();
				
				System.out.println((model.isPIDActive()?"Activando ":"Desactivando ")+"PID");
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
		}
	}
	class SetPIDButtonListener implements ActionListener{

		public SetPIDButtonListener() {
			view.addSetPIDButtonListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				model.setPIDParameters(GradoDeLibertad.PITCH,view.getNewKp(GradoDeLibertad.PITCH), view.getNewKi(GradoDeLibertad.PITCH), view.getNewKd(GradoDeLibertad.PITCH));
				model.setPIDParameters(GradoDeLibertad.YAW,view.getNewKp(GradoDeLibertad.YAW), view.getNewKi(GradoDeLibertad.YAW), view.getNewKd(GradoDeLibertad.YAW));
				model.setPIDParameters(GradoDeLibertad.ROLL,view.getNewKp(GradoDeLibertad.ROLL), view.getNewKi(GradoDeLibertad.ROLL), view.getNewKd(GradoDeLibertad.ROLL));
				
				comunicacion.sendMessage();
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
		}
	}
	class GetPIDButtonListener implements ActionListener{

		public GetPIDButtonListener() {
			view.addGetPIDButtonListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				view.updatePID(GradoDeLibertad.PITCH,model.getKP(GradoDeLibertad.PITCH),model.getKI(GradoDeLibertad.PITCH),model.getKD(GradoDeLibertad.PITCH));
				view.updatePID(GradoDeLibertad.YAW,model.getKP(GradoDeLibertad.YAW),model.getKI(GradoDeLibertad.YAW),model.getKD(GradoDeLibertad.YAW));
				view.updatePID(GradoDeLibertad.ROLL,model.getKP(GradoDeLibertad.ROLL),model.getKI(GradoDeLibertad.ROLL),model.getKD(GradoDeLibertad.ROLL));
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
		}
	}
	class SetPointButtonListener implements ActionListener{
		
		public SetPointButtonListener(){
			view.addSetPointButtonListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				model.setPoint( view.getNewYaw(), view.getNewPitch(), view.getNewRoll());
				
				comunicacion.sendMessage();
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
			
		}
		
	}
	class GetPointButtonListener implements ActionListener{
		
		public GetPointButtonListener(){
			view.addGetPointButtonListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				view.updatePos(model.getPitchPoint(), model.getYawPoint(), model.getRollPoint());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
			
		}
		
	}
	@Override
	public void updatePitch(float newPitch) {
		view.updatePitch(newPitch, model.getPitchPoint());
		
	}

	@Override
	public void updateRoll(float newRoll) {
		view.updateRoll(newRoll, model.getRollPoint());
		
	}

	@Override
	public void updateYaw(float newYaw) {
		view.updateYaw(newYaw, model.getYawPoint());
		
	}

	@Override
	public void updateX(float newX) {
		view.updateX(newX);
		
	}

	@Override
	public void updateY(float newY) {
		view.updateY(newY);
		
	}

	@Override
	public void updateZ(float newZ) {
		view.updateZ(newZ);
		
	}

	@Override
	public void updateMotorAngles(float[] newMotorAngles) {
		view.updateMotorAngles(newMotorAngles);		
	}
}

