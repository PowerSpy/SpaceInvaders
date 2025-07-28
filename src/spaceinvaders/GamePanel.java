package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	Player player = new Player(275, 710, 50, 50, Color.PINK);
	Alien alien1 = new Alien(80,  70, 40, 40, Color.GREEN);
	Alien alien2 = new Alien(180, 70, 40, 40, Color.GREEN);
	Alien alien3 = new Alien(280, 70, 40, 40, Color.GREEN);
	Alien alien4 = new Alien(380, 70, 40, 40, Color.GREEN);
	Alien alien5 = new Alien(480, 70, 40, 40, Color.GREEN);
	


	
	public GamePanel() {

	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, getWidth(), getHeight());
		player.draw(g);
		alien1.draw(g);
		alien2.draw(g);
		alien3.draw(g);
		alien4.draw(g);
		alien5.draw(g);
		
	}
}
