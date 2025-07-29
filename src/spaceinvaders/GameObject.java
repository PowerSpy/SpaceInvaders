package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

public class GameObject{
	float x;
	float y;
	int width;
	int height;
	Color drawColor;
	float speed;
	
	public GameObject(float xPos, float yPos, int w, int h, Color col, float moveSpeed) {
		width = w;
		height = h;
		x = xPos;
		y = yPos;
		drawColor = col;
		speed = moveSpeed;
	}
	
	public void draw(Graphics g) {
		g.setColor(drawColor);
		g.fillRect((int) x, (int) y,  width, height);
		g.setColor(Color.BLACK);
	}
}
