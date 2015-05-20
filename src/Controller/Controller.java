package Controller;

import Model.CommunicationModel;
import View.DebugView;
import View.UserView;
import View.View;

public class Controller {
	protected View view;
	protected CommunicationModel model;
	
	public Controller(CommunicationModel model){
		this.model = model;
		this.view = new UserView(this,model);
		
	}
	
	public Controller(CommunicationModel model, boolean DebugMode){
		this.model = model;
		this.view = DebugMode ? new DebugView(this,model) : new UserView(this,model);
	}
	
	/**
	 * @param pitch
	 * @deprecated
	 */
	public void UpdatePitch(float pitch){
		view.updatePitch(pitch);
	}
}
