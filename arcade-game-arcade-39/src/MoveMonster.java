import java.awt.Graphics2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * monster that is able to move
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class MoveMonster extends Monster{
	
	private BufferedImage leftImg = null;
	private BufferedImage rightImg = null;
	private BufferedImage leftTrapped = null;
	private BufferedImage rightTrapped = null;

	public MoveMonster(Level level, Double centerPoint) {
		super(level, centerPoint);
		try {
		    leftImg = ImageIO.read(new File("movemonsterleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Move monster left image not found.");
		}
		try {
		    rightImg = ImageIO.read(new File("movemonsterright.png"));
		} catch (IOException e) {
			System.err.print("Error! Move monster right image not found.");
		}
		try {
			rightTrapped = ImageIO.read(new File("movetrappedright.png"));
		} catch (IOException e) {
			System.err.print("Error! Move monster right trapped image not found.");
		}
		try {
			leftTrapped = ImageIO.read(new File("movetrappedleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Move monster left trapped image not found.");
		}
	}

	// draw the monster
	@Override
	public void drawOn(Graphics2D g) {
		if (this.isMovingLeft && !this.isFloating) {
			g.drawImage(leftImg, null,(int)centerPoint.getX(),(int)centerPoint.getY());
		} else if (!this.isMovingLeft && !this.isFloating){
			g.drawImage(rightImg, null,(int)centerPoint.getX(),(int)centerPoint.getY());
		} else if (this.isMovingLeft && this.isFloating){
			g.drawImage(leftTrapped, null,(int)centerPoint.getX(),(int)centerPoint.getY());
		} else {
			g.drawImage(rightTrapped, null,(int)centerPoint.getX(),(int)centerPoint.getY());
		}
	}

	@Override
	public void die() {
		//do nothing
		
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
