import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * components of the game
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class LevelComponent extends JComponent{

	private Level level;
	private boolean isEnded;
	private Image backgdound;
	
	public LevelComponent(Level level) {
		this.level = level;
		this.isEnded = false;
		
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		this.backgdound = tk.createImage("opening_background.gif");
		
		// read from the file and store the informations of each level
		for (int i = 0; i < Level.MAX_LEVEL; i++) {
			File file = new File("level" + (i + 1) + ".txt");
			Scanner sc = null;
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e1) {
				System.err.println("Level file" + (i + 1) + " not found");
				continue;
			}
			while(sc.hasNext()) {
				String input = sc.nextLine();
				String[] location = input.split(", ");
				Rock rock = new Rock(this.level, new Point2D.Double(Double.parseDouble(location[0]), Double.parseDouble(location[1])));
				level.addRock(i, rock);
			}
			sc.close();
			this.level.loadMonsters();
		}
		
			
		// create a timer to update all methods
		Timer timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!level.getPaused()) {
					level.updateMonsterAndChracter();
					level.updateBulletAndBubble();
					level.updateFruit();
					level.checkLife();
					if (level.getMonsters().size() <= 0)
						try {
							level.nextLevel();
						} catch (IllegalAccessException e1) {
							level.isPaused();
							isEnded = true;
						}
					repaint();
				}
			}
		});
		timer.start();
	}
	
	/**
	 * paint the audio page
	 * @param g2
	 */
	public void paintStartingPage(Graphics2D g2) {
		g2.drawImage(this.backgdound, 0, 0, null);
		g2.setColor(Color.GREEN);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		g2.drawString("Bubble Bubble", 210, 90);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.drawString("PRESS S TO START THE GAME", 185, 550);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (!this.level.isStarted()) {
			this.paintStartingPage(g2);
		}
		else {
			Image background = this.level.getBackground();
			g.drawImage(background, 0, 0, this);
			ArrayList<Rock> rocks = this.level.getRocks();
			for (Rock r : rocks) {
					r.drawOn(g2);
			}
			for (Character c: level.characters) {
				c.drawOn(g2);
			}
			for (Weapon w: level.bulletAndBubbles) {
				w.drawOn(g2);
			}
			for (Fruit f: level.fruits) {
				f.drawOn(g2);
			}
			g2.setColor(Color.MAGENTA);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			String paused = (this.level.getPaused() ^ this.isEnded) ? "Paused!!!" : "";
			String finalMessage = "Congratulations!!!";
			g2.drawString("Level: " + this.level.getLevel(), 0, 25);
			g2.setColor(Color.BLACK);
			g2.drawString("Life: " + this.level.hero.getLife(), 650, 25);
			g2.drawString("Score: " + this.level.hero.getScore(), 650, 50);
			g2.drawString(paused, 0, 50);
			if (this.isEnded) {
				g2.setColor(Color.MAGENTA);
				g2.fillRect(250, 370, 300, 60);
				g2.setColor(Color.BLUE);
				g2.setFont(new Font(finalMessage, Font.PLAIN, 30));
				g2.drawString(finalMessage, 270, 415);
			}
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime time = LocalDateTime.now();  
		String dateAndTime = formatter.format(time);
		g2.setColor(Color.BLUE);
		g2.drawString(dateAndTime, 250, 30);
	}
}
