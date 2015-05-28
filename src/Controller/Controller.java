package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.CommunicationModel;
import View.DebugView;
import View.UserView;
import View.View;

public class Controller{
	protected View view;
	protected CommunicationModel model;
	private PIDButtonListener PIDListener;
	private SetPointButtonListener setPointListener;
	private GetPointButtonListener getPointListener;
	private GetPIDButtonListener getPIDListener;
	private SetPIDButtonListener setPIDListener;
	
	public Controller(CommunicationModel model){
		this.model = model;
		this.view = new UserView();
		PIDListener = new PIDButtonListener();
		getPIDListener = new GetPIDButtonListener();
		setPIDListener = new SetPIDButtonListener();
		setPointListener = new SetPointButtonListener();
		getPointListener = new GetPointButtonListener();
	}
	
	public Controller(CommunicationModel model, boolean DebugMode){
		this.model = model;
		this.view = DebugMode ? new DebugView() : new UserView();
		PIDListener = new PIDButtonListener();
		setPointListener = new SetPointButtonListener();
		getPointListener = new GetPointButtonListener();
		getPIDListener = new GetPIDButtonListener();
		setPIDListener = new SetPIDButtonListener();
	}
	
	/**
	 * @param pitch
	 * @deprecated
	 */
	public void UpdatePitch(float pitch){
		view.updatePitch(pitch);
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
				model.setPIDParameters(view.getNewKp(), view.getNewKi(), view.getNewKd());
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
				view.updatePID(model.getKP(),model.getKI(),model.getKD());
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
				model.setPoint(view.getNewPitch(), view.getNewYaw(), view.getNewRoll());
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
}

