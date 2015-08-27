package Model;

import java.util.ArrayList;

public class PlatformModel  {
	
	private boolean PID;
	private float 	kpPitch,kiPitch,kdPitch,
					kpYaw,kiYaw,kdYaw,
					kpRoll,kiRoll,kdRoll;
	private float pitchPoint, yawPoint, rollPoint;
	private float xPoint, yPoint, zPoint;
	private static final float 	xOrig = 101,
								yOrig = 113,
								zOrig = 230;
	private float pitchCurrent, yawCurrent, rollCurrent;
	private float xCurrent, yCurrent, zCurrent;
	private float motorAnglesCurrent[]= new float[6];
	private float cellValuesCurrent[]= new float[6];
	private ArrayList<ModelObserver> observers;
	
	public PlatformModel(){
		PID = false;
		kpPitch = kpYaw = kpRoll = 0.10f;
		kiPitch = kiYaw = kiRoll = 0;
		kdPitch = kdYaw = kdRoll = 0;
		xPoint=xOrig;
		yPoint= yOrig;
		zPoint= zOrig;
		observers = new ArrayList<ModelObserver>();
	}
	public PlatformModel(boolean isControlled,float kp,float ki,float kd){
		PID = isControlled;
		kpPitch = kpYaw = kpRoll = kp;
		kiPitch = kiYaw = kiRoll = ki;
		kdPitch = kdYaw = kdRoll = kd;
		xPoint=xOrig;
		yPoint= yOrig;
		zPoint= zOrig;
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
	
	public void setPoint(float yawPoint, float pitchPoint, float rollPoint, float xPoint, float yPoint, float zPoint){
		this.pitchPoint = pitchPoint;
		this.yawPoint = yawPoint;
		this.rollPoint = rollPoint;
		this.xPoint = xPoint+xOrig;
		this.yPoint = yPoint+yOrig;
		this.zPoint = zPoint+zOrig;
	}
	
	public void setCurrent(float xCurrent,float yCurrent,float zCurrent, float yawCurrent,float pitchCurrent, float rollCurrent,
							float m0, float m1, float m2, float m3, float m4, float m5){
		//TODO implementar celdas de carga
		this.xCurrent=xCurrent;
		this.yCurrent=yCurrent;
		this.zCurrent=zCurrent;
		this.pitchCurrent = pitchCurrent;
		this.yawCurrent = yawCurrent;
		this.rollCurrent = rollCurrent;
		this.motorAnglesCurrent[0]=m0;
		this.motorAnglesCurrent[1]=m1;
		this.motorAnglesCurrent[2]=m2;
		this.motorAnglesCurrent[3]=m3;
		this.motorAnglesCurrent[4]=m4;
		this.motorAnglesCurrent[5]=m5;
		
		notifyObservers();
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
	public float getXPoint(){
		return xPoint;
	}
	public float getYPoint(){
		return yPoint;
	}
	public float getZPoint(){
		return zPoint;
	}
	public float getX(){
		return xPoint;
	}
	public float getY(){
		return yPoint;
	}
	public float getZ(){
		return zPoint;
	}
	public float getRelativeX() {
		return xPoint-xOrig;
	}
	public float getRelativeY() {
		return yPoint-yOrig;
	}
	public float getRelativeZ() {
		return zPoint-zOrig;
	}
	
	public void attachObserver(ModelObserver o){
		try{
			observers.add(o);
		}catch(NullPointerException e){}
	}
	
	public void removeObserver(ModelObserver o){
		observers.remove(o);
	}
	
	private void notifyObservers(){
		for(ModelObserver o : observers){
			o.updatePitch(pitchCurrent);
			o.updateRoll(rollCurrent);
			o.updateYaw(yawCurrent);
			o.updateMotorAngles(motorAnglesCurrent);
			o.updateX(xCurrent);
			o.updateY(yCurrent);
			o.updateZ(zCurrent);
			o.updateLoadCells(cellValuesCurrent);
			//TODO: agregar implementacion de X,Y y Z
		}
	}
}
