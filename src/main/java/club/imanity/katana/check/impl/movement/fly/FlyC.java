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
	name = "Fly (C)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.FLY,
	experimental = false
)
public final class FlyC extends PositionCheck {
	private double lastAccel;
	private double lastMotionY;

	public FlyC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		double accel = Math.abs(this.lastMotionY - this.data.deltas.motionY);
		boolean valid = !this.data.recentlyTeleported(1)
			&& this.data.getAirTicks() > 10
			&& !this.data.isTakingVertical()
			&& !this.data.isOnClimbable()
			&& !this.data.isOnLiquid()
			&& !this.data.isInUnloadedChunk()
			&& !this.data.isGliding()
			&& !this.data.isCollidedHorizontally()
			&& !this.data.isRiptiding()
			&& !this.data.isSpectating()
			&& !this.data.isOnWeb()
			&& !this.data.isInWeb()
			&& !this.data.isOnSlime()
			&& this.data.elapsed(this.data.getLastInBerry()) > 3
			&& !this.data.isOnGhostBlock()
			&& (this.data.isHasReceivedTransaction() || this.data.getTotalTicks() > 100)
			&& !(Math.abs(this.data.deltas.motionY + 3.9) <= 0.021)
			&& !this.data.isAllowFlying()
			&& this.data.getLevitationLevel() == 0
			&& this.data.elapsed(this.data.getLastFlyTick()) > 80
			&& !this.data.isUnderBlock();
		if (Math.abs(this.data.deltas.motionY + 0.098) <= 1.0E-5) {
			this.lastAccel = accel;
		} else if (this.data.getLocation().y <= -5.0 && this.data.deltas.motionY == 0.0) {
			this.lastAccel = accel;
		} else {
			double acceleration = Math.abs(accel - this.lastAccel);
			double maxVL = acceleration == 0.0 ? 6.0 : 3.0;
			if ((acceleration <= 1.0E-5 || accel <= 1.0E-1) && valid) {
				if (this.violations++ > maxVL) {
					this.fail(
						"* Achieving impossible air acceleration \n §f* A: §b" + acceleration + "\n §f* ST/CT: §b" + this.data.getAirTicks() + " | " + this.data.getClientAirTicks(),
						this.getBanVL(),
						300L
					);
				}
			} else if (acceleration > 1.0E-5 || accel > 1.0E-1) {
				this.violations *= 0.75;
			}

			this.lastAccel = accel;
			this.lastMotionY = this.data.deltas.motionY;
		}
	}
}
