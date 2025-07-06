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
	name = "AimAssist (C)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = true,
	credits = "§c§lCREDITS: §aWizzard §7made this check."
)
public class AimAssistC extends RotationCheck {
	double pitch;
	double yaw;
	double thresholdP;
	double thresholdY;

	public AimAssistC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float pitch = Math.abs(to.getPitch() - from.getPitch());
		float yaw = Math.abs(to.getYaw() - from.getYaw());
		if (!this.data.isPossiblyTeleporting() && !this.data.isRiding() && !this.data.recentlyTeleported(3) && this.data.getSensitivity() < 150) {
			double gcdP = MathUtil.getGcd((double)pitch, this.pitch);
			double gcdY = MathUtil.getGcd((double)yaw, this.yaw);
			if (gcdP > 0.7 && ((double)pitch % this.pitch == 0.0 || Double.isNaN((double)pitch % this.pitch)) && pitch <= 10.0F) {
				this.thresholdP = Math.min(10.0, this.thresholdP + 0.5);
				if (this.thresholdP > 8.0) {
					this.fail("* Vertical aimassist\n §f* GCD: §b" + gcdP + "\n §f* COUNT: §b" + this.thresholdP, this.getBanVL(), 150L);
				}
			} else {
				this.thresholdP = Math.max(0.0, this.thresholdP - 1.25);
			}

			if (gcdY > 0.7 && ((double)yaw % this.yaw == 0.0 || Double.isNaN((double)yaw % this.yaw)) && yaw <= 10.0F) {
				this.thresholdY = Math.min(10.0, this.thresholdY + 0.5);
				if (this.thresholdY > 4.0) {
					this.fail("* Horizontal aimassist\n §f* GCD: §b" + gcdY + "\n §f* COUNT: §b" + this.thresholdY, this.getBanVL(), 150L);
				}
			} else {
				this.thresholdY = Math.max(0.0, this.thresholdY - 1.5);
			}
		}

		this.pitch = (double)pitch;
		this.yaw = (double)yaw;
	}
}
