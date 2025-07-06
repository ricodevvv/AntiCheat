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
	name = "Ground (A)",
	category = Category.WORLD,
	subCategory = SubCategory.NOFALL,
	experimental = false
)
public final class GroundA extends PacketCheck {
	private int noGroundTicks;

	public GroundA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent && this.data.deltas.motionY != 0.0 && this.data.deltas.deltaXZ >= 0.031) {
			if (this.data.elapsed(this.data.getLastFlyTick()) <= 20 || this.data.getGameMode() == GameMode.CREATIVE) {
				return;
			}

			if (!this.data.isOnGroundServer()
				&& !this.data.isWasOnComparator()
				&& !this.data.isOnComparator()
				&& !this.data.isOnLiquid()
				&& !this.data.isPossiblyTeleporting()
				&& !this.data.isOnGhostBlock()
				&& !this.data.isInUnloadedChunk()
				&& !this.data.isOnClimbable()
				&& !this.data.isSpectating()
				&& !this.data.isRiding()
				&& !this.data.isCollidedHorizontally()) {
				if (this.data.isOnGroundPacket() && this.data.elapsed(this.data.getPlaceTicks()) > Math.min(15, MathUtil.getPingInTicks(this.data.getTransactionPing() + 50L) + 6)) {
					++this.noGroundTicks;
				} else {
					this.noGroundTicks = Math.max(this.noGroundTicks - 5, 0);
				}

				if (this.noGroundTicks >= 10) {
					if (++this.violations > 1.0) {
						this.fail("ticks=" + this.noGroundTicks + " server=" + this.data.isOnGroundServer() + " client=" + this.data.isOnGroundPacket(), this.getBanVL(), 100L);
					}
				} else {
					this.violations *= 0.95;
				}
			}
		}
	}
}
