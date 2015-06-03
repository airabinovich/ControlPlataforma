package Main;

import java.util.Random;
import java.util.Scanner;

import Controller.Controller;
import Model.PlatformModel;
import Model.TwoWaySerialComm;

public class ControlPlataforma {

	private static Controller controller;
	private static PlatformModel model;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		model = new PlatformModel();
		controller = new Controller(model, true);
		Scanner teclado = new Scanner(System.in);
//		int i = 0;
//		Random r = new Random();
//		while(true){
//			
//			try {
//				Thread.sleep(50);
//				controller.UpdatePitch(r.nextFloat());
//				controller.UpdateYaw(r.nextFloat());
//				controller.UpdateRoll(r.nextFloat());
//				i = (i+1)%10;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//		}
	}

}
