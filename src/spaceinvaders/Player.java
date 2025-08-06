package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
	
	public Player(float xPos, float yPos, int w, int h, Color col, float moveSpeed, BufferedImage img) {
		super(xPos, yPos, w, h, col, moveSpeed, img);
	}
	

}
