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
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

@CheckInfo(
	name = "AutoClicker (D)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = true
)
public final class AutoClickerD extends PacketCheck {
	int flying;
	double lastSTD;
	Deque<Integer> samples = new ArrayDeque<>();

	public AutoClickerD(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			if (this.flying < 10 && !this.data.isHasDig() && !this.data.isPlacing() && !this.data.isUsingItem()) {
				this.samples.add(this.flying);
			}

			if (this.samples.size() == 1000) {
				int outliers = MathUtil.getOutliers(this.samples);
				double std = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.samples));
				double kur = new Kurtosis().evaluate(MathUtil.dequeTranslator(this.samples));
				double sdd = Math.abs(std - this.lastSTD);
				double cps = 20.0 / MathUtil.average(this.samples);
				if (std < 0.8 && kur < 0.5 && sdd < 0.04 && outliers <= 6) {
					if (++this.violations > 1.0) {
						this.fail("* Repeating pattern\n §f* O: §b" + outliers + "\n §f* CPS: §b" + cps, this.getBanVL(), 450L);
					}
				} else {
					this.decrease(0.5);
				}

				this.samples.clear();
				this.lastSTD = std;
			}

			this.flying = 0;
		} else if (packet instanceof FlyingEvent && !((FlyingEvent)packet).isTeleport()) {
			++this.flying;
		}
	}
}
