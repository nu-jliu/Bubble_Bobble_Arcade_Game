import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * class represents the level
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class Level{

	public final static int HEIGHT=600; 
	public final static int WIDTH=800;
	public final static int BLOCK_SIZE=210; // the width of rock blocks in the level
	public final static int ITEM_SIZE=30; // the size of hero, monster, and rock height
	public final static double HERO_SPEED=15; // the change in position when a key is pressed
	public final static double MONSTER_SPEED=5; // monster speed
	public final static double B_SPEED=15; // the speed of bullet and bubble, stand for B
	public static final int MAX_LEVEL = 3; // the number of level
	public static final int BUBBLE_SIZE = 20; // the size of the bubble before the collision
	
	private int level;
	private Clip clip;
	private Clip opening;
	private HashMap<Integer, ArrayList<Rock>> rocks;
	private HashMap<Integer, Image> backgrounds;
	private HashMap<Integer, Clip> bgm;
	private ArrayList<Monster> monsters;
	private ArrayList<Monster> monstersRemove;
	private boolean isPaused;
	private boolean isStarted;
	
	public Hero hero;
	public ArrayList<Character> characters;
	public ArrayList<Character> charactersRemove= new ArrayList<Character>(); 
	public ArrayList<Weapon> bulletAndBubbles= new ArrayList<Weapon>();
	public ArrayList<Weapon> bulletAndBubblesRemove= new ArrayList<Weapon>();
	public ArrayList<Fruit> fruits= new ArrayList<Fruit>();
	public ArrayList<Fruit> fruitsRemove= new ArrayList<Fruit>();
	

	public Level() {
		this.rocks = new HashMap<Integer, ArrayList<Rock>>();
		this.backgrounds = new HashMap<Integer, Image>();
		this.bgm = new HashMap<Integer, Clip>();
		
		try {
			AudioInputStream open = AudioSystem.getAudioInputStream(new File("opening.wav").getAbsoluteFile());
			this.opening = AudioSystem.getClip();
			this.opening.open(open);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			System.err.println("Error in loading the audio files!!!");
		}
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		for (int i = 0; i < MAX_LEVEL; i++) {
			this.rocks.put(i, new ArrayList<Rock>());
			Image img = tk.getImage("background_img_" + (i + 1) + ".gif");
			this.backgrounds.put(i, img);
			Clip clip = null;
			try {
				AudioInputStream audio = AudioSystem.getAudioInputStream(new File("bgm_level_" + (i + 1) + ".wav").getAbsoluteFile());
				clip = AudioSystem.getClip();
				clip.open(audio);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				System.err.println("Error in loading the audio files!!!");
			}
			this.bgm.put(i, clip);
		}
		this.isPaused = false;
		this.isStarted = false;
		Point2D.Double centerPoint=new Point2D.Double(0, 0);
		this.hero = new Hero(this, centerPoint);
		this.level = 0;
		this.start();
	}
	
	/**
	 * load all monsters according to the position of the rocks
	 */
	public void loadMonsters() {
		Random random = new Random();
		this.monsters = new ArrayList<Monster>();
		this.monstersRemove = new ArrayList<Monster>();
		for (Rock r : this.getRocks()) {
			int rand = random.nextInt(2);
			Point2D.Double centerRock = r.centerPoint;
			double x = centerRock.getX() + 90;
			double y = centerRock.getY() - 50;
			Point2D.Double centerMonster = new Point2D.Double(x, y);
			Monster monster = null;
			if (rand == 0)
				monster = new ShootMonster(this, centerMonster);
			else
				monster = new MoveMonster(this, centerMonster);
			this.monsters.add(monster);
		}
		
		this.characters = new ArrayList<Character>();
		this.characters.add(this.hero);
		this.characters.addAll(this.monsters);
	}
	
	/**
	 * reset all parameters of the level
	 */
	private void reset() {
		this.hero.reset();
		this.level = 0;
	}
	
	/**
	 * start the game
	 */
	public void startGame() {
		this.isStarted = true;
		this.reset();
		this.stop();
		this.reload();
	}
	
	/**
	 * end the game
	 */
	public void endGame() {
		this.isStarted = false;
		this.isPaused = false;
		this.stop();
		this.start();
	}
	
	public boolean isStarted() {
		return this.isStarted;
	}
	
	public void isPaused() {
		this.isPaused = !this.isPaused;
	}
	
	public boolean getPaused() {
		return this.isPaused;
	}
	
	public int getLevel() {
		return this.level + 1;
	}
	
	public ArrayList<Monster> getMonsters() {
		return this.monsters;
	}
	
	public void addMonsterToremove(Monster m) {
		this.monstersRemove.add(m);
	}

	public void addRock(int index, Rock r) {
		ArrayList<Rock> currentRocks = this.rocks.get(index);
		currentRocks.add(r);
	}
	
	public ArrayList<Rock> getRocks(){
		return this.rocks.get(this.level);
	}
	
	public Image getBackground() {
		return this.backgrounds.get(this.level);
	}
	
	public void updateBulletAndBubble() {
		for (Weapon b: this.bulletAndBubbles) {
			b.updatePosition();
		}
		for (Weapon b2 : this.bulletAndBubblesRemove) {
			this.bulletAndBubbles.remove(b2);
		}
	}
	
	/**
	 * remove the dead monsters
	 */
	public void updateMonsterAndChracter() {
		for (Character c2: this.characters) {
			c2.fire();
			c2.updatePosition();
		}
		for (Monster m: this.monstersRemove) {
			this.monsters.remove(m);
		}
		for (Character c : this.charactersRemove) {
			this.characters.remove(c);
		}
	}
	
	/**
	 * remove the fruit eaten by the hero
	 */
	public void updateFruit() {
		for (Fruit f: this.fruits) {
			f.updatePosition();
		}
		for (Fruit b2 : this.fruitsRemove) {
			this.fruits.remove(b2);
		}
	}
	
	/**
	 * check if the hero is alive
	 * and update the coordinate parameters
	 */
	public void checkLife() {
		if (!this.hero.isAlive()) {
			this.stop();
			this.level = 0;
			this.reload();
			this.hero.reset();
		}
	}
	
	/**
	 * reload the level
	 */
	public void reload() {
		this.fruits.removeAll(this.fruits);
		this.bulletAndBubbles.removeAll(this.bulletAndBubbles);
		this.start();
		this.loadMonsters();
		this.hero.returnToStart();
	}
	
	public void nextLevel() throws IllegalAccessException {
		if (this.level >= Level.MAX_LEVEL - 1 || !this.isStarted || this.isPaused)
			throw new IllegalAccessException();
		this.stop();
		this.level++;
		this.reload();
	}
	
	public void previousLevel() throws IllegalAccessException {
		if (this.level <= 0 || !this.isStarted || this.isPaused)
			throw new IllegalAccessException();
		this.stop();
		this.level--;
		this.reload();
	}
	
	public void restart() {
		for (Character c : this.characters) {
			c.returnToStart();
		}
	}
	
	/**
	 * starts playing the audio
	 */
	public void start() {
		if (this.isStarted)
			this.clip = this.bgm.get(this.level);
		else
			this.clip = this.opening;
		this.clip.start();
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * stop the audio
	 */
	public void stop() {
		this.clip.stop();
	}
}
