/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class AbilityEvent extends Event {
	private final long timeStamp = System.nanoTime() / 1000000L;

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
