package spaceinvaders;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	
	Player player = new Player(275, 710, 50, 50, Color.PINK, 300);
	
	Timer redrawTimer;
	
	long lastTime = System.nanoTime();
	float deltaTime;

	ArrayList<Alien> aliens;
	
	ArrayList<Bullet> bullets;
	
	Stack<Bullet> bulletStack;
	
	boolean reloading = false;
	
	public GamePanel() {
		
		bulletStack = new Stack<Bullet>();
		reloadAmmo();
		bullets = new ArrayList<Bullet>();
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
	
	public void reloadAmmo() {
		bulletStack.clear();
		for (int i = 0; i < 6; i++) {
			bulletStack.push(new Bullet(-10, -10, 5, 10, Color.RED, 800));
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		deltaTime = (System.nanoTime() - lastTime) / 1_000_000_000.0f; // Time elapsed in seconds since the last frame
		
		g.setColor(Color.BLACK);
		g.fillRect(0,  0, getWidth(), getHeight());
		
		for(Bullet bullet: bullets) {
			bullet.draw(g);
		}

		player.draw(g);
		
		for(Alien alien: aliens) {
			alien.draw(g);
		}
		
		
		int totalAmmo = 6;
		int bulletDisplayW = 15;
		int bulletDisplayH = 30;
		int spacing = 5;
		
		int startX = getWidth() - (totalAmmo * (bulletDisplayW + spacing)) - 20;
        int startY = 20;
        
        for (int i = 0; i < totalAmmo; i++) {
            if (i < bulletStack.size()) {
                g.setColor(Color.WHITE);
                g.fillRect(startX + i * (bulletDisplayW + spacing), startY, bulletDisplayW, bulletDisplayH);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(startX + i * (bulletDisplayW + spacing), startY, bulletDisplayW, bulletDisplayH);
                g.setColor(Color.WHITE);
                g.drawRect(startX + i * (bulletDisplayW + spacing), startY, bulletDisplayW, bulletDisplayH);
            }
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
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(!reloading && !bulletStack.isEmpty()) {
				Bullet bullet = bulletStack.pop();
				int bulletWidth = 5;
				int bulletHeight = 10;
				bullet.x = player.x + (player.width/2.0f) - (bulletWidth / 2.0f);
				bullet.y = player.y;
				bullets.add(bullet);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			if(!reloading) {
				reloading = true;
				// Set a timer to reload in 1 second
				Timer reloadTimer = new Timer(1000, ev -> {
					reloadAmmo();
					reloading = false;
				});
				reloadTimer.setRepeats(false);
				reloadTimer.start();
			}
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
		
		for(Bullet bullet: bullets) {
			bullet.update(deltaTime);
		}
		
		repaint();
	}
}
