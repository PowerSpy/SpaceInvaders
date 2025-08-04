package spaceinvaders;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
	
	Player player = new Player(275, 710, 50, 50, Color.PINK, 300);
	
	Timer redrawTimer;
	Timer alienRandomTimer;
	
	long lastTime = System.nanoTime();
	float deltaTime;

	ArrayList<Alien> aliens;
	
	ArrayList<Bullet> bullets;
	
	Stack<Bullet> bulletStack;
	
	boolean reloading = false;
	
	int score;
	
	BufferedImage alienImageGreen;
	
	public GamePanel() {
		
		score = 0;		
		
		bulletStack = new Stack<Bullet>();
		reloadAmmo();
		bullets = new ArrayList<Bullet>();
		aliens = new ArrayList<Alien>();
		
		
		try {
			alienImageGreen = ImageIO.read(new File("SpaceInvadersSprites/alien_sprite_green.png"));
		} catch (IOException e) {
			System.out.println("Failed to load alien image green: " + e.getMessage());
		}
		
		redrawTimer = new Timer(1000/60, this);
		redrawTimer.start();
		
		alienRandomTimer = new Timer(1000/10, ev -> {
			randomlySwitchAlienDirections();
		});
		
		alienRandomTimer.start();
		
		for(int i = 0; i < 5; i++) {
			aliens.add(new Alien(80 + (100 * i), 70, 60, 45, Color.GREEN, 20, 1, alienImageGreen));
		}
		
		setFocusable(true);
		requestFocusInWindow(true);
		addKeyListener(this);

	}
	
	public void reloadAmmo() {
		bulletStack.clear();
		for (int i = 0; i < 6; i++) {
			bulletStack.push(new Bullet(-10, -10, 5, 10, Color.RED, 800, 1));
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
		
		g.setColor(Color.WHITE);
		
		g.drawLine((int) (player.x + (player.width / 2.0f)), (int) player.y, (int) (player.x + (player.width / 2.0f)), 0);

		
		
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
        
        drawScore(g);
				
		lastTime = System.nanoTime();
		
	}
	
	public void randomlySwitchAlienDirections() {
		for(Alien alien: aliens) {
			if(!alien.isActive) continue;
			
			if(Math.random() < 0.1) {
				alien.direction *= -1;
			}
		}
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
	
	public void drawScore(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
		g.drawString("Score: " + score, 10, 30);
	}
	
	public void checkBulletCollision() {

		for(Bullet bullet: bullets) {
			for(Alien alien: aliens) {
				if(bullet.collider.intersects(alien.collider)) {
					alien.health -= bullet.damage;
					if(alien.health <= 0) {
						alien.isActive = false;
					}
					bullet.isActive = false;
					score++;
				}
			}
		}
	}
	
	public void moveAliensRandomly(float deltaTime) {
		for(Alien alien: aliens) {
			if(!alien.isActive) continue;
			
			float originalX = alien.x;
			
			float moveAmount = alien.speed/4 * deltaTime * alien.direction;
			
			alien.x += moveAmount;
			alien.collider.setRect(alien.x - 5, alien.y, alien.width + 10, alien.height);
			
			boolean overlaps = false;
			for(Alien other: aliens) {
				if(other != alien && other.isActive && alien.collider.intersects(other.collider)) {
					overlaps = true;
					break;
				}
			}
			
			alien.collider.setRect(alien.x, alien.y, alien.width, alien.height);
			
			if (overlaps || alien.x < 0 || alien.x + alien.width > 600) {
				alien.direction *= -1;
				alien.x = originalX;
				alien.collider.setRect(alien.x, alien.y, alien.width, alien.height);
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
		
		checkBulletCollision();
		player.update(deltaTime);
		
		Iterator<Bullet> iterBullet = bullets.iterator();
		
		while(iterBullet.hasNext()) {
			Bullet bullet = iterBullet.next();
			bullet.checkOutOfBounds();
			if(!bullet.isActive) {
				iterBullet.remove();
			}
			else {
				bullet.update(deltaTime);
			}
		}
		
		Iterator<Alien> iterAlien = aliens.iterator();
		
		while(iterAlien.hasNext()) {
			Alien alien = iterAlien.next();
			if(!alien.isActive) {
				iterAlien.remove();
			}
			else {
				moveAliensRandomly(deltaTime);
				alien.update(deltaTime);
			}
		}
		
		
		repaint();
	}
}
