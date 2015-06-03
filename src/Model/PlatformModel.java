package Model;

import java.util.ArrayList;

public class PlatformModel  {
	
	private boolean PID;
	private float 	kpPitch,kiPitch,kdPitch,
					kpYaw,kiYaw,kdYaw,
					kpRoll,kiRoll,kdRoll;
	private float pitchPoint, yawPoint, rollPoint;
	private float pitchCurrent, yawCurrent, rollCurrent;
	
	private ArrayList<ModelObserver> observers;
	
	public PlatformModel(){
		PID = false;
		kpPitch = kpYaw = kpRoll = 1;
		kiPitch = kiYaw = kiRoll = 0;
		kdPitch = kdYaw = kdRoll = 0;
		
		observers = new ArrayList<ModelObserver>();
	}
	public PlatformModel(boolean isControlled,float kp,float ki,float kd){
		PID = isControlled;
		kpPitch = kpYaw = kpRoll = kp;
		kiPitch = kiYaw = kiRoll = ki;
		kdPitch = kdYaw = kdRoll = kd;
		
		observers = new ArrayList<ModelObserver>();
	}
	
	public void setPIDParameters(GradoDeLibertad grado,float kp,float ki,float kd){
		switch(grado){
		case PITCH:
			kpPitch = kp;
			kiPitch = ki;
			kdPitch = kd;
			break;
		case YAW:
			kpYaw = kp;
			kiYaw = ki;
			kdYaw = kd;
			break;
		default:
			kpRoll = kp;
			kiRoll = ki;
			kdRoll = kd;
			break;
		}
	}
	
	public void setPoint(float yawPoint, float pitchPoint, float rollPoint){
		this.pitchPoint = pitchPoint;
		this.yawPoint = yawPoint;
		this.rollPoint = rollPoint;
	}
	
	public void setCurrent(float xCurrent,float yCurrent,float zCurrent, float yawCurrent,float pitchCurrent, float rollCurrent){
		//TODO implementar x,y,z
		this.pitchCurrent = pitchCurrent;
		this.yawCurrent = yawCurrent;
		this.rollCurrent = rollCurrent;
	}
	public boolean isPIDActive(){
		return PID;
	}
	public void setPIDActive(boolean isActive){
		PID = isActive;
	}
	public float getKP(GradoDeLibertad grado){
		switch(grado){
		case PITCH:
			return kpPitch;
		case YAW:
			return kpYaw;
		default:
			return kpRoll;
		}
	}
	public float getKI(GradoDeLibertad grado){
		switch(grado){
		case PITCH:
			return kiPitch;
		case YAW:
			return kiYaw;
		default:
			return kiRoll;
		}
	}
	public float getKD(GradoDeLibertad grado){
		switch(grado){
		case PITCH:
			return kdPitch;
		case YAW:
			return kdYaw;
		default:
			return kdRoll;
		}
	}
	public float getPitchPoint(){
		return pitchPoint;
	}
	public float getYawPoint(){
		return yawPoint;
	}
	public float getRollPoint(){
		return rollPoint;
	}
	public float getX(){
		return 0;
	}
	public float getY(){
		return 0;
	}
	public float getZ(){
		return 0;
	}
	
	public void attachObserver(ModelObserver o){
		try{
			observers.add(o);
		}catch(NullPointerException e){}
	}
	
	public void removeObserver(ModelObserver o){
		observers.remove(o);
	}
	
	public void notifyObservers(){
		for(ModelObserver o : observers){
			//TODO notificar observadores
		}
	}
}
