/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.killaura;

import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.AttackEvent;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.InteractEvent;

@CheckInfo(
	name = "Killaura (J)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false,
	credits = "§c§lCREDITS: §aMexify §7made this check."
)
public final class KillauraJ extends PacketCheck {
	private boolean sent;

	public KillauraJ(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan8()) {
			if (!(packet instanceof AttackEvent) && !(packet instanceof InteractEvent)) {
				if (packet instanceof DigEvent) {
					DiggingAction type = ((DigEvent)packet).getDigType();
					if (type == DiggingAction.START_DIGGING || type == DiggingAction.CANCELLED_DIGGING || type == DiggingAction.RELEASE_USE_ITEM) {
						this.sent = true;
					}
				} else if (packet instanceof FlyingEvent) {
					this.sent = false;
				}
			} else if (this.sent && !this.data.isPossiblyTeleporting()) {
				this.fail("* Illegal block order", this.getBanVL(), 90L);
			}
		}
	}
}
