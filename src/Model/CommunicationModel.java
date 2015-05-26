package Model;

public class CommunicationModel  {
	
	private boolean PID;
	private float kp,ki,kd;
	private float pitchPoint, yawPoint, rollPoint;
	private float pitchCurrent, yawCurrent, rollCurrent;
	
	public CommunicationModel(){
		PID = false;
		kp = 1;
		ki = 0;
		kd = 0;
	}
	public CommunicationModel(boolean isControlled,float kp,float ki,float kd){
		PID = isControlled;
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
	}
	
	public void setPIDParameters(float kp,float ki,float kd){
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
	}
	
	public void setPoint(float pitchPoint, float yawPoint, float rollPoint){
		this.pitchPoint = pitchPoint;
		this.yawPoint = yawPoint;
		this.rollPoint = rollPoint;
	}
	
	public void setCurrent(float pitchCurrent, float yawCurrent, float rollCurrent){
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
	public float getKP(){
		return kp;
	}
	public float getKI(){
		return ki;
	}
	public float getKD(){
		return kd;
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
}
