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
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "Killaura (G)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraG extends PacketCheck {
	private boolean sentInteract;

	public KillauraG(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan8()) {
			if (packet instanceof FlyingEvent) {
				this.sentInteract = false;
			} else if (packet instanceof BlockPlaceEvent && ((BlockPlaceEvent)packet).isUsableItem()) {
				this.sentInteract = true;
			} else if (packet instanceof DigEvent && ((DigEvent)packet).getDigType() == DiggingAction.RELEASE_USE_ITEM && this.sentInteract && !this.data.isPossiblyTeleporting()) {
				this.fail("* Illegal sword blocking order", this.getBanVL(), 600L);
			}
		}
	}
}
