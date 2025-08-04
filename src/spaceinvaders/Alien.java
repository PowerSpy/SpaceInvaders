package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Alien extends GameObject{
	
	int direction;
	float health;
	
	public Alien(float xPos, float yPos, int w, int h, Color col, float moveSpeed, float healthVal, BufferedImage img) {
		super(xPos, yPos, w, h, col, moveSpeed, img);
		
		down = true;
		
		health = healthVal;
		
		if(Math.random() > 0.5) direction = 1;
		else direction = -1;
	}
	
}

