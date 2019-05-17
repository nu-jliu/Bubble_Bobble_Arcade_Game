import java.awt.Graphics2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * class that represents the rocks in the map
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Rock extends Item{

	public Rock(Level level, Double centerPoint) {
		super(level, centerPoint);
	}

	@Override
	public void drawOn(Graphics2D g) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("level" + this.level.getLevel() + ".png"));
		} catch (IOException e) {
			System.err.print("Error! Bullet left image not found.");
		}
		int numDraw = Level.BLOCK_SIZE / Level.ITEM_SIZE;
		for (int i = 0; i < numDraw; i++) {
			g.drawImage(img, null, (int) this.centerPoint.getX() + i * Level.ITEM_SIZE, (int) this.centerPoint.getY());
		}
	}

	@Override
	public void updatePosition() {
		// do nothing
		
	}

	@Override
	public void die() {
		// do nothing
		
	}

	@Override
	public void addToLevel() {
		// do nothing
		
	}
}
