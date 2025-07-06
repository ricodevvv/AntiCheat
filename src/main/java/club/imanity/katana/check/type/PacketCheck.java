/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.type;

import club.imanity.katana.check.api.Check;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;

public abstract class PacketCheck extends Check<Event> {
	public PacketCheck(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	public boolean checkClick() {
		return !this.data.isPlacing() && !this.data.isHasDig() && !this.data.isUsingItem();
	}

	public abstract void handle(Event event);
}
