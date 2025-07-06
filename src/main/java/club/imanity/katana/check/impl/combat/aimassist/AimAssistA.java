/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.aimassist;

import java.util.Deque;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.evictinglist.EvictingList;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "AimAssist (A)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = false
)
public final class AimAssistA extends RotationCheck {
	private double lastAveragePitch;
	private double lastAverageYaw;
	private final Deque<Float> samplesP = new EvictingList<>(20);
	private final Deque<Float> samplesY = new EvictingList<>(20);

	public AimAssistA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
		float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
		if (!this.data.isPossiblyTeleporting() && this.data.getLastAttackTick() <= 20) {
			if ((double)deltaYaw > 0.0) {
				this.samplesY.add(deltaYaw);
			}

			if ((double)deltaPitch > 0.0) {
				this.samplesP.add(deltaPitch);
			}

			if (this.samplesP.size() == 20 && this.samplesY.size() == 20) {
				double averagePitch = this.samplesP.stream().mapToDouble(d -> (double)d.floatValue()).average().orElse(0.0);
				double averageYaw = this.samplesY.stream().mapToDouble(d -> (double)d.floatValue()).average().orElse(0.0);
				if ((MathUtil.isNearlySame(averagePitch, this.lastAveragePitch, 1.0E-4) || MathUtil.isNearlySame(averageYaw, this.lastAverageYaw, 1.0E-4)) && !this.data.isRiding()) {
					if (++this.violations > 5.0) {
						this.fail("* Consistent changes\n §f* avgPitch: §b" + averagePitch + "\n §f* avgYaw: §b" + averageYaw, this.getBanVL(), 300L);
					}
				} else {
					this.violations = Math.max(this.violations - 1.25, 0.0);
				}

				this.samplesP.clear();
				this.samplesY.clear();
				this.lastAverageYaw = averageYaw;
				this.lastAveragePitch = averagePitch;
			}
		}
	}
}
