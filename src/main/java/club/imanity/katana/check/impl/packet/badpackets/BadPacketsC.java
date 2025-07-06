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
import club.imanity.katana.event.AbilityEvent;
import club.imanity.katana.event.Event;

@CheckInfo(
	name = "BadPackets (C)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsC extends PacketCheck {
	public BadPacketsC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof AbilityEvent) {
			if (this.data.elapsed(this.data.getLastFlyTick()) <= 30 || this.data.getTotalTicks() <= 60) {
				this.violations = 0.0;
			} else if (++this.violations > 2.0) {
				this.fail("* Sent ability packet without flying", this.getBanVL(), 110L);
			}
		}
	}
}
