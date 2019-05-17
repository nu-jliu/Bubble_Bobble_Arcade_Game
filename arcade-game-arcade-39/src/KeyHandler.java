import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * handles the keyboard input
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class KeyHandler implements KeyListener {

	private Hero hero;
	private Level level;
	private JFrame frame;
	
	public KeyHandler(Level level, JFrame frame) {
		this.level = level;
		this.hero = this.level.hero;
		this.frame = frame;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_RIGHT) {
			hero.keyPressed();
			hero.moveRight();
		}
		else if (code == KeyEvent.VK_LEFT) {
			hero.keyPressed();
			hero.moveLeft();	
		} 
		else if (code == KeyEvent.VK_UP) {
			hero.jump();
		}
		else if (code == KeyEvent.VK_SPACE) {
			hero.setFire();
		}
		else if (code == KeyEvent.VK_U) {
			try {
				this.level.nextLevel();
			} catch (IllegalAccessException e1) {
				System.err.println("Error in changing the level!!!");
			}
		}
		else if (code == KeyEvent.VK_D) {
			try {
				this.level.previousLevel();
			} catch (IllegalAccessException e1) {
				System.err.println("Error in changing the level!!!");
			}	
		}
		else if (code == KeyEvent.VK_P) {
			this.level.isPaused();
		} 
		else if (code == KeyEvent.VK_S) {
			if (!this.level.isStarted())
				this.level.startGame();
		}
		else if (code == KeyEvent.VK_ESCAPE) {
			if (this.level.isStarted())
				this.level.endGame();
		}
		else if (code == KeyEvent.VK_E) 
			System.exit(0);
		frame.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			hero.keyNotPressed();
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			hero.setNotFire();	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
}
