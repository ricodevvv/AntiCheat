/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.ground;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "Ground (B)",
	category = Category.WORLD,
	subCategory = SubCategory.NOFALL,
	experimental = false
)
public final class GroundB extends PacketCheck {
	public GroundB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			if (this.data.elapsed(this.data.getLastFlyTick()) <= 20 || this.data.getGameMode() == GameMode.CREATIVE && !this.data.isPossiblyTeleporting()) {
				return;
			}

			if (!this.data.isPossiblyTeleporting()
				&& this.data.getAirTicks() > 10
				&& !this.data.isOnClimbable()
				&& !this.data.isInsideBlock()
				&& !this.data.isInWeb()
				&& !this.data.isGroundNearBox()
				&& !this.data.isSpectating()
				&& !this.data.isOnGhostBlock()
				&& !this.data.isInUnloadedChunk()
				&& (this.data.isHasReceivedTransaction() || this.data.getTotalTicks() > 120)
				&& !this.data.isWasInUnloadedChunk()
				&& !this.data.isWasInWeb()
				&& this.data.elapsed(this.data.getPlaceTicks()) > Math.min(15, MathUtil.getPingInTicks(this.data.getTransactionPing() + 50L) + 2)) {
				double MAX = this.data.elapsed(this.data.getPlaceTicks()) <= Math.min(15, MathUtil.getPingInTicks(this.data.getTransactionPing() + 50L) + 5) ? 5.0 : 4.25;
				MAX += this.data.elapsed(this.data.getLastPacketDrop()) < 5 ? 2.0 : 1.25;
				if (((FlyingEvent)packet).isOnGround()) {
					if (++this.violations > MAX) {
						this.fail(
							"* Spoofed ground status\n* CT: §b"
								+ this.data.getClientAirTicks()
								+ "\n* ST: §b"
								+ this.data.getAirTicks()
								+ "\n* UNLOADED: §b"
								+ this.data.elapsed(this.data.getLastInUnloadedChunk())
								+ "\n* MOVE: §b"
								+ this.data.getMoveTicks()
								+ "\n* NOMOVE: §b"
								+ this.data.getNoMoveTicks(),
							this.getBanVL(),
							40L
						);
					}
				} else {
					this.violations = Math.max(this.violations - 0.1, 0.0);
				}
			}
		}
	}
}
