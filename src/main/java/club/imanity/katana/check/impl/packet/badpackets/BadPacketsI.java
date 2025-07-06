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
import club.imanity.katana.event.TransactionEvent;

@CheckInfo(
	name = "BadPackets (I)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = true
)
public final class BadPacketsI extends PacketCheck {
	private long lastFlag;

	public BadPacketsI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof TransactionEvent) {
			long time = (long)((double)((TransactionEvent)packet).getNow() / 1000000.0);
			long flying = (long)((double)this.data.lastFlying / 1000000.0);
			if (time - flying <= (long)(this.data.isNewerThan8() ? '鱀' : 4000) + this.data.getTransactionPing()
				|| time - this.lastFlag <= 250L
				|| this.data.isSpectating()
				|| this.data.getBukkitPlayer().isDead()) {
				this.violations *= 0.2;
			} else if (++this.violations > 20.0) {
				this.fail("* Blink?\n T: §b" + (time - flying) / 50L, this.getBanVL(), 110L);
				this.lastFlag = time;
			}
		}
	}
}
