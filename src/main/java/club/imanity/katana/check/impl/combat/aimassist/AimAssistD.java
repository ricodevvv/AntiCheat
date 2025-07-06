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
	name = "AimAssist (D)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = false
)
public class AimAssistD extends RotationCheck {
	private float lastDeltaYaw;
	private float lastLastDeltaYaw;

	public AimAssistD(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float deltaYaw = Math.abs(to.yaw - from.yaw);
		if ((this.data.getLastAttackTick() < 4 || this.data.elapsed(this.data.getUnderPlaceTicks()) <= 6) && this.data.getTotalTicks() > 40 && !this.data.isPossiblyTeleporting()) {
			double range = 69.0;
			if (this.data.getLastTarget() != null) {
				range = this.data.getBukkitPlayer().getEyeLocation().clone().toVector().setY(0.0).distance(this.data.getLastTarget().getEyeLocation().clone().toVector().setY(0.0));
			}

			if (deltaYaw < 3.0F && this.lastDeltaYaw > 30.0F && this.lastLastDeltaYaw < 3.0F && range > 1.3) {
				this.fail("* Snappy aim\n §f* now: §b" + deltaYaw + "\n §f* l: §b" + this.lastDeltaYaw + "\n §f* ll: §b" + this.lastLastDeltaYaw, this.getBanVL(), 100L);
			}
		}

		this.lastLastDeltaYaw = deltaYaw;
		this.lastDeltaYaw = deltaYaw;
	}
}
