/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.killaura;

import java.util.Deque;
import java.util.LinkedList;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Killaura (M)",
	category = Category.COMBAT,
	subCategory = SubCategory.KILLAURA,
	experimental = true
)
public final class KillauraM extends RotationCheck {
	private final Deque<Float> pitches = new LinkedList<>();

	public KillauraM(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float pitch = Math.abs(to.pitch - from.pitch);
		float yaw = Math.abs(to.yaw - from.yaw);
		if (((double)yaw > 0.0 || (double)pitch > 0.0) && this.data.getLastAttackTick() <= 1) {
			this.pitches.add(pitch);
		}

		if (this.pitches.size() == 40) {
			double avg = MathUtil.getAverage(this.pitches);
			double std = MathUtil.getStandardDeviation(this.pitches);
			double osc = MathUtil.getOscillation(this.pitches);
			if (osc > 40.0 && std > 12.5 && avg > 25.0) {
				if (++this.violations > (double)(this.data.getSensitivity() > 90 ? 5 : 2)) {
					this.fail(
						"* Randomized aim\n §f* std: §b"
							+ this.format(3, Double.valueOf(std))
							+ "\n §f* avg: §b"
							+ this.format(3, Double.valueOf(avg))
							+ "\n §f* osc: §b"
							+ this.format(3, Double.valueOf(osc)),
						this.getBanVL(),
						300L
					);
				}
			} else {
				this.violations = Math.max(this.violations - (this.data.getSensitivity() > 90 ? 0.2 : 0.05), -0.2);
			}

			this.pitches.clear();
		}
	}
}
