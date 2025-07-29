package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	
	Player player = new Player(275, 710, 50, 50, Color.PINK, 300);
	
	Timer redrawTimer;
	
	long lastTime = System.nanoTime();
	float deltaTime;

	ArrayList<Alien> aliens;
	
	public GamePanel() {
		
		aliens = new ArrayList<Alien>();
		
		redrawTimer = new Timer(1000/60, this);
		redrawTimer.start();
		
		for(int i = 0; i < 5; i++) {
			aliens.add(new Alien(80 + (100 * i), 70, 40, 40, Color.GREEN, 5));
		}
		
		setFocusable(true);
		requestFocusInWindow(true);
		addKeyListener(this);

	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		deltaTime = (System.nanoTime() - lastTime) / 1_000_000_000.0f; // Time elapsed in seconds since the last frame
		
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, getWidth(), getHeight());
		player.draw(g);
		
		for(Alien alien: aliens) {
			alien.draw(g);
		}
		lastTime = System.nanoTime();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		player.update(deltaTime);
		repaint();
	}
}
