import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;


/**
 * class represents all characters of the game
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public abstract class Character extends Item {

	protected boolean onPlatform;
	protected boolean isMovingUp;
	protected boolean isMovingLeft;
	protected double gravity;
	protected double speed;
	protected Point2D.Double startPosition;

	public Character(Level level, Double centerPoint) {
		super(level, centerPoint);
		this.onPlatform = false;
		this.isMovingUp = false;
		this.isMovingLeft=false;
		this.gravity=10;
		this.startPosition = centerPoint;
	}

	/**
	 * reset the onPlatform boolean based on rocks
	 * @param r the rock
	 * @return whether it is on the platform
	 */
	public boolean onPlatform(Rock r) {
		double height = r.centerPoint.getY();
		double width =  r.centerPoint.getX();
		double x = this.centerPoint.getX();
		double y = this.centerPoint.getY();
		if (x > width-Level.ITEM_SIZE && x < width+ Level.BLOCK_SIZE && !this.isMovingUp) {
			if (y + Level.ITEM_SIZE >= height-10 && y + Level.ITEM_SIZE <= height + Level.ITEM_SIZE-10) { // the 10 here is abjustment 
				this.onPlatform = true;
				this.gravity = 10;
				this.moveTo(new Point2D.Double(x, height - Level.ITEM_SIZE-10));
				return true;
			}
		} 
		this.onPlatform=false;
		return false;
	}

	/**
	 *  the gravity can be either positive or negative based on the direction of movement
	 */
	public void fall() {
		double x = this.centerPoint.getX();
		double y = this.centerPoint.getY();
		// reset gravity when touch the top
		if (y<=0) {
			gravity=10;
		}
		// reset position and gravity when touch the bottom
		if (y > 600 - Level.ITEM_SIZE - 55) { // 55 here is tp cancel the effect of windows
			y=10;
			this.gravity=10;
		}
		// update isMovingUp
		if (this.gravity > 0) {
			this.isMovingUp = false;
		}
		// make the movement based on gravity
		y = y + this.gravity;
		this.gravity += 1;
		this.moveTo(new Point2D.Double(x, y));
	}

	/**
	 *  the jump can only occur when the character is on platform, and it reverse the gravity
	 */
	public void jump() {
		if (this.onPlatform) {
			this.gravity = -15;
			this.onPlatform = false;
			this.isMovingUp = true;
		}
	}
	
	/** 
	 * control the horizontal movement
	 */
	public void shift() {
		double x = this.centerPoint.getX();
		x+=speed;
		if (x <= 0) {
			x = 0;
		} else if (x >= 785 - Level.ITEM_SIZE) {
			x = 785 - Level.ITEM_SIZE;
		}
		this.moveTo(new Point2D.Double(x, centerPoint.getY()));
	}
	
	/**
	 * return to the start position
	 */
	public void returnToStart() {
		this.moveTo(startPosition);
	}
	
	public abstract void fire();
}
