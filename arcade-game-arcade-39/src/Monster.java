import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

/**
 * class that represents all monsters
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public abstract class Monster extends Character{
	
	protected boolean isFloating;
	protected boolean isTrapped;
	private StopWatch timer;
	
	public Monster(Level level, Double centerPoint) {
		super(level, centerPoint);
		this.isFloating = false;
		this.timer = new StopWatch();
		this.speed=Level.MONSTER_SPEED;
		this.isTrapped=false;
	}

	// keep the monster constantly moving
	@Override
	public void shift() {
		double x = this.centerPoint.getX();
		if (x <= -10) { // the -10 here is for adjustment
			x = -10;
			this.speed=-speed;
		} else if (x >= 785 - Level.ITEM_SIZE) {
			x = 785 - Level.ITEM_SIZE;
			this.speed=-speed;
		}
		x+=speed;
		this.moveTo(new Point2D.Double(x, centerPoint.getY()));
		if (this.speed>0) {
			this.isMovingLeft=false;
		}
		else {
			this.isMovingLeft=true;
		}
	}
	
	// random automatic jump
	@Override
	public void jump() {
		double num=Math.random()*100;
		if (this.onPlatform && num<=1) {
			this.gravity = -15;
			this.onPlatform = false;
			this.isMovingUp = true;
		}
	}
	
	// move up is after trapped by bubble
	public void moveUp() {
		this.gravity = -5;
		double y = this.centerPoint.getY();
		this.moveTo(new Point2D.Double(this.centerPoint.getX(), y + this.gravity));
		if (y <= 0)
			this.moveTo(new Point2D.Double(this.centerPoint.getX(), 0));
	}
	
	public void floats() {
		this.isTrapped=true;
		this.onPlatform = false;
		this.isFloating = true;
		this.timer.start();
	}
	
	public void checkTime() {
		this.timer.stop();
		if (this.timer.getDuration() > 7500) {
			this.isFloating = false;
			this.isTrapped=false;
			this.timer.reset();
		}
	}
	
	@Override
	public void returnToStart() {
		super.returnToStart();
		this.isFloating = false;
		this.isTrapped = false;
		this.timer.reset();
	}

	// the overall method that updates the monster's position
	@Override
	public void updatePosition() {
		if (this.isFloating) {
			this.checkTime();
			this.moveUp();
			return;
		}
		this.fall();
		this.shift();
		this.jump();
		ArrayList<Rock> rocks = level.getRocks();
		for (Rock r : rocks) {
			if(this.onPlatform(r))
				break;
		}
	}
}
