package gameComponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import characters.Monster;



public class GamePanel extends JPanel {

	final double Logic_Hertz = 30;
	final double Target_Time_Between_Logic = 1000000000/Logic_Hertz;
	final double Render_Hertz = 60;
	final double Target_Time_Between_Renders = 1000000000/Render_Hertz;
	double lastLogicTime = System.nanoTime();
	double lastRenderTime = System.nanoTime();
	boolean running = true;
	double interpolation=0;
	boolean upKeyPressed = false;
	boolean downKeyPressed = false;
	boolean leftKeyPressed = false;
	boolean rightKeyPressed = false;
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
	
	ArrayList<Monster> monsterList = new ArrayList<Monster>();
	
	public void enterGameLoop(){
		monsterList.add(new Monster(Level1,5));
		while(running){
			lastLogicTime =System.nanoTime();
			logic();
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
	private void render(){
		this.repaint();
	}
	private void logic(){
		for(Monster m: monsterList){
			m.updateLogic();
		}
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		
		for(Monster m: monsterList){
			m.render(g2d,interpolation);
		}
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
			String firstDirection = "Right";
			String secondDirection = "Left";
			if(pathPoints[i][0] > pathPoints[i-1][0]){
				firstDirection = MovingRIGHT;
			} else if(pathPoints[i][0] < pathPoints[i-1][0]){
				firstDirection = MovingLEFT;
			} else if(pathPoints[i][1] < pathPoints[i-1][1]){
				firstDirection = MovingUP;
			} else if(pathPoints[i][1] > pathPoints[i-1][1]){
				firstDirection = MovingDOWN;
			}
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
