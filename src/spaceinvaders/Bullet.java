package spaceinvaders;

import java.awt.Color;

public class Bullet extends GameObject{
	
	float damage;

	public Bullet(float xPos, float yPos, int w, int h, Color col, float moveSpeed, float dmg) {
		super(xPos, yPos, w, h, col, moveSpeed, null);
		
		up = true;
		keepInBounds = false;
		damage = dmg;
	}
	
	public void checkOutOfBounds() {
		if(y + height < 0) {
			isActive = false;
		}
	}
	
}
