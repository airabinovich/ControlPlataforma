package Model;

public interface ModelObserver {
	
	void updatePitch(float newPitch);
	void updateRoll(float newRoll);
	void updateYaw(float newYaw);
	void updateX(float newX);
	void updateY(float newY);
	void updateZ(float newZ);
	void updateMotorAngles(float[] newMotorAngles);

}
