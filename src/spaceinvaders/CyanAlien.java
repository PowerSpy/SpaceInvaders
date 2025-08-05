package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class CyanAlien extends Alien{
	public CyanAlien(float x, float y, BufferedImage img) {
		super(x, y, 60, 45, new Color(79, 249, 234), 20, 3, img);
	}
}
