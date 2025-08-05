package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RedAlien extends Alien{
	public RedAlien(float x, float y, BufferedImage img) {
		super(x, y, 60, 45, new Color(216, 46, 46), 35, 1, img);
	}
}
