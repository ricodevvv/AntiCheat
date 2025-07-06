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
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "Killaura (H)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraH extends PacketCheck {
	private boolean sentDig;
	private boolean sentPlace;

	public KillauraH(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan8()) {
			if (packet instanceof FlyingEvent) {
				this.sentDig = false;
				this.sentPlace = false;
			} else if (packet instanceof AttackEvent) {
				if (!this.sentPlace & this.sentDig) {
					if (++this.violations > 1.0) {
						this.fail("* Illegal block order", this.getBanVL(), 60L);
					}
				} else {
					this.violations = Math.max(this.violations - 0.1, 0.0);
				}
			} else if (packet instanceof DigEvent) {
				DiggingAction type = ((DigEvent)packet).getDigType();
				if (type != DiggingAction.DROP_ITEM_STACK && type != DiggingAction.DROP_ITEM) {
					this.sentDig = true;
				}
			} else if (packet instanceof BlockPlaceEvent) {
				this.sentPlace = true;
			}
		}
	}
}
