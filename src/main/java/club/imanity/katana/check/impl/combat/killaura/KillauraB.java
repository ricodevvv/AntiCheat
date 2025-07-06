/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.killaura;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.AttackEvent;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.InteractEvent;

@CheckInfo(
	name = "Killaura (B)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraB extends PacketCheck {
	private boolean sentInteract;
	private boolean sentAttack;

	public KillauraB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan8()) {
			if (packet instanceof FlyingEvent) {
				this.sentInteract = false;
				this.sentAttack = false;
			} else if (packet instanceof AttackEvent) {
				this.sentAttack = true;
			} else if (packet instanceof InteractEvent) {
				this.sentInteract = true;
			} else if (packet instanceof BlockPlaceEvent) {
				if (this.sentAttack && !this.sentInteract && this.data.getLastTarget() != null) {
					if (++this.violations > 1.0) {
						this.fail("* Illegal block order", this.getBanVL(), 60L);
					}
				} else {
					this.violations = Math.max(this.violations - 0.1, 0.0);
				}
			}
		}
	}
}
