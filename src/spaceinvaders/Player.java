package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	float x;
	float y;
	int width;
	int height;
	Color drawColor;
	
	public Player(float xPos, float yPos, int w, int h, Color col) {
		width = w;
		height = h;
		x = xPos;
		y = yPos;
		drawColor = col;
	}
	
	public void draw(Graphics g) {
		g.setColor(drawColor);
		g.fillRect((int) x, (int) y,  width, height);
		g.setColor(Color.BLACK);
	}
	
	
}
