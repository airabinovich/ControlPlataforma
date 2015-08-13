package View;

import java.awt.event.ActionListener;

import Model.PlatformModel;

public class UserView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6539339348338170290L;


	public UserView(PlatformModel model) {
		super("Control de Plataforma", model);
		// TODO Auto-generated constructor stub
		createView();
	}



	@Override
	public void addSetPIDButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addGetPIDButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateMotorAngles(float[] newMotorAngles) {
		// TODO Auto-generated method stub
		
	}

}
