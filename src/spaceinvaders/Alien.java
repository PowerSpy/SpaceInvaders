package spaceinvaders;

import java.awt.Color;

public class Alien extends GameObject{
	
	int direction;
	
	public Alien(float xPos, float yPos, int w, int h, Color col, float moveSpeed) {
		super(xPos, yPos, w, h, col, moveSpeed);
		
		down = true;
		
		if(Math.random() > 0.5) direction = 1;
		else direction = -1;
	}
	
}

