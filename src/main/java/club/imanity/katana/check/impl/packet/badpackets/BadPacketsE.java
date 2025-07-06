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
import club.imanity.katana.event.HeldItemSlotEvent;

@CheckInfo(
	name = "BadPackets (E)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsE extends PacketCheck {
	private int lastSlot = -420;

	public BadPacketsE(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof HeldItemSlotEvent) {
			int slot = ((HeldItemSlotEvent)packet).getSlot();
			if (slot == this.lastSlot && slot != this.data.lastServerSlot) {
				this.fail("* Sent 2 same held slot packets", this.getBanVL(), 210L);
			}

			this.lastSlot = ((HeldItemSlotEvent)packet).getSlot();
		}
	}
}
