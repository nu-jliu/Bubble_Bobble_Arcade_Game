import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * class that represents the items in the class
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public abstract class Item{
	
	protected Level level;
	protected Point2D.Double centerPoint; // the centerpoint is the left up cornor of an item.
	
	public Item(Level level, Point2D.Double centerPoint) {
		this.level = level;
		this.centerPoint = centerPoint;
	}
	
	public void moveTo(Point2D.Double point) {
		this.centerPoint = point;
	}
	
	public abstract void updatePosition();
	
	public abstract void drawOn(Graphics2D g2);
	
	public abstract void die();
	
	public abstract void addToLevel();
}
