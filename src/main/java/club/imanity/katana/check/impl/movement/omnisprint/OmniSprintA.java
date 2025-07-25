/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.omnisprint;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.mc.MathHelper;
import club.imanity.katana.util.player.PlayerUtil;
import club.imanity.katana.util.update.MovementUpdate;
import org.bukkit.util.Vector;

@CheckInfo(
	name = "OmniSprint (A)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.SPEED,
	experimental = true
)
public final class OmniSprintA extends PositionCheck {
	public OmniSprintA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate e) {
		if (this.data.deltas.deltaXZ > 0.2 && this.data.getVelocityHorizontal() == 0.0 && !this.data.isSpectating() && !this.data.isOnLiquid()) {
			double move = MathUtil.getVectorSpeed(e.getFrom(), e.getTo()).distanceSquared(MathUtil.getDirection(this.data));
			double baseSpeed = (double)PlayerUtil.getBaseSpeedAttribute(this.data, 2.0F) + 0.03;
			double jumpHeight = (double)PlayerUtil.getJumpHeight(this.data);
			if (this.data.deltas.motionY >= jumpHeight - 0.03125) {
				float yawRadians = this.data.getLocation().yaw * (float) Math.PI / 180.0F;
				baseSpeed += Math.hypot((double)(MathHelper.sin(yawRadians) * 0.2F), (double)(MathHelper.cos(yawRadians) * 0.2F));
			}

			if (!this.data.isOnGroundPacket()) {
				baseSpeed += 0.05;
			}

			if (this.data.getClientAirTicks() <= 3) {
				baseSpeed += 0.04;
			}

			baseSpeed += this.data.elapsed(this.data.getLastOnIce()) <= 3 ? 0.8 : 0.0;
			double p = baseSpeed / move * 100.0;
			double addition = this.data.isSprinting() && this.data.isWasSprinting() ? 1.2 : 0.4;
			float[] keyPair = MathUtil.getStrafeForward(e.from, e.to, this.data);
			Vector moveChange = MathUtil.getMoveChange(e.from, e.to, this.data);
			Vector yawDirection = MathUtil.getDirection(e.to.yaw, 0.0F);
			double angle = MathUtil.angle(yawDirection, moveChange);
			if (!(move > baseSpeed)
				|| !(this.data.deltas.deltaXZ > baseSpeed)
				|| !(keyPair[0] <= 0.0F)
				|| !(angle > 1.1)
				|| this.data.elapsed(this.data.getLastFlyTick()) <= 30
				|| this.data.elapsed(this.data.getLastPistonPush()) <= 3
				|| this.data.recentlyTeleported(5)) {
				this.violations = Math.max(this.violations - 0.05, 0.0);
			} else if ((this.violations += addition) > 7.0) {
				this.fail(
					"* Sprinting in impossible direction\n §f* speed §b"
						+ this.format(3, Double.valueOf(p))
						+ "\n §f* dXZ §b"
						+ this.format(3, Double.valueOf(this.data.deltas.deltaXZ))
						+ "\n §f* moved §b"
						+ this.format(3, Double.valueOf(move))
						+ " | "
						+ this.format(3, Double.valueOf(angle))
						+ "\n §f* sp/lsp §b"
						+ this.data.isSprinting()
						+ " | "
						+ this.data.isWasSprinting()
						+ "\n §f* expected §b"
						+ baseSpeed,
					this.getMaxvl(),
					125L
				);
			}
		}
	}
}
