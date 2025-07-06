/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class TransactionEvent extends Event {
	private final long now;

	public TransactionEvent(long now) {
		this.now = now;
	}

	public long getNow() {
		return this.now;
	}
}
