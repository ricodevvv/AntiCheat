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
	name = "BadPackets (P)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsP extends PacketCheck {
	private float lpitch;
	private float lyaw;

	public BadPacketsP(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent && ((FlyingEvent)packet).hasLooked()) {
			if (((FlyingEvent)packet).getPitch() == this.lpitch && ((FlyingEvent)packet).getYaw() == this.lyaw && !this.data.isPossiblyTeleporting()) {
				this.fail("* Improper pitch\n §f* P: §b" + this.format(0, Float.valueOf(((FlyingEvent)packet).getPitch())), this.getBanVL(), 110L);
			}

			this.lpitch = ((FlyingEvent)packet).getPitch();
			this.lyaw = ((FlyingEvent)packet).getYaw();
		}
	}
}
