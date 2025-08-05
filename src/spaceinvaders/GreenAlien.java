package spaceinvaders;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GreenAlien extends Alien{
	public GreenAlien(float x, float y, BufferedImage img) {
		super(x, y, 60, 45, new Color(117, 254, 2), 20, 1, img);
	}
}
