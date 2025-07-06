/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.fly;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Fly (D)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.FLY,
	experimental = true
)
public final class FlyD extends PositionCheck {
	public FlyD(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		if (this.data.getVelocityYTicks() == 0) {
			double excepted = this.data.getVelocityY();
			if (excepted < this.data.deltas.motionY - 1.0 && !this.data.isGroundNearBox()) {
				if (++this.violations > 3.0) {
					this.fail(
						"* Not taking fall damage velocity\n §f* EXP: §b"
							+ excepted
							+ "\n §f* RES: §b"
							+ this.data.deltas.motionY
							+ "\n §f* TICK: §b"
							+ this.data.getAirTicks()
							+ " | "
							+ this.data.getClientAirTicks(),
						this.getBanVL(),
						300L
					);
				}
			} else {
				this.violations = Math.max(this.violations - 0.25, 0.0);
			}
		}
	}
}
