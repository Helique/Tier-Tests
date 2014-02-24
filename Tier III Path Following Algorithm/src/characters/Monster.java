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
	
	//create a monster, it requires the entire path he is going to follow and the speed
	//he can move at9=
	public Monster(double[][] setPathPoints, double setSpeed){
		Speed = setSpeed;
		pathPoints = setPathPoints;
		//set current location the the beginning of the path
		x= pathPoints[currentPointDestination][0];
		y= pathPoints[currentPointDestination][1];
		lastx =x;
		lasty =y;
		//increment the current destination
		currentPointDestination++;
		//calculate the movement direction to get to the next point on the path.
		this.calculateDirectionToNextPoint();
	}
	
	//render the character 
	public void render(Graphics2D g2d, double interpolation) {
		//the characters render location is based on its last position
		//plus the difference between the current location and the last location multiplied by the percent of 
		//the time between logic updates
		double renderX = lastx + (x-lastx)*interpolation;
		double renderY = lasty + (y-lasty)*interpolation;
		g2d.drawOval((int)renderX-5, (int)renderY-5, 10, 10);
	}
	
	public void updateLogic() {
		lastx =x;
		lasty =y;
		
		//move in a direction based on the direction your supposed to move! 
		if(movementDirection == MovingRIGHT){
			velocityX = Speed;
			velocityY = 0;
			x +=velocityX;
			y +=velocityY;
			//if you have moved, and then passed your destination, move back to your destination
			//increment the location number
			//recalculate movementDirection with the new location number
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
	
	//calculates the movement direction required to get to the next destinationPoint
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
