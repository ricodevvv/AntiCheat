/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.type;

import club.imanity.katana.check.api.Check;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

public abstract class PositionCheck extends Check<MovementUpdate> {
	protected static final double JUMP_MOMENTUM = 0.42F;
	protected static final double WORLD_GRAVITY = 0.08;
	protected static final double VERTICAL_AIR_FRICTION = 0.98F;
	protected static final float JUMP_MOVEMENT_FACTOR = 0.026F;
	protected static final float LAND_MOVEMENT_FACTOR = 0.16277136F;
	protected static final float SPRINT_BOOST = 1.3F;
	protected static final float AIR_FRICTION = 0.91F;
	protected static final double JUMP_BOOST = 0.2;

	public PositionCheck(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	public void handle(MovementUpdate update) {
	}
}
