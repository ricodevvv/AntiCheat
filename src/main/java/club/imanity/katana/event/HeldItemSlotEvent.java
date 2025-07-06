/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class HeldItemSlotEvent extends Event {
	private final int slot;

	public HeldItemSlotEvent(int slot) {
		this.slot = slot;
	}

	public int getSlot() {
		return this.slot;
	}
}
