/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.water;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PositionCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.update.MovementUpdate;

@CheckInfo(
	name = "Jesus (B)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.JESUS,
	experimental = true
)
public final class JesusB extends PositionCheck {
	private int zeroTicks;

	public JesusB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(MovementUpdate update) {
		if ((this.data.isOnLiquid() || this.data.isAboveButNotInWater())
			&& this.data.elapsed(this.data.getUnderPlaceTicks()) > this.data.getPingInTicks() + 5
			&& this.data.elapsed(this.data.getLastFlyTick()) > 30) {
			double minMove = 0.001;
			if (club.imanity.katana.katana.SERVER_VERSION.isNewerThanOrEquals(ServerVersion.V_1_13)) {
				if (this.data.getBukkitPlayer().isSwimming()) {
					this.zeroTicks = 0;
					return;
				}
			} else if (this.data.getClientVersion().getProtocolVersion() > 340) {
				this.zeroTicks = 0;
				return;
			}

			if (this.data.getAirTicks() > 4
				&& !this.data.isUnderBlock()
				&& !this.data.isWasUnderBlock()
				&& !this.data.isInWeb()
				&& !this.data.isOnWeb()
				&& !this.data.isPossiblyTeleporting()) {
				if (Math.abs(this.data.deltas.motionY) < minMove && Math.abs(this.data.deltas.motionY) > 0.0) {
					if (++this.violations > 3.0) {
						this.fail(
							"* Illegal y-axis movement in liquid\n§f* Inside §b"
								+ this.data.isOnLiquid()
								+ "\n§f* Above §b"
								+ this.data.isAboveButNotInWater()
								+ "\n§f* motionY §b"
								+ Math.abs(this.data.deltas.motionY),
							this.getBanVL(),
							200L
						);
					}

					this.zeroTicks = 0;
				} else if (Math.abs(this.data.deltas.motionY) == 0.0) {
					if (++this.zeroTicks > 3) {
						this.fail(
							"* Illegal y-axis movement in liquid (zeros)\n§f* Inside §b"
								+ this.data.isOnLiquid()
								+ "\n§f* Above §b"
								+ this.data.isAboveButNotInWater()
								+ "\n§f* motionY §b"
								+ Math.abs(this.data.deltas.motionY),
							this.getBanVL(),
							200L
						);
					}
				} else {
					this.violations = Math.max(this.violations - 0.05, 0.0);
					this.zeroTicks = 0;
				}
			}
		}
	}
}
