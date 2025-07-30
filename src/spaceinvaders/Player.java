package spaceinvaders;

import java.awt.Color;

public class Player extends GameObject{
	
	boolean up = false;
	boolean down = false;
	boolean right = false;
	boolean left = false;
	
	public Player(float xPos, float yPos, int w, int h, Color col, float moveSpeed) {
		super(xPos, yPos, w, h, col, moveSpeed);
	}
	
	public void update(float deltaTime) {
		if (up) {
		    y -= speed * deltaTime;
		    if(y < 20) y = 20;
		}
		if (down) {
		    y += speed * deltaTime;
		    if(y + height > 770) y = 770 - height;
		}

		if (left) {
		    x -= speed * deltaTime;
		    if(x < 0) x = 0;
		}
		if (right) {
		    x += speed * deltaTime;
		    if(x + width > 600) x = 600 - width;
		}
	}
}
