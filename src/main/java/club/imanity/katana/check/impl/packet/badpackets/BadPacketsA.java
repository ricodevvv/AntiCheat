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

@CheckInfo(
	name = "BadPackets (A)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsA extends PacketCheck {
	public BadPacketsA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent
			&& (((FlyingEvent)packet).hasMoved() || ((FlyingEvent)packet).hasLooked())
			&& Math.abs(((FlyingEvent)packet).getPitch()) > 90.0F
			&& !this.data.isPossiblyTeleporting()) {
			this.fail("* Improper pitch\n §f* P: §b" + this.format(0, Float.valueOf(((FlyingEvent)packet).getPitch())), this.getBanVL(), 110L);
		}
	}
}
