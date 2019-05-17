import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * the class represents the bubble shot by hero
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Bubble extends Weapon {
	
	private BufferedImage img;
	
	public Bubble(Level level, Double centerPoint, boolean left) {
		super(level, centerPoint, left);
		try {
			img = ImageIO.read(new File("bubble.png"));
		} catch (IOException e) {
			System.err.print("Error! Bubble image not found.");
		}
	}
	
	/**
	 * draw the bubble
	 */
	@Override
	public void drawOn(Graphics2D g) {
		g.drawImage(img, null, (int)this.centerPoint.getX()+Level.ITEM_SIZE/2, (int)this.centerPoint.getY()+25);
	}
	
	/**
	 * update the positions of the bubble
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		for (Monster m : this.level.getMonsters()) {
			if (this.collideWith(m)) {
				m.floats();
				level.bulletAndBubblesRemove.add(this);
			}
		}
	}
	
	/**
	 * deal collisions with monsters
	 * @param m monster
	 * @return weather it collides with the monster
	 */
	public boolean collideWith(Monster m) {
		Point2D.Double bubbleLocation = this.centerPoint;
		Point2D.Double monsterLocation = m.centerPoint;
		double xB = bubbleLocation.getX();
		double yB = bubbleLocation.getY();
		double xC = monsterLocation.getX();
		double yC = monsterLocation.getY();
		if (xB >= xC - Level.BUBBLE_SIZE && xB <= xC + Level.ITEM_SIZE) {
			if (yB >= yC - Level.BUBBLE_SIZE && yB <= yC + Level.ITEM_SIZE) {
				return true;
			}
		}
		return false;
	}
}
