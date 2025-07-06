/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.HeldItemSlotEvent;

@CheckInfo(
	name = "BadPackets (B)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsB extends PacketCheck {
	private boolean placing;

	public BadPacketsB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof HeldItemSlotEvent) {
			if (!this.placing || !this.data.getClientVersion().isOlderThan(ClientVersion.V_1_9)) {
				this.violations = Math.max(this.violations - 0.3, 0.0);
			} else if (++this.violations > 1.0) {
				this.fail("* Placing while changing slot", this.getBanVL(), 110L);
			}
		} else if (packet instanceof FlyingEvent) {
			this.placing = false;
		} else if (packet instanceof BlockPlaceEvent) {
			this.placing = true;
		}
	}
}
