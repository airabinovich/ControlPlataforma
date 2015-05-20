package View;

public interface ModelObserver {
	
	void updatePitch(float newPitch);
	void updateRoll(float newRoll);
	void updateYaw(float newYaw);
	void updateAccelX();
	void updateAccelY();
	void updateAccelZ();
	void updateHeight();

}
