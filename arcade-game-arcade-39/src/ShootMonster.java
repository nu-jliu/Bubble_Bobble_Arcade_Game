import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Monster that is able to shot
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class ShootMonster extends Monster{

	private int timeInterval;
	private boolean fireReady;
	private BufferedImage leftImg;
	private BufferedImage rightImg;
	private BufferedImage leftTrapped = null;
	private BufferedImage rightTrapped = null;
	
	public ShootMonster(Level level, Double centerPoint) {
		super(level, centerPoint);
		this.timeInterval=50;
		this.fireReady=true;
		this.isMovingLeft=true;
		try {
			leftImg = ImageIO.read(new File("shootmonsterleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Shoot monster left image not found.");
		}
		try {
			rightImg = ImageIO.read(new File("shootmonsterright.png"));
		} catch (IOException e) {
			System.err.print("Error! Shoot monster right image not found.");
		}
		try {
			rightTrapped = ImageIO.read(new File("shoottrappedright.png"));
		} catch (IOException e) {
			System.err.print("Error! Shoot monster right trapped image not found.");
		}
		try {
			leftTrapped = ImageIO.read(new File("shoottrappedleft.png"));
		} catch (IOException e) {
			System.err.print("Error! Shoot monster left trapped image not found.");
		}
	}
	
	public void updateTime() {
		if (this.timeInterval>=0) {
			this.timeInterval--;
			this.fireReady=false;
		} else {
			this.timeInterval=50;
			this.fireReady=true;
		}
	}
	
	@Override
	public void fire() {
		this.updateTime();
		if (this.fireReady && !this.isFloating) {
			Point2D.Double point = new Point2D.Double(centerPoint.getX()+Level.ITEM_SIZE/2,centerPoint.getY()+Level.ITEM_SIZE/2);
			Bullet newBullet = new Bullet(level,point,this.isMovingLeft);
			newBullet.addToLevel();
		}
	}
	
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
		// do nothing
		
	}

	@Override
	public void addToLevel() {
		// do nothing
		
	}
}
