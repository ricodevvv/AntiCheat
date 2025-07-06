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
import club.imanity.katana.event.AttackEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "AutoClicker (I)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = false
)
public final class AutoClickerI extends PacketCheck {
	private final Deque<Integer> delays = new ArrayDeque<>();
	private int delay;

	public AutoClickerI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof AttackEvent) {
			boolean valid = !this.data.isPlacing() && !this.data.isHasDig() && !this.data.isUsingItem() && this.data.elapsed(this.data.getDigTicks()) > 5;
			if (valid) {
				if (this.delay <= 5 && this.delay > 0) {
					this.delays.add(this.delay);
				}

				if (this.delays.size() == 40) {
					double average = MathUtil.average(this.delays);
					double std = MathUtil.stdDev(average, this.delays);
					if (!(average <= 2.0) || !(std < 0.15) || !(this.data.getCps() > 8.0)) {
						this.decrease(this.violations);
					} else if (++this.violations > 10.0) {
						this.fail("* No randomization\n§f* STD §b" + std + "\n§f* AVG §b" + average + "\n§f* CPS §b" + this.data.getCps(), this.getBanVL(), 200L);
						this.decrease(this.violations);
					}

					this.delays.removeFirst();
				}

				this.delay = 0;
			}
		} else if (packet instanceof FlyingEvent) {
			++this.delay;
		}
	}
}
