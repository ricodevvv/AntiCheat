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
	name = "AimAssist (I)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = false
)
public class AimAssistI extends RotationCheck {
	private int zeroDeltaTicks;

	public AimAssistI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		CustomLocation from = update.getFrom();
		float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
		float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
		if (this.data.getLastAttackTick() < 3 && !this.data.isPossiblyTeleporting()) {
			if (deltaPitch == 0.0F) {
				++this.zeroDeltaTicks;
			} else {
				this.zeroDeltaTicks = 0;
			}

			if (this.zeroDeltaTicks <= 40 || !(deltaYaw > 3.0F) || !(Math.abs(to.getPitch()) < 45.0F) || !(this.data.deltas.deltaXZ > 0.08)) {
				this.violations *= 0.75;
			} else if (++this.violations > 5.0) {
				this.fail("* Weird rotation\n §f* p: §b" + to.getPitch() + "\n §f* lp: §b" + from.getPitch(), this.getBanVL(), 300L);
			}
		}
	}
}
