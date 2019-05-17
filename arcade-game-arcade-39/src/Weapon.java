import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * weapon of the shoot monsters
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Weapon extends Item{

	protected boolean left;
	
	public Weapon(Level level, Double centerPoint, boolean left) {
		super(level, centerPoint);
		this.left=left;
	}

	public void updatePosition() {
		double x= this.centerPoint.getX();
		double y= this.centerPoint.getY();
		if (left) {
			x-=Level.B_SPEED;
			this.moveTo(new Point2D.Double(x,y));
		}
		else {
			x+=Level.B_SPEED;
			this.moveTo(new Point2D.Double(x,y));
		}
		if (x<0 || x>800) {
			level.bulletAndBubblesRemove.add(this);
		}
	}
	
	@Override 
	public void addToLevel() {
		this.level.bulletAndBubbles.add(this);
	}

	@Override
	public void drawOn(Graphics2D g2) {
		// do nothing
		
	}

	@Override
	public void die() {
		// do nothing
		
	}
}
