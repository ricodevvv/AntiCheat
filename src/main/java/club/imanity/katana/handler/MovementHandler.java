/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler;

import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.world.nms.FrictionLookup;

public final class MovementHandler {
	private final KatanaPlayer data;

	public void handleMotions(boolean moved, boolean look) {
		if (look) {
			this.data.deltas.deltaPitch = Math.abs(this.data.getLocation().pitch - this.data.getLastLocation().pitch);
			this.data.deltas.deltaYaw = Math.abs(this.data.getLocation().yaw - this.data.getLastLocation().yaw);
		} else {
			this.data.deltas.deltaPitch = 0.0F;
			this.data.deltas.deltaYaw = 0.0F;
		}

		if (this.data.getPositionPackets() > 1) {
			this.data.deltas.lastLastMotionY = this.data.deltas.lastMotionY;
			this.data.deltas.lastMotionY = this.data.deltas.motionY;
			this.data.deltas.lastDX = this.data.deltas.deltaX;
			this.data.deltas.lastDZ = this.data.deltas.deltaZ;
			this.data.deltas.deltaX = this.data.getLocation().x - this.data.getLastLocation().x;
			this.data.deltas.deltaZ = this.data.getLocation().z - this.data.getLastLocation().z;
			this.data.deltas.motionY = this.data.getLocation().y - this.data.getLastLocation().y;
			this.data.deltas.lastLastDXZ = this.data.deltas.lastDXZ;
			this.data.deltas.lastDXZ = this.data.deltas.deltaXZ;
			this.data.deltas.deltaXZ = MathUtil.hypot(this.data.deltas.deltaX, this.data.deltas.deltaZ);
			this.data.deltas.lastAccelXZ = this.data.deltas.accelXZ;
			this.data.deltas.accelXZ = this.data.deltas.deltaXZ - this.data.deltas.lastDXZ;
		}
	}

	public void handleOther(boolean ground) {
		if (!this.data.isOnGroundServer() && this.data.deltas.motionY < 0.0 && !this.data.isOnLiquid() && !this.data.isInWeb()) {
			this.data.fallDistance += (float)(-this.data.deltas.motionY);
		} else {
			if (this.data.isOnGroundServer() && !this.data.isOnSlime() && !this.data.isWasOnSlime()) {
				this.data.lFallDistance = this.data.fallDistance;
				this.data.fallDistance = 0.0F;
			}

			if (this.data.isOnLiquid() || this.data.isInWeb()) {
				this.data.lFallDistance = this.data.fallDistance;
				this.data.fallDistance = 0.0F;
			}
		}

		this.data.setLastTickFriction(this.data.getCurrentFriction());
		this.data.setCurrentFriction(FrictionLookup.lookup(this.data));
		this.data.setLClientAirTicks(this.data.getClientAirTicks());
		this.data.setAirTicks(!this.data.isOnGroundServer() ? this.data.getAirTicks() + 1 : 0);
		this.data.setClientAirTicks(!ground ? this.data.getClientAirTicks() + 1 : 0);
		this.data.setJumpFactor(this.data.isOnHoney() ? 0.5F : 1.0F);
	}

	public MovementHandler(KatanaPlayer data) {
		this.data = data;
	}
}
