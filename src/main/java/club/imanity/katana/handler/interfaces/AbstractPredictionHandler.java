/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.handler.collision.BlockCollisionHandler;
import club.imanity.katana.handler.collision.enums.CollisionType;
import club.imanity.katana.handler.prediction.core.PredictionHandler;
import club.imanity.katana.handler.prediction.core.PredictionHandler1_9;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.mc.BlockPos;
import club.imanity.katana.util.mc.MathHelper;
import club.imanity.katana.util.mc.axisalignedbb.AxisAlignedBB;
import club.imanity.katana.util.pair.Pair;
import club.imanity.katana.util.player.PlayerUtil;
import club.imanity.katana.world.nms.NMSValueParser;
import org.bukkit.Location;
import org.bukkit.block.Block;

public abstract class AbstractPredictionHandler {
	public double motX;
	public double motZ;
	public double motY;
	public double nextMotX;
	public double nextMotZ;
	public double nextMotY;
	public float moveStrafe;
	public float moveForward;
	public CustomLocation predictedLocation;
	protected final KatanaPlayer data;
	protected final club.imanity.katana.katana katana;
	protected AxisAlignedBB boundingBox;

	public AbstractPredictionHandler(KatanaPlayer data, club.imanity.katana.katana katana) {
		this.data = data;
		this.katana = katana;
	}

	protected final void jump() {
		this.motY = 0.42F;
		this.motY += (double)((float)this.data.getJumpBoost() * 0.1F);
		float f = this.data.getLocation().getYaw() * (float) (Math.PI / 180.0);
		this.motX -= (double)(MathHelper.sin(f) * 0.2F);
		this.motZ += (double)(MathHelper.cos(f) * 0.2F);
	}

	protected final void doBlockCollisions() {
		BlockPos blockpos = new BlockPos(this.getEntityBoundingBox().minX + 0.001, this.getEntityBoundingBox().minY + 0.001, this.getEntityBoundingBox().minZ + 0.001);
		BlockPos blockpos1 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001, this.getEntityBoundingBox().maxY - 0.001, this.getEntityBoundingBox().maxZ - 0.001);
		if (this.katana.getNmsWorldProvider().isAreaLoaded(this.data.getWorld(), blockpos, blockpos1)) {
			for (int i = blockpos.getX(); i <= blockpos1.getX(); ++i) {
				for (int k = blockpos.getZ(); k <= blockpos1.getZ(); ++k) {
					for (int j = blockpos.getY(); j <= blockpos1.getY(); ++j) {
						Location l = new Location(this.data.getWorld(), (double)i, (double)j, (double)k);
						Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(l);
						if (block != null) {
							BlockCollisionHandler.run(block, CollisionType.WALKING, this);
						}
					}
				}
			}
		}
	}

	protected final void computeKeys() {
		float motionAngle = MathUtil.getMoveAngleNoAbs(this.data.getLastLocation(), this.data.getLocation()) / 45.0F;
		int dir = 6;
		String key = "-";
		if (Math.abs(Math.abs(this.data.deltas.deltaX) + Math.abs(this.data.deltas.deltaZ)) > 1.0E-12) {
			dir = (int)new BigDecimal((double)motionAngle).setScale(1, RoundingMode.HALF_UP).doubleValue();
			switch (dir) {
				case -3:
					this.moveForward = -1.0F;
					this.moveStrafe = 1.0F;
					key = "S + A";
					break;
				case -2:
					this.moveStrafe = 1.0F;
					key = "A";
					break;
				case -1:
					this.moveForward = 1.0F;
					this.moveStrafe = 1.0F;
					key = "W + A";
					break;
				case 0:
					this.moveForward = 1.0F;
					key = "W";
					break;
				case 1:
					this.moveForward = 1.0F;
					this.moveStrafe = -1.0F;
					key = "W + D";
					break;
				case 2:
					this.moveStrafe = -1.0F;
					key = "D";
					break;
				case 3:
					this.moveForward = -1.0F;
					this.moveStrafe = -1.0F;
					key = "S + D";
					break;
				case 4:
					this.moveForward = -1.0F;
					key = "S";
				case 5:
				case 6:
				case 7:
				default:
					break;
				case 8:
					this.moveForward = 1.0F;
					key = "W";
			}
		}

		if (this.data.isSneaking()) {
			this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
			this.moveForward = (float)((double)this.moveForward * 0.3);
		}

		this.moveForward *= 0.98F;
		this.moveStrafe *= 0.98F;
		if (this.data.isUsingItem()) {
			this.moveForward *= 0.2F;
			this.moveStrafe *= 0.2F;
		}

		this.data.setKeyCombo(key);
	}

	protected final void init() {
		double x = this.motX;
		double z = this.motZ;
		this.motZ = 0.0;
		this.motX = 0.0;
		HashMap<Double, Pair> dataAssessments = new HashMap<>();

		for (float[] floats : NMSValueParser.KEY_COMBOS) {
			float forward = floats[1];
			float strafe = floats[0];
			if (this.data.isSneaking()) {
				strafe = (float)((double)strafe * 0.3);
				forward = (float)((double)forward * 0.3);
			}

			forward *= 0.98F;
			strafe *= 0.98F;
			if (this.data.isUsingItem()) {
				forward *= 0.2F;
				strafe *= 0.2F;
			}

			if (this.data.isJumped() && this.data.elapsed(this.data.getLastInLiquid()) > 1) {
				this.jump();
			}

			this.moveFlying(strafe, forward, PlayerUtil.getScaledFriction(this.data));
			dataAssessments.put(MathUtil.hypot(this.data.deltas.deltaX - this.motX, this.data.deltas.deltaZ - this.motZ), new Pair<>(strafe, forward));
			this.motX = 0.0;
			this.motZ = 0.0;
		}

		this.motX = x;
		this.motZ = z;
		double closest = dataAssessments.keySet().stream().mapToDouble(d -> d).min().orElse(-1.0);
		Pair motions = closest == -1.0 ? null : dataAssessments.get(closest);
		if (motions != null) {
			this.moveStrafe = (Float)motions.getX();
			this.moveForward = (Float)motions.getY();
		}
	}

	protected final AxisAlignedBB getEntityBoundingBox() {
		return this.boundingBox;
	}

	public abstract void moveFlying(float float1, float float2, float float3);

	public abstract void moveEntityWithHeading();

	public abstract void moveEntity(double double1, double double2, double double3);

	public static AbstractPredictionHandler loadPredictionHandler(KatanaPlayer data, club.imanity.katana.katana katana) {
		AbstractPredictionHandler p = null;
		if (data.getClientVersion() != null) {
			p = (AbstractPredictionHandler)(data.isNewerThan8() ? new PredictionHandler1_9(data, katana) : new PredictionHandler(data, katana));
		}

		if (p == null) {
			p = new PredictionHandler(data, katana);
		}

		p.init();
		return p;
	}

	public KatanaPlayer getData() {
		return this.data;
	}

	public club.imanity.katana.katana getKatana() {
		return this.katana;
	}
}
