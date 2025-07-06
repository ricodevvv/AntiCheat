/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction.Action;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.ActionEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "BadPackets (R)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsR extends PacketCheck {
	private int sprints;

	public BadPacketsR(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan8()) {
			if (packet instanceof FlyingEvent) {
				if (this.sprints > 1 && !this.data.isPossiblyTeleporting()) {
					this.fail("* Too many actions", 300L);
				}

				this.sprints = 0;
			} else if (packet instanceof ActionEvent) {
				if (((ActionEvent)packet).getAction().equals(Action.START_SPRINTING)) {
					++this.sprints;
				}

				if (((ActionEvent)packet).getAction().equals(Action.STOP_SPRINTING)) {
					++this.sprints;
				}
			}
		}
	}
}
