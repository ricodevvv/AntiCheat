/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.HeldItemSlotEvent;

@CheckInfo(
	name = "BadPackets (G)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsG extends PacketCheck {
	private int slots;

	public BadPacketsG(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof HeldItemSlotEvent) {
			++this.slots;
		} else if (packet instanceof FlyingEvent) {
			int threshold = this.data.isNewerThan8() ? 40 : 10;
			if (this.slots > threshold) {
				this.fail("* Sent too many slot packets\n * S §b" + this.slots, this.getBanVL(), 110L);
			}

			this.slots = 0;
		}
	}
}
