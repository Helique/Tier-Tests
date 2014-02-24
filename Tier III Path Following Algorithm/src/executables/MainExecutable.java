package executables;

import javax.swing.JFrame;

import gameComponents.GamePanel;

public class MainExecutable {

	public static void main(String[] args) {
		JFrame gameWindow = new JFrame();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setTitle("A Tale of Two Towers");
		gameWindow.setSize(500,500);
		gameWindow.setResizable(false);
		GamePanel gamePanel = new GamePanel();
		gameWindow.add(gamePanel);
		gameWindow.setVisible(true);
		gamePanel.enterGameLoop();
		

	}

}
