/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class SteerEvent extends Event {
	private final boolean unmount;

	public SteerEvent(boolean unmount) {
		this.unmount = unmount;
	}

	public boolean isUnmount() {
		return this.unmount;
	}
}
