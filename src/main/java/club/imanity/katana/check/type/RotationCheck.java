/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.type;

import club.imanity.katana.check.api.Check;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

public abstract class RotationCheck extends Check<MovementUpdate> {
	public RotationCheck(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	public void handle(MovementUpdate update) {
	}
}
