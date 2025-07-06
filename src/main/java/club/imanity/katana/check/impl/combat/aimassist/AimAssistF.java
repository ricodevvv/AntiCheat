/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.aimassist;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "AimAssist (F)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = true
)
public class AimAssistF extends RotationCheck {
	private float lastDeltaPitch;
	private float lastDeltaYaw;
	private int streak;

	public AimAssistF(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
		float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
		if (this.data.getLastAttackTick() <= 5 && (double)deltaYaw > 0.001 && deltaYaw <= 5.0F && this.lastDeltaYaw <= 5.0F && Math.abs(to.pitch) <= 80.0F) {
			double gcdYAW = (double)MathUtil.getGcd(deltaYaw, this.lastDeltaYaw);
			if (gcdYAW < 0.009 && !this.data.isCinematic()) {
				double gcdPITCH = (double)MathUtil.getGcd(deltaPitch, this.lastDeltaPitch);
				if (deltaPitch > 0.0F && gcdPITCH < 0.009) {
					this.streak = 0;
					this.violations = 0.0;
				}

				if (++this.streak > 20 && this.lastDeltaPitch == 0.0F && ++this.violations > 15.0) {
					this.fail("* Consistent rotations\n §f* gcdY: §b" + gcdYAW + "\n §f* gcdP: §b" + gcdPITCH, this.getBanVL(), 300L);
					this.violations = 0.0;
				}
			} else {
				this.decrease(0.5);
			}
		}

		this.lastDeltaPitch = deltaPitch;
		this.lastDeltaYaw = deltaYaw;
	}
}
