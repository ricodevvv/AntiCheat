/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class WindowEvent extends Event {
	private final long timeStamp;

	public WindowEvent(long nano) {
		this.timeStamp = nano;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
