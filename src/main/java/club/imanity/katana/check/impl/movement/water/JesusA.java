/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.water;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Jesus (A)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.JESUS,
	experimental = true
)
public final class JesusA extends PositionCheck {
	public JesusA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		if ((this.data.isOnLiquid() || this.data.isAboveButNotInWater())
			&& !this.data.isPossiblyTeleporting()
			&& this.data.elapsed(this.data.getUnderPlaceTicks()) > this.data.getPingInTicks() + 5
			&& this.data.elapsed(this.data.getLastPistonPush()) > 3
			&& this.data.elapsed(this.data.getLastFlyTick()) > 30) {
			if (this.data.getAirTicks() > 4 && update.isGround()) {
				if (++this.violations > 3.0) {
					this.fail("* Wrong groundstate on liquid\n§f* Inside §b" + this.data.isOnLiquid() + "\n§f* Above §b" + this.data.isAboveButNotInWater(), this.getBanVL(), 300L);
				}
			} else {
				this.violations = Math.max(this.violations - 0.25, 0.0);
			}
		}
	}
}
