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
	name = "AutoClicker (F)",
	category = Category.COMBAT,
	subCategory = SubCategory.AUTOCLICKER,
	experimental = true,
	desc = "Standard consistency"
)
public final class AutoClickerF extends PacketCheck {
	private int flying;
	private double lastSTD;
	private final Deque<Integer> samples = new ArrayDeque<>();
	private boolean lastSet;

	public AutoClickerF(KatanaPlayer data, club.imanity.katana.katana katana) {
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
				double kur = new Kurtosis().evaluate(MathUtil.dequeTranslator(this.samples));
				double std = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.samples));
				double sdd = Math.abs(std - this.lastSTD);
				double cps = 20.0 / MathUtil.average(this.samples);
				if (std < 0.75 && sdd < 0.1 && outliers <= 30 && kur < 0.4) {
					if (++this.violations > 1.0) {
						this.fail(
							"* Standard consistency\n §f* STD/D: §b"
								+ this.format(3, Double.valueOf(std))
								+ "/"
								+ this.format(4, Double.valueOf(sdd))
								+ "\n §f* O: §b"
								+ outliers
								+ "\n §f* CPS: §b"
								+ cps,
							this.getBanVL(),
							450L
						);
					}
				} else {
					this.decrease(0.25);
				}

				this.samples.clear();
				this.lastSTD = std;
			}

			if (!this.lastSet && this.samples.size() == 500) {
				this.lastSTD = new StandardDeviation().evaluate(MathUtil.dequeTranslator(this.samples));
				this.lastSet = true;
			}

			this.flying = 0;
		} else if (packet instanceof FlyingEvent && !((FlyingEvent)packet).isTeleport()) {
			++this.flying;
		}
	}
}
