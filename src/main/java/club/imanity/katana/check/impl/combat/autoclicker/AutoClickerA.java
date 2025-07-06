/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.autoclicker;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.SwingEvent;

@CheckInfo(
	name = "AutoClicker (A)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = false
)
public final class AutoClickerA extends PacketCheck {
	private int delay;
	private int clicks;

	public AutoClickerA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			if (this.canClick()) {
				if (this.data.isNewerThan8()) {
					if (this.data.elapsedMS(((SwingEvent)packet).getTimeStamp(), this.data.getLastFlying()) <= 60L) {
						++this.clicks;
					}
				} else {
					++this.clicks;
				}
			}
		} else if (packet instanceof FlyingEvent && ++this.delay >= 20) {
			if (this.clicks >= 1) {
				this.data.setLastCps(this.data.getCps());
				this.data.setCps((double)this.clicks);
				this.data.setHighestCps(Math.max((double)this.clicks, this.data.getHighestCps()));
			}

			if ((double)this.clicks > club.imanity.katana.katana.getInstance().getConfigManager().getMaxCps() && !this.data.isHasDig()) {
				this.fail("* Too high cps\n §f* CPS: §b" + this.clicks, 300L);
			}

			this.delay = 0;
			this.clicks = 0;
		}
	}
}
