package characters;

import java.awt.Graphics2D;

public class Monster {
	double x =0;
	double y= 0;
	double lastx =0;
	double lasty =0;
	double width =0;
	double height =0;
	double velocityX =0;
	double velocityY =0;
	double Speed;
	double[][] pathPoints;
	int currentPointDestination =0;
	String movementDirection ="right";
	final static String MovingUP ="UP";
	final static String MovingDOWN ="DOWN";
	final static String MovingRIGHT ="RIGTH";
	final static String MovingLEFT ="LEFT";
	
	public Monster(double[][] setPathPoints, double setSpeed){
		Speed = setSpeed;
		pathPoints = setPathPoints;
		x= pathPoints[currentPointDestination][0];
		y= pathPoints[currentPointDestination][1];
		lastx =x;
		lasty =y;
		currentPointDestination++;
		this.calculateDirectionToNextPoint();
	}
	
	
	public void render(Graphics2D g2d, double interpolation) {
		double renderX = lastx + (x-lastx)*interpolation;
		double renderY = lasty + (y-lasty)*interpolation;
		g2d.drawOval((int)renderX-5, (int)renderY-5, 10, 10);
		
	}
	public void updateLogic() {
		lastx =x;
		lasty =y;
		if(movementDirection == MovingRIGHT){
			velocityX = Speed;
			velocityY = 0;
			x +=velocityX;
			y +=velocityY;
			if(pathPoints[currentPointDestination][0] < x){
				x = pathPoints[currentPointDestination][0];
				currentPointDestination++;
				this.calculateDirectionToNextPoint();
			}
		} else if(movementDirection == MovingLEFT){
			velocityX = -Speed;
			velocityY = 0;
			x +=velocityX;
			y +=velocityY;
			if(pathPoints[currentPointDestination][0] > x){
				x = pathPoints[currentPointDestination][0];
				currentPointDestination++;
				this.calculateDirectionToNextPoint();
			}
		} else if(movementDirection == MovingUP){
			velocityX = 0;
			velocityY = -Speed;
			x +=velocityX;
			y +=velocityY;
			if(pathPoints[currentPointDestination][1] > y){
				y = pathPoints[currentPointDestination][1];
				currentPointDestination++;
				this.calculateDirectionToNextPoint();
			}
		} else if(movementDirection == MovingDOWN){
			velocityX = 0;
			velocityY = Speed;
			x +=velocityX;
			y +=velocityY;
			if(pathPoints[currentPointDestination][1] < y){
				y = pathPoints[currentPointDestination][1];
				currentPointDestination++;
				this.calculateDirectionToNextPoint();
			}
		}
		
		
		
	}
	private void calculateDirectionToNextPoint(){
		if(pathPoints[currentPointDestination][0] > x){
			movementDirection = MovingRIGHT;
		} else if(pathPoints[currentPointDestination][0] < x){
			movementDirection = MovingLEFT;
		} else if(pathPoints[currentPointDestination][1] < y){
			movementDirection = MovingUP;
		} else if(pathPoints[currentPointDestination][1] > y){
			movementDirection = MovingDOWN;
		}
		
	}
	
	
}
