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
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "Killaura (A)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = false
)
public final class KillauraA extends PacketCheck {
	public Long lastUseEntity;
	public Long lastFlying;

	public KillauraA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			this.lastFlying = ((FlyingEvent)packet).getCurrentTimeMillis();
			if (this.lastUseEntity != null) {
				double delay = (double)(this.lastFlying - this.lastUseEntity);
				if (!(delay < 60.0) || !(delay > 40.0) || this.data.hasFast() || this.data.isPossiblyTeleporting() || this.data.isLagging(this.data.getTotalTicks())) {
					this.violations = Math.max(this.violations - 0.35, 0.0);
				} else if (++this.violations > 3.0) {
					this.fail("* Post killaura\n §f* D §b" + delay, this.getBanVL(), 600L);
				}

				this.lastUseEntity = null;
			}
		} else if (packet instanceof AttackEvent) {
			if (this.lastFlying != null && ((AttackEvent)packet).getTimeMillis() - this.lastFlying < 2L) {
				this.lastUseEntity = this.lastFlying;
			} else {
				this.violations = Math.max(this.violations - 0.35, 0.0);
			}
		}
	}
}
