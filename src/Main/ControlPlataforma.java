package Main;

import java.util.Scanner;

import Controller.Controller;
import Model.CommunicationModel;

public class ControlPlataforma {

	private static Controller controller;
	private static CommunicationModel model;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		model = new CommunicationModel();
		controller = new Controller(model, true);
		Scanner teclado = new Scanner(System.in);
		while(true){
			
			controller.UpdatePitch(teclado.nextFloat());
			
		}
	}

}
