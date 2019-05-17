/**
 * class that checks the time
 * @author Zhenrui Zhang; Jingkun Liu
 *
 */
public class StopWatch {

	private long startTime;
	private long endTime;
	
	public StopWatch() {
		this.reset();
	}
	
	public void start() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		this.endTime = System.currentTimeMillis();
	}
	
	public long getDuration() {
		return this.endTime - this.startTime;
	}
	
	public void reset() {
		this.startTime = 0;
		this.endTime = 0;
	}
	
}
