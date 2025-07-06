/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class SwingEvent extends Event {
	private final long nanoTime;
	private final long timeMillis;

	public SwingEvent(long nanoTime, long timeMillis) {
		this.nanoTime = nanoTime;
		this.timeMillis = timeMillis;
	}

	public long getTimeStamp() {
		return this.nanoTime;
	}

	public long getTimeStampMS() {
		return this.timeMillis;
	}
}
