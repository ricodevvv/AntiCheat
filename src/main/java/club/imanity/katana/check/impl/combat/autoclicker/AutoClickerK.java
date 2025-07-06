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
import org.apache.commons.math3.stat.descriptive.moment.SemiVariance;

@CheckInfo(
	name = "AutoClicker (K)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = true
)
public final class AutoClickerK extends PacketCheck {
	private int flying;
	private Deque<Integer> samples = new ArrayDeque<>();

	public AutoClickerK(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			if (this.data.isNewerThan8()) {
				if (this.flying < 8 && this.canClick() && this.data.elapsedMS(((SwingEvent)packet).getTimeStamp(), this.data.getLastFlying()) <= 70L) {
					this.samples.add(this.flying);
				}
			} else if (this.flying < 8 && this.canClick()) {
				this.samples.add(this.flying);
			}

			if (this.samples.size() == 500) {
				double std = MathUtil.getStandardDeviation(this.samples);
				double cps = 20.0 / MathUtil.getAverage(this.samples);
				double semiVar = new SemiVariance().evaluate(MathUtil.dequeTranslator(this.samples));
				double divided = semiVar / std;
				if (cps > 8.0 && divided < 0.06 && std < 0.75) {
					if (this.increase(1.0) > 1.0) {
						this.fail("* Low variation\n §f* STD: §b" + std + "\n §f* DIVIDED: §b" + divided + "\n §f* SEMIV: §b" + semiVar + "\n §f* CPS: §b" + cps, this.getBanVL(), 450L);
					}
				} else {
					this.decrease(0.25);
				}

				this.samples.clear();
			}

			this.flying = 0;
		} else if (packet instanceof FlyingEvent && !((FlyingEvent)packet).isTeleport()) {
			++this.flying;
		}
	}
}
