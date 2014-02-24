package gameComponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import characters.Monster;



public class GamePanel extends JPanel {

	//Hertz measures the amount of cycles in one second, 60 fps == 60hz
	final double Logic_Hertz = 30;
	
	//Target_Time is measured in milliseconds. Time for one cycle == (1second/#OFHERTZ);
	final double Target_Time_Between_Logic = 1000000000/Logic_Hertz;
	final double Render_Hertz = 60;
	final double Target_Time_Between_Renders = 1000000000/Render_Hertz;
	
	//these times are recorded when a logic/render update happens
	//when the difference between this value and the current time is > Target_Time_Between_XXX
	//then a render or logic update is executed
	double lastLogicTime = System.nanoTime();
	double lastRenderTime = System.nanoTime();
	
	//set to false to exit the gameLoop
	boolean running = true;
	
	//this value is a # between 0 and 1
	//this value is based on the time percentage between logic updates at the time of a render
	private double interpolation=0;
	
	
	//Path points for level1, order determines the order that the monster moves
	//only state corners, and only give commands at 90 degree angles
	double[][] Level1 = {
							{0,250},
							{100,250},
							{100,100},
							{200,100},
							{200,400},
							{400,400},
							{400,300},
							{300,300},
							{300,50},
							{50,50},
							{50,200},
							{0,200}
	};
	
	//A list of monsters, used to easily keep track of all the little critters...
	ArrayList<Monster> monsterList = new ArrayList<Monster>();
	
	public void enterGameLoop(){
		monsterList.add(new Monster(Level1,5));
		
		//Loop runs forever
		while(running){
			lastLogicTime =System.nanoTime();
			updateLogic();
			System.out.println("Logic executed!");
			while((System.nanoTime() -lastLogicTime)< Target_Time_Between_Logic){
				System.out.println("Render executed!");
				lastRenderTime = System.nanoTime();
				interpolation = (System.nanoTime()-lastLogicTime)/Target_Time_Between_Logic;
				render();
				while((System.nanoTime() - lastRenderTime)<Target_Time_Between_Renders){
					Thread.yield();
					try {
						Thread.sleep(0, 250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
	}
	
	//Render, Called from game loop
	private void render(){
		this.repaint();
	}
	//Called from Gameloop
	private void updateLogic(){
		for(Monster m: monsterList){
			m.updateLogic();
		}
	}
	//called from repaint, which is called in render
	public void paint(Graphics g){
		//clear the screen, comment this code out and run the application
		//to see what happens
		super.paint(g);
		
		
		Graphics2D g2d = (Graphics2D)g;
		
		
		//go through the list of monsters and render each one
		for(Monster m: monsterList){
			m.render(g2d,interpolation);
		}
		
		//render the path
		drawPath(Level1, g2d);
	}
	
	public void drawPath(double[][] pathPoints, Graphics2D g2d){
		int PathWidth = 18;
		String MovingUP ="UP";
		String MovingDOWN ="DOWN";
		String MovingRIGHT ="RIGTH";
		String MovingLEFT ="LEFT";
		
		//draw corridors 
		for (int i=0; i <pathPoints.length -1; i++){
			String Direction = "RIGHT";
			
			if(pathPoints[i+1][0] > pathPoints[i][0]){
				Direction = MovingRIGHT;
			} else if(pathPoints[i+1][0] < pathPoints[i][0]){
				Direction = MovingLEFT;
			} else if(pathPoints[i+1][1] < pathPoints[i][1]){
				Direction = MovingUP;
			} else if(pathPoints[i+1][1] > pathPoints[i][1]){
				Direction = MovingDOWN;
			}
			
			if(Direction == MovingRIGHT){
				g2d.drawLine((int)pathPoints[i][0] + PathWidth/2, (int)pathPoints[i][1] - PathWidth/2, (int)pathPoints[i+1][0] - PathWidth/2, (int)pathPoints[i+1][1] - PathWidth/2);
				g2d.drawLine((int)pathPoints[i][0] + PathWidth/2, (int)pathPoints[i][1] + PathWidth/2, (int)pathPoints[i+1][0] - PathWidth/2, (int)pathPoints[i+1][1] + PathWidth/2);
			} else if(Direction == MovingLEFT){
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] - PathWidth/2, (int)pathPoints[i+1][0] + PathWidth/2, (int)pathPoints[i+1][1] - PathWidth/2);
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] + PathWidth/2, (int)pathPoints[i+1][0] + PathWidth/2, (int)pathPoints[i+1][1] + PathWidth/2);
			} else if(Direction == MovingUP){
				
			} else if(Direction == MovingDOWN){
				
			}
		}
		
		//Draw the corners
		for (int i = 1; i<pathPoints.length-1;i++){
			//direction the monsters will come from
			String firstDirection = "Right";
			//direction the monsters will go to
			String secondDirection = "Left";
			
			//figure out which direction the monsters will come from
			if(pathPoints[i][0] > pathPoints[i-1][0]){
				firstDirection = MovingRIGHT;
			} else if(pathPoints[i][0] < pathPoints[i-1][0]){
				firstDirection = MovingLEFT;
			} else if(pathPoints[i][1] < pathPoints[i-1][1]){
				firstDirection = MovingUP;
			} else if(pathPoints[i][1] > pathPoints[i-1][1]){
				firstDirection = MovingDOWN;
			}
			
			//figure out which direction the monsters will go to
			if(pathPoints[i+1][0] > pathPoints[i][0]){
				secondDirection = MovingRIGHT;
			} else if(pathPoints[i+1][0] < pathPoints[i][0]){
				secondDirection = MovingLEFT;
			} else if(pathPoints[i+1][1] < pathPoints[i][1]){
				secondDirection = MovingUP;
			} else if(pathPoints[i+1][1] > pathPoints[i][1]){
				secondDirection = MovingDOWN;
			}
	
			
			// left and top line corner pair
			if (firstDirection == MovingUP && secondDirection == MovingRIGHT){
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] - PathWidth/2, (int)pathPoints[i][0] + PathWidth/2, (int)pathPoints[i][1] - PathWidth/2);
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] + PathWidth/2, (int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] - PathWidth/2);
			}
			else if (firstDirection == MovingLEFT && secondDirection == MovingDOWN){
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] - PathWidth/2, (int)pathPoints[i][0] + PathWidth/2, (int)pathPoints[i][1] - PathWidth/2);
				g2d.drawLine((int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] + PathWidth/2, (int)pathPoints[i][0] - PathWidth/2, (int)pathPoints[i][1] - PathWidth/2);
			}
			
			// right and top line corner pair
			else if (firstDirection == MovingUP && secondDirection == MovingLEFT){
				
			}
			else if (firstDirection == MovingRIGHT && secondDirection == MovingDOWN){
				
			}
			
			//left and bottom line corner pair
			else if (firstDirection == MovingDOWN && secondDirection == MovingRIGHT){
				
			}
			else if (firstDirection == MovingLEFT && secondDirection == MovingUP){
				
			}
			
			//right and bottom line corner pair
			else if (firstDirection == MovingDOWN && secondDirection == MovingLEFT){
				
			}
			else if (firstDirection == MovingRIGHT && secondDirection == MovingUP){
				
			}
			
		}
	}

}
