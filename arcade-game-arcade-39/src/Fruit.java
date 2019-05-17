import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * class of fruits
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Fruit extends Character{

	private BufferedImage img;
	
	public Fruit(Level level, Double centerPoint) {
		super(level, centerPoint);
		try {
			img = ImageIO.read(new File("fruit.png"));
		} catch (IOException e) {
			System.err.print("Error! Fruit image not found.");
		}
	}

	// draw the fruit
	@Override
	public void drawOn(Graphics2D g) {
		g.drawImage(img, null, (int)this.centerPoint.getX(), (int)this.centerPoint.getY());
	}
	
	// deal with collision with hero
	public boolean collideWith(Hero hero) {
		Point2D.Double fruitLocation = this.centerPoint;
		Point2D.Double heroLocation = level.hero.centerPoint;
		double xB = fruitLocation.getX();
		double yB = fruitLocation.getY();
		double xC = heroLocation.getX();
		double yC = heroLocation.getY();
		if (xB >= xC - Level.ITEM_SIZE && xB <= xC + Level.ITEM_SIZE) {
				if (yB >= yC - Level.ITEM_SIZE && yB <= yC + Level.ITEM_SIZE) {
					return true;
				}
		}
		return false;
	}
	
	// update position and collision with hero
	@Override
	public void updatePosition() {
		this.fall();
		ArrayList<Rock> rocks = level.getRocks();
		for (Rock r : rocks) {
			if(this.onPlatform(r))
				break;
		}
		if (this.collideWith(level.hero)) {
			level.hero.addScore();
			level.fruitsRemove.add(this);
		}
	}

	@Override
	public void die() {
		// do nothing
		
	}

	@Override
	public void addToLevel() {
		// do nothing
		
	}

	@Override
	public void fire() {
		// do nothing
		
	}
}
	

