package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PurpleAlien extends Alien{
	public PurpleAlien(float x, float y, BufferedImage img) {
		super(x, y, 60, 45, new Color(179, 79, 249), 40, 5, img);
	}
}

