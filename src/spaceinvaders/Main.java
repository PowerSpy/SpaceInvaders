package spaceinvaders;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	JFrame frame;
	
	JPanel panel;
	
	public static final int HEIGHT = 800;
	public static final int WIDTH = 600;
	
	public Main() {
		frame = new JFrame("SpaceInvaders");
		panel = new GamePanel();
	}
	
	public void setup() {
		frame.setSize(WIDTH, HEIGHT);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);

	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.setup();
		
	}
	
	
}
