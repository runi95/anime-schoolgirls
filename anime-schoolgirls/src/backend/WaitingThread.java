package backend;

public class WaitingThread extends Thread {
	private String id;
	
	public WaitingThread(String id) {
		this.id = id;
	}

	public synchronized String getSeriesId() { return id; }
	
	public synchronized void setSeriesId(String id) {
		this.id = id;
	}
}
