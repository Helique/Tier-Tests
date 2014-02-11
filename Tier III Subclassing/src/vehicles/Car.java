package vehicles;

public class Car {
	
	private int currentGear =0;
	private boolean engineBlown = false;
	private double maxRPM = 7500;
	private double tireCircumference = 83.82;
	private double[] gearRatios = {3.794,2.324,1.624,1.271,1,0.794};
	private double finalDriveRatio = 3.69;
	private double carSpeed = 0;
	private double MPHToHz = 1056;
	private double engineRPM = (MPHToHz*carSpeed*finalDriveRatio*gearRatios[currentGear])/(tireCircumference);
	
	
	public Car(){
		
	}
	public void shiftUp(){
		if(currentGear < gearRatios[currentGear]-1){
			currentGear++;
		}
		recalculateEngineRPM();
	}
	public void shiftDown(){
		if(currentGear > 0){
			currentGear--;
		}
		recalculateEngineRPM();
	}
	public void recalculateEngineRPM(){
		engineRPM = (MPHToHz*carSpeed*finalDriveRatio*gearRatios[currentGear])/(tireCircumference);
		if(engineRPM > maxRPM){
			engineBlown = true;
			engineRPM =0;
			carSpeed = 0;
		}
	}
	public boolean isEngineBlown(){
		return engineBlown;
	}
	public double getRPM(){
		return engineRPM;
	}
	public double getMaxRPM (){
		return maxRPM;
	}	
	public double getSpeed(){
		return carSpeed;
	}
	
	public void accelerate(double acceleration){
		
		carSpeed += acceleration;
		if(carSpeed <=0){
			carSpeed =0;
		}
		recalculateEngineRPM();
	}
	
}
