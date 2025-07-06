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
	name = "BadPackets (D)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsD extends PacketCheck {
	public BadPacketsD(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			if (this.data.getTotalTicks() < 300) {
				return;
			}

			if (this.data.getPing() == 0L && this.data.getTransactionPing() > 1L || this.data.getPing() > 1L && this.data.getTransactionPing() == 0L) {
				if (this.violations > 5.0) {
					this.fail("§b* §fNull ping\n§b* §fKPing=§b" + this.data.getPing() + "\n§b* §fTPing=§b" + this.data.getTransactionPing(), this.getBanVL(), 110L);
				}
			} else {
				this.violations = 0.0;
			}
		}
	}
}
