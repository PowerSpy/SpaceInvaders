package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class GameObject{
	float x;
	float y;
	int width;
	int height;
	Color drawColor;
	float speed;
	
	boolean up = false;
	boolean down = false;
	boolean right = false;
	boolean left = false;
	
	boolean keepInBounds = true;
	boolean isActive;
	
	BufferedImage image = null;
	
	Rectangle2D collider;
	
	public GameObject(float xPos, float yPos, int w, int h, Color col, float moveSpeed, BufferedImage img) {
		width = w;
		height = h;
		x = xPos;
		y = yPos;
		drawColor = col;
		speed = moveSpeed;
		
		isActive = true;
		collider = new Rectangle2D.Double(x, y, width, height);
		
		if (img != null) {
			try {
				img.getRGB(0, 0);
				image = img;
			} catch(Exception e) {
				System.out.println("Image Load Error: " + e.getMessage());
				image = null;
			}
		}
	}
	
	public void draw(Graphics g) {
		if (image != null) {
			try {
				g.drawImage(image, (int) x, (int) y, width, height, null);
			} catch(Exception e) {
				System.out.println("Error Drawing Image: " + e.getMessage());
				fallbackDraw(g);
			}
		}
		else {
			fallbackDraw(g);
		}
	}
	
	public void fallbackDraw(Graphics g) {
		g.setColor(drawColor);
		g.fillRect((int) x, (int) y,  width, height);
		g.setColor(Color.BLACK);
	}
	
	public void update(float deltaTime) {
		if (up) {
		    y -= speed * deltaTime;
		    if(keepInBounds && y < 0) y = 0;
		}
		if (down) {
		    y += speed * deltaTime;
		    if(keepInBounds && y + height > 770) y = 770 - height;
		}

		if (left) {
		    x -= speed * deltaTime;
		    if(keepInBounds && x < 0) x = 0;
		}
		if (right) {
		    x += speed * deltaTime;
		    if(keepInBounds && x + width > 600) x = 600 - width;
		}
		
		collider.setRect(x, y, width, height);
	}
}
