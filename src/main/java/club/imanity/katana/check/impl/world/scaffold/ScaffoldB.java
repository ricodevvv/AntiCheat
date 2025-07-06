/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.SwingEvent;

@CheckInfo(
	name = "Scaffold (B)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = false
)
public final class ScaffoldB extends PacketCheck {
	public Long lastSwing;
	public Long lastFlying;

	public ScaffoldB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			this.lastFlying = ((FlyingEvent)packet).getCurrentTimeMillis();
			if (this.lastSwing != null) {
				double delay = (double)(this.lastFlying - this.lastSwing);
				if (!(delay < 60.0)
					|| !(delay > 40.0)
					|| club.imanity.katana.katana.getInstance().isViaRewind()
					|| this.data.hasFast()
					|| this.data.isPossiblyTeleporting()
					|| this.data.isLagging(this.data.getTotalTicks())) {
					this.violations = Math.max(this.violations - 0.35, 0.0);
				} else if (++this.violations > 3.0) {
					this.fail("* Post swing\n §f* D §b" + delay, this.getBanVL(), 60000L);
				}

				this.lastSwing = null;
			}
		} else if (packet instanceof SwingEvent) {
			if (this.lastFlying != null && ((SwingEvent)packet).getTimeStampMS() - this.lastFlying < 2L) {
				this.lastSwing = this.lastFlying;
			} else {
				this.violations = Math.max(this.violations - 0.35, 0.0);
			}
		}
	}
}
