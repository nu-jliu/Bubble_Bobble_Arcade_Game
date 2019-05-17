import javax.swing.JFrame;

/**
 * the frame of the game
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class LevelFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LevelFrame() {
		JFrame frame = new JFrame("Bubble Bobble!");
		frame.setSize(Level.WIDTH, Level.HEIGHT);
		Level level = new Level();
		
		LevelComponent c = new LevelComponent(level);
		// Create a keyListener
		frame.add(c);
		frame.addKeyListener(new KeyHandler(level, frame));
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}