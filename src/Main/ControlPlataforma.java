package Main;

import java.util.Random;

import Controller.Controller;
import Model.PlatformModel;

public class ControlPlataforma {

	private static Controller controller;
	private static PlatformModel model;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		model = new PlatformModel();
		controller = new Controller(model, true);

//		int i = 0;
//		Random r = new Random();
//		float celdas[]= new float[6];
//		while(true){
//			
//			try {
//				Thread.sleep(50);
//				controller.updatePitch(r.nextFloat()-0.5f);
//				controller.updateYaw(r.nextFloat()-0.5f);
//				controller.updateRoll(r.nextFloat()-0.5f);
//				controller.updateX(r.nextFloat()-0.5f);
//				controller.updateY(r.nextFloat()-0.5f);
//				controller.updateZ(r.nextFloat()-0.5f);
//				for (int j=0;j<6;j++){
//					celdas[j]=(r.nextFloat()-0.5f)*800;
//				}
//				controller.updateLoadCells(celdas);
//				i = (i+1)%10;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

}
