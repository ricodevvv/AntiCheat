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
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

@CheckInfo(
	name = "AutoClicker (B)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = true
)
public final class AutoClickerB extends PacketCheck {
	int flying;
	Deque<Integer> samples = new ArrayDeque<>();

	public AutoClickerB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent && !this.data.isHasDig()) {
			boolean valid = !this.data.isPlacing() && !this.data.isUsingItem();
			if (valid) {
				if (this.data.isNewerThan8()) {
					if (this.flying < 10 && this.data.elapsedMS(((SwingEvent)packet).getTimeStamp(), this.data.getLastFlying()) <= 70L) {
						this.samples.add(this.flying);
					}
				} else if (this.flying < 10) {
					this.samples.add(this.flying);
				}

				if (this.samples.size() == 500) {
					double kur = new Kurtosis().evaluate(MathUtil.dequeTranslator(this.samples));
					double ske = new Skewness().evaluate(MathUtil.dequeTranslator(this.samples));
					double std = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.samples));
					if (ske < 0.2 && kur < 0.0 && std < 0.7) {
						if (++this.violations > 2.0) {
							this.fail(
								"* Weird click pattern\n§f* KU §b"
									+ this.format(2, Double.valueOf(kur))
									+ "\n§f* SK §b"
									+ this.format(2, Double.valueOf(ske))
									+ "\n§f* STD §b"
									+ this.format(2, Double.valueOf(std))
									+ "\n§f* SK §b"
									+ this.format(2, Double.valueOf(ske)),
								this.getBanVL(),
								400L
							);
						}
					} else {
						this.violations = Math.max(this.violations - 0.5, 0.0);
					}

					this.samples.clear();
				}
			}

			this.flying = 0;
		} else if (packet instanceof FlyingEvent && !((FlyingEvent)packet).isTeleport()) {
			++this.flying;
		}
	}
}
