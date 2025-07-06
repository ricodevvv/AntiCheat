/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.combat.aimassist;

import java.util.Deque;
import java.util.LinkedList;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.RotationCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.location.CustomLocation;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "AimAssist (M)",
	category = Category.COMBAT,
	subCategory = SubCategory.AIM,
	experimental = true
)
public class AimAssistM extends RotationCheck {
	private final Deque<Float> pitchList = new LinkedList<>();

	public AimAssistM(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		CustomLocation to = update.getTo();
		if (this.data.deltas.deltaYaw > 2.5F && Math.abs(to.pitch) <= 80.0F && !this.data.isCinematic() && this.data.getLastAttackTick() <= 3 && this.data.getSensitivity() != -1) {
			this.pitchList.add(this.data.deltas.deltaPitch);
		}

		if (this.pitchList.size() == 200) {
			double min = MathUtil.lowest(this.pitchList);
			double max = MathUtil.highest(this.pitchList);
			double difference = Math.abs(max - min);
			if (difference < (double)this.data.getPitchGCD() * 1.25) {
				this.fail(
					"* Weird change\n §f* d: §b" + this.format(4, Double.valueOf(difference)) + "\n §f* e: §b" + this.format(4, Double.valueOf((double)this.data.getPitchGCD() * 1.5)),
					this.getBanVL(),
					300L
				);
			}

			this.pitchList.clear();
		}
	}
}
