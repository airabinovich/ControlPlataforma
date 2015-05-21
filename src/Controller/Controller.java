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
	private PIDButtonListener pidListener;
	private SetPointButtonListener setPointListener;
	
	public Controller(CommunicationModel model){
		this.model = model;
		this.view = new UserView();
		pidListener = new PIDButtonListener();
		setPointListener = new SetPointButtonListener();
	}
	
	public Controller(CommunicationModel model, boolean DebugMode){
		this.model = model;
		this.view = DebugMode ? new DebugView() : new UserView();
		pidListener = new PIDButtonListener();
		setPointListener = new SetPointButtonListener();
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
			System.out.println("Creando PIDListener");
			view.addPIDButtonListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println((model.isPIDActive()?"Activando ":"Desactivando ")+"PID");
			try{
				model.setPIDActive(!model.isPIDActive());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
		}
	}
	class PIDSetButtonListener implements ActionListener{

		public PIDSetButtonListener() {
			System.out.println("Creando PIDListener");
			view.addPIDSetButtonListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Boton Set PID");
			try{
				model.setPIDParameters(view.getNewKp(), view.getNewKi(), view.getNewKd());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
		}
	}
	class SetPointButtonListener implements ActionListener{
		
		public SetPointButtonListener(){
			System.out.println("Creando SETListener");
			view.addSetPointButtonListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Boton SET");
			try{
				model.setPoint(view.getNewPitch(), view.getNewYaw(), view.getNewRoll());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}
			
		}
		
	}
}

