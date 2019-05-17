import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * the bullet for monsters
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Bullet extends Weapon {

	private BufferedImage leftImg;
	private BufferedImage rightImg;
	
	public Bullet(Level level, Double centerPoint, boolean left) {
		super(level, centerPoint, left);
		try {
		    leftImg = ImageIO.read(new File("bulletleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Bullet left image not found.");
		}
		try {
		    rightImg = ImageIO.read(new File("bulletright.png"));
		} catch (IOException e) {
			System.err.print("Error! Bullet right image not found.");
		}
	}
	
	
	/**
	 *  draw the bullet based on direction 
	 */
	@Override
	public void drawOn(Graphics2D g) {
		if (this.left) {
			g.drawImage(leftImg, null, (int)this.centerPoint.getX(), (int)this.centerPoint.getY());
		} else {
			g.drawImage(rightImg, null, (int)this.centerPoint.getX(), (int)this.centerPoint.getY());
		}
	}
		
	/**
	 *  update collision with the hero
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		Hero hero = this.level.hero;
		if (this.collideWith(hero)) {
			hero.die();
			level.bulletAndBubblesRemove.add(this);
		}
	}
	
	/**
	 *  deal with collision with hero
	 * @param hero the hero
	 * @return weather it collides with the hero
	 */
	public boolean collideWith(Hero hero) {
		Point2D.Double bulletLocation = this.centerPoint;
		Point2D.Double heroLocation = hero.centerPoint;
		double xB = bulletLocation.getX();
		double yB = bulletLocation.getY();
		double xC = heroLocation.getX();
		double yC = heroLocation.getY();
		if (xB >= xC - Level.BUBBLE_SIZE && xB <= xC + Level.ITEM_SIZE) {
			if (yB >= yC - Level.BUBBLE_SIZE && yB <= yC + Level.ITEM_SIZE) {
				return true;
			}
		}
		return false;
	}
}
