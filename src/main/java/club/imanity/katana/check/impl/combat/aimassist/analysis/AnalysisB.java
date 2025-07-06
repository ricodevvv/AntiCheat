/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.aimassist.analysis;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.EntityData;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;
import org.bukkit.util.Vector;

@CheckInfo(
	name = "Analysis (B)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = true
)
public class AnalysisB extends RotationCheck {
	private double lastAngle;
	private double lastAngleDiff;

	public AnalysisB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		if (this.data.getLastAttackTick() <= 60 && this.data.getLastTarget() != null) {
			if (this.data.getLastAttackTick() <= 40) {
				EntityData edata = this.data.getEntityData().get(this.data.getLastTarget().getEntityId());
				if (edata != null) {
					CustomLocation to = update.getTo();
					CustomLocation from = update.getFrom();
					float deltaPitch = Math.abs(to.getPitch() - from.getPitch());
					float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
					double x = edata.getEntityBoundingBox().getCenterX();
					double z = edata.getEntityBoundingBox().getCenterZ();
					double direction = MathUtil.getDirection(this.data.getLocation(), new Vector(x, 0.0, z));
					double angle = MathUtil.getAngleDistance((double)this.data.getLocation().getYaw(), direction);
					double aDiff = Math.abs(angle - this.lastAngle);
					double angleDiffDiff = Math.abs(aDiff - this.lastAngleDiff);
					if ((double)deltaYaw > 3.5 && aDiff <= 0.075 && this.data.deltas.deltaXZ > 0.1) {
						if (++this.violations > 5.0) {
							this.fail(
								"* Aimlock\n §f* p: §b" + deltaPitch + "\n §f* y: §b" + deltaYaw + "\n §f* ang: §b" + angle + "\n §f* ad: §b" + aDiff + "\n §f* add: §b" + angleDiffDiff,
								this.getBanVL(),
								300L
							);
						}
					} else {
						this.violations = Math.max(this.violations - 0.15, 0.0);
					}

					this.lastAngle = angle;
					this.lastAngleDiff = aDiff;
				}
			}
		} else {
			this.violations = 0.0;
		}
	}
}
