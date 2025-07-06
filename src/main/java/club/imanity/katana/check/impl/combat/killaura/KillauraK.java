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
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;

@CheckInfo(
	name = "Killaura (K)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false,
	credits = "§c§lCREDITS: §aMexify §7made this check."
)
public final class KillauraK extends PacketCheck {
	public KillauraK(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof DigEvent) {
			DiggingAction type = ((DigEvent)packet).getDigType();
			if (this.data.getClientVersion().getProtocolVersion() <= 47 && type == DiggingAction.RELEASE_USE_ITEM && (this.data.isPlacing() || this.data.isUsingItem())) {
				this.fail("* Illegal block order", this.getBanVL(), 120L);
			}
		}
	}
}
