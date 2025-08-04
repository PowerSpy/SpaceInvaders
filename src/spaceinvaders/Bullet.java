package spaceinvaders;

import java.awt.Color;

public class Bullet extends GameObject{

	public Bullet(float xPos, float yPos, int w, int h, Color col, float moveSpeed) {
		super(xPos, yPos, w, h, col, moveSpeed);
		
		up = true;
		keepInBounds = false;
	}
	
	public void checkOutOfBounds() {
		if(y + height < 0) {
			isActive = false;
		}
	}
	
}
