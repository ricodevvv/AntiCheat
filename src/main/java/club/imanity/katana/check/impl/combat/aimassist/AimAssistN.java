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
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "AimAssist (N)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = true
)
public class AimAssistN extends RotationCheck {
	private float pitch;
	private float yaw;

	public AimAssistN(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float pitch = Math.abs(to.pitch - from.pitch);
		float yaw = Math.abs(to.yaw - from.yaw);
		float changeY = Math.abs(this.pitch - pitch);
		float changeX = Math.abs(this.yaw - yaw);
		double differenceYX = (double)Math.abs(changeY - changeX);
		if (this.data.getLastAttackTick() <= 3 && !this.data.isPossiblyTeleporting()) {
			if (!(differenceYX > 2.5) || !((double)yaw < 0.001) || !((double)this.yaw < 0.001)) {
				this.decrease(0.5);
			} else if (++this.violations > 8.0) {
				this.fail(
					"* Weird X/Y changes\n §f* difference: §b" + this.format(4, Double.valueOf(differenceYX)) + "\n §f* change: §b" + this.format(4, Float.valueOf(changeY)),
					this.getBanVL(),
					300L
				);
			}
		}

		this.pitch = pitch;
		this.yaw = yaw;
	}
}
