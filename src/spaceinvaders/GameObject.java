package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class GameObject{
	float x;
	float y;
	int width;
	int height;
	Color drawColor;
	float speed;
	boolean isActive;
	
	Rectangle2D collider;
	
	public GameObject(float xPos, float yPos, int w, int h, Color col, float moveSpeed) {
		width = w;
		height = h;
		x = xPos;
		y = yPos;
		drawColor = col;
		speed = moveSpeed;
		isActive = true;
		
		collider = new Rectangle2D.Double(x, y, w, h);
	}
	
	public void draw(Graphics g) {
		g.setColor(drawColor);
		g.fillRect((int) x, (int) y,  width, height);
		g.setColor(Color.BLACK);
	}
}
