package spaceinvaders;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RadialGradientPaint;
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
	
	Player player;
	
	Timer redrawTimer;
	Timer alienRandomTimer;
	Timer alienSpawnTimer;
	
	long lastTime;
	float deltaTime;

	ArrayList<Alien> aliens;
	
	ArrayList<Bullet> bullets;
	
	Stack<Bullet> bulletStack;
	
	boolean reloading = false;
	
	int score;
	int lives;
	
	BufferedImage alienImageGreen;
	BufferedImage alienImageRed;
	BufferedImage alienImageCyan;
	BufferedImage alienImagePurple;
	
	BufferedImage playerImage;
	
	
	int gameState;
	
	static final int MENU = 0;
	
	static final int GAME = 1;
	
	static final int GAMEOVER = 2;
	
	public GamePanel() {
		
		
		
		
		playerImage = loadImg("SpaceInvadersSprites/player.png", "player");
		alienImageGreen = loadImg("SpaceInvadersSprites/alien_sprite_green.png", "green alien");		
		alienImageRed = loadImg("SpaceInvadersSprites/alien_sprite_red.png", "red alien");	
		alienImageCyan = loadImg("SpaceInvadersSprites/alien_sprite_cyan.png", "cyan alien");	
		alienImagePurple = loadImg("SpaceInvadersSprites/alien_sprite_purple.png", "purple alien");	
		
				
		redrawTimer = new Timer(1000/60, this);
		redrawTimer.start();
		
		
		
		
		
		setFocusable(true);
		requestFocusInWindow(true);
		addKeyListener(this);

	}
	
	public void startGame() {
		
		gameState = MENU;
		
		score = 0;	
		lives = 3;
		
		bulletStack = new Stack<Bullet>();
		reloadAmmo();
		bullets = new ArrayList<Bullet>();
		aliens = new ArrayList<Alien>();
		
		player = new Player(275, 710, 50, 50, Color.PINK, 300, playerImage);
		spawnAliens(score);
		
		alienRandomTimer = new Timer(1000/10, ev -> {
			randomlySwitchAlienDirections();
		});
		
		alienRandomTimer.start();
		
		alienSpawnTimer = new Timer(1000 * 10, ev -> {
			spawnAliens(score);
		});
		
		alienSpawnTimer.start();
		
		lastTime = System.nanoTime();
		
	}
	
	public BufferedImage loadImg(String filePath, String name) {
		
		BufferedImage img;
		
		try {
			img = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.out.println("Failed to load " + name + ":" + e.getMessage());
			img = null;
		}
		
		
		return img;
		
	}
	
	public void reloadAmmo() {
		bulletStack.clear();
		for (int i = 0; i < 6; i++) {
			bulletStack.push(new Bullet(-10, -10, 5, 10, Color.RED, 800, 1));
		}
	}
	
	
	public void spawnAliens(int score) {
		for(int i = 0; i < 5; i++) {
			
			int x = 80 + (100 * i);
			int y = 70;
			
			Alien alien = createAlienBasedOnScore(x, y, score);
			aliens.add(alien);
		}
	}
	
	public Alien createAlienBasedOnScore(int x, int y, int score) {
		int clampedScore = Math.min(Math.max(score, 0), 100);
		
		int greenWeight;
		int redWeight;
		int cyanWeight;
		int purpleWeight;
		
		if (clampedScore < 25) {
			greenWeight = 80;
			redWeight = 8;
			cyanWeight = 8;
			purpleWeight = 4;
		}
		else if(clampedScore < 75) {
			greenWeight = 20;
			redWeight = 35;
			cyanWeight = 35;
			purpleWeight = 10;
		}
		else {
			greenWeight = 10;
			redWeight = 20;
			cyanWeight = 20;
			purpleWeight = 50;
		}
		
		
		int rand = (int) (Math.random() * 100);
		
		if (rand < greenWeight) {
			return new GreenAlien(x,  y, alienImageGreen);
		}
		else if (rand < redWeight + greenWeight) {
			return new RedAlien(x, y, alienImageRed);
		}
		else if (rand < cyanWeight + redWeight + greenWeight) {
			return new CyanAlien(x, y, alienImageCyan);
		}
		else {
			return new PurpleAlien(x, y, alienImagePurple);
		}
		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		if(gameState == MENU) {
			Menu(g);
		}
		else if(gameState == GAME) {
			GameLoop(g);
		}
		else {
			GameOver(g);
		}
		
	}
	
	public void Menu(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("SPACE INVADERS", 80, 200);
		
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Press ENTER to Start!", 170, 300);
		
	}
	public void GameLoop(Graphics g) {
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
        drawLives(g);
				
		lastTime = System.nanoTime();
	}
	
	public void GameOver(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString("GAME OVER", 150, 200);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Score: " + score, 250, 300);
		g.drawString("Press ENTER to return to MENU", 110, 350);
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

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == MENU) {
				startGame();
				gameState = GAME;
			}
			else if(gameState == GAMEOVER) {
				gameState = MENU;
			}
		}
		if (gameState == GAME) {
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
		
	}
	
	public void drawScore(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		
		g.drawString("Score: " + score, 10, 30);
	}
	
	public void drawLives(Graphics g) {
		int radius = 10;
		int spacing = 5;
		int startX = 10;
		int startY = 40;
		
		g.setColor(Color.RED);
		
		for(int i = 0; i < lives; i ++) {
			g.fillOval(startX + i * (radius * 2 + spacing), startY, radius * 2, radius * 2);
		}
		
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
	
	public void checkLoss() {
		for(Alien alien: aliens) {
			if(alien.y + alien.height >= 770 ) {
				alien.isActive = false;
				lives--;
			}
		}
		
		
		if(lives <= 0) {
			gameState = GAMEOVER;					
		}
		
		
		
	}
	
	public void updateGameLoop() {
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
		
		checkLoss();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(gameState == GAME) {
			updateGameLoop();
		}
		
		
		repaint();
	}
}
