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
	name = "Motion (C)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.MOTION,
	experimental = false
)
public final class MotionC extends PositionCheck {
	public MotionC(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate e) {
	}
}
