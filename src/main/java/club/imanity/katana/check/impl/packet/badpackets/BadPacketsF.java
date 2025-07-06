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
import club.imanity.katana.event.SteerEvent;

@CheckInfo(
	name = "BadPackets (F)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false,
	credits = "§c§lCREDITS: §aOilSlug §7for the base idea."
)
public final class BadPacketsF extends PacketCheck {
	private double ticks;
	private boolean sent;

	public BadPacketsF(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SteerEvent && this.data.getTotalTicks() > 200) {
			this.sent = !((SteerEvent)packet).isUnmount();
		} else if (packet instanceof FlyingEvent) {
			if (this.sent) {
				if (++this.ticks > 3.0) {
					if (this.data.getBukkitPlayer().getVehicle() == null && !this.data.isRiding() && !this.data.isExitingVehicle()) {
						if (++this.violations > 5.0) {
							this.fail("* Sent vehicle packet without being inside a vehicle", this.getBanVL(), 110L);
						}
					} else {
						this.decrease(0.75);
					}
				}

				this.sent = false;
			} else {
				this.ticks = 0.0;
			}
		}
	}
}
