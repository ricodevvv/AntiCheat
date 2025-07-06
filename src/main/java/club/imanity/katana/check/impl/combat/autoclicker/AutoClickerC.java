/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.autoclicker;

import java.util.ArrayDeque;
import java.util.Deque;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.SwingEvent;
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "AutoClicker (C)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = false
)
public final class AutoClickerC extends PacketCheck {
	private final Deque<Integer> delays = new ArrayDeque<>();
	private int delay;

	public AutoClickerC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			if (this.checkClick()) {
				if (this.data.isNewerThan8()) {
					if (this.delay < 10 && this.data.elapsedMS(((SwingEvent)packet).getTimeStamp(), this.data.getLastFlying()) <= 70L) {
						this.delays.add(this.delay);
					}
				} else if (this.delay < 10) {
					this.delays.add(this.delay);
				}

				if (this.delays.size() == 800) {
					int outliers = MathUtil.getOutliers(this.delays);
					double cps = 20.0 / MathUtil.average(this.delays);
					if (outliers <= 5) {
						if (++this.violations > 1.0) {
							this.fail("* Low outliers\n §f* O: §b" + outliers + "\n §f* CPS: §b" + cps, this.getBanVL(), 450L);
						}
					} else {
						this.violations = Math.max(this.violations - 0.5, 0.0);
					}

					this.delays.clear();
				}
			}

			this.delay = 0;
		} else if (packet instanceof FlyingEvent) {
			if (this.data.isUsingItem()) {
				this.delay = 0;
				return;
			}

			++this.delay;
		}
	}
}
