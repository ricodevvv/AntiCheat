/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.fly;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Fly (B)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.FLY,
	experimental = false
)
public final class FlyB extends PositionCheck {
	private float slimeJump;

	public FlyB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		double limit = 0.0;
		if (this.data.isOnSlime() && this.data.deltas.lastMotionY < -0.4) {
			this.slimeJump = this.data.fallDistance;
		} else if (this.data.getDeltas().motionY <= 1.0E-7 && this.data.getAirTicks() > 4 || this.data.getAirTicks() > 80) {
			this.slimeJump = 0.0F;
		}

		limit += (double)((float)this.data.getLevitationLevel() * 0.5F);
		limit += (double)Math.abs(this.slimeJump);
		if (this.data.isInUnloadedChunk()) {
			if (this.data.getLocation().getY() > 0.0) {
				limit = 0.1;
			} else {
				limit = 0.0;
			}
		}

		if (this.data.elapsed(this.data.getLastInLiquid()) < 15 && club.imanity.katana.katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_8_8)) {
			limit = 5.0;
		}

		double maxVL = 7.5;
		double accel = Math.abs(this.data.deltas.lastMotionY - this.data.deltas.motionY);
		int jumpTicks = (int)(this.data.getJumpBoost() > 0 ? (float)this.data.getJumpBoost() * 10.6F : 0.0F);
		boolean valid = !this.data.isTakingVertical()
			&& this.data.getLocation().y > -100.0
			&& this.data.elapsed(this.data.getLastOnClimbable()) > 35
			&& !this.data.isNearClimbable()
			&& this.data.elapsed(this.data.getLastVelocityYReset()) > 2
			&& !this.data.isOnWeb()
			&& !this.data.isSpectating()
			&& this.data.elapsed(this.data.getLastInLiquid()) > 2
			&& !this.data.isInUnloadedChunk()
			&& this.data.elapsed(this.data.getPlaceTicks()) > Math.min(15, MathUtil.getPingInTicks(this.data.getTransactionPing() + 50L) + 3)
			&& this.data.elapsed(this.data.getLastRiptide()) > 30
			&& this.data.elapsed(this.data.getLastGlide()) > 30
			&& !this.data.isPossiblyTeleporting()
			&& (this.data.isHasReceivedTransaction() || this.data.getTotalTicks() > 100)
			&& this.data.getGameMode() != GameMode.CREATIVE
			&& !this.data.isOnGhostBlock()
			&& !this.data.isInBed()
			&& !this.data.isLastInBed()
			&& !this.data.isRiding()
			&& (this.data.getAirTicks() > 30 + jumpTicks || this.data.getClientAirTicks() > 30 + jumpTicks)
			&& this.data.elapsed(this.data.getLastFlyTick()) > 80;
		double addition = (
					MathUtil.isNearlySame(this.data.deltas.motionY, 0.33, 0.01)
						|| MathUtil.isNearlySame(this.data.deltas.motionY, 0.24, 0.01)
						|| MathUtil.isNearlySame(this.data.deltas.motionY, 0.16, 0.01)
						|| MathUtil.isNearlySame(this.data.deltas.motionY, 0.08, 0.01)
						|| MathUtil.isNearlySame(this.data.deltas.motionY, 0.0, 0.01)
				)
				&& this.data.elapsed(this.data.getPlaceTicks()) <= Math.min(20, MathUtil.getPingInTicks(this.data.getTransactionPing() + 50L) + 7)
			? 0.05
			: 1.0;
		addition = addition == 0.05 && MathUtil.isNearlySame(this.data.deltas.motionY, this.data.deltas.lastMotionY, 0.021) ? 0.5 : addition;
		if (!(this.data.deltas.motionY >= limit) || !valid || this.data.elapsed(this.data.getLastFlyTick()) <= 30) {
			this.violations = Math.max(this.violations - 0.375, 0.0);
		} else if ((this.violations += addition) > maxVL) {
			this.fail(
				"* Accelerating upwards before being on ground \n §f* D: §b" + this.data.deltas.motionY + "\n §f* ST/CT: §b" + this.data.getAirTicks() + " | " + this.data.getClientAirTicks(),
				this.getBanVL(),
				60L
			);
		}
	}
}
