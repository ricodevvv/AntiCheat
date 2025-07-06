/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.motion;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Motion (I)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.MOTION,
	experimental = true
)
public final class MotionI extends PositionCheck {
	public MotionI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		double motionY = this.data.deltas.motionY;
		double lastMotionY = this.data.deltas.lastMotionY;
		double minimum = this.data.getMoveTicks() <= 1 ? -0.2 : -0.1;
		if (this.data.getTotalTicks() > 40
			&& !this.data.isPossiblyTeleporting()
			&& !this.data.isRiding()
			&& !this.data.isInBed()
			&& !this.data.isLastInBed()
			&& this.data.elapsed(this.data.getLastFlyTick()) > 30
			&& this.data.elapsed(this.data.getLastPistonPush()) > 30) {
			if (motionY < -3.92005) {
				String data = "* Too high vertical motion downwards\n§f* motY: §b"
					+ this.format(3, Double.valueOf(motionY))
					+ "\n§f* vehicle: §b"
					+ this.data.isRiding()
					+ "\n§f* teleport §b"
					+ this.data.getTeleportManager().teleportTicks;
				this.fail(data, this.getBanVL(), 200L);
			} else if (lastMotionY == 0.0 && motionY < minimum && !this.data.isOnSlime() && !this.data.isTakingVertical() && !this.data.isOnLiquid()) {
				String data = "* Invalid vertical motion downwards\n§f* motY: §b"
					+ this.format(3, Double.valueOf(motionY))
					+ "\n§f* vehicle: §b"
					+ this.data.isRiding()
					+ "\n§f* teleport §b"
					+ this.data.getTeleportManager().teleportTicks;
				this.fail(data, this.getBanVL(), 200L);
			}
		}
	}
}
