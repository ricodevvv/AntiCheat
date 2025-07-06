/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "BadPackets (N)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = true
)
public final class BadPacketsN extends PacketCheck {
	private int lastMove;

	public BadPacketsN(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof FlyingEvent) {
			boolean collidedHorizontally = this.data.elapsed(this.data.getLastCollided()) < 10 && this.data.elapsed(this.data.getLastCollidedV()) > 10;
			if (!collidedHorizontally && ((FlyingEvent)packet).hasMoved()) {
				if (this.lastMove++ >= 3) {
					double moveDist = this.data.getLocation().toVector().distanceSquared(this.data.getLastLocation().toVector());
					boolean invalid = moveDist <= 1.0E-12 && moveDist != 0.0 && !this.data.isPossiblyTeleporting();
					if (invalid && !this.data.isRiding() && !this.data.recentlyTeleported(5)) {
						++this.violations;
						if (this.violations >= 2.5) {
							this.fail("Invalid movement packet state", this.getBanVL(), 300L);
						}
					} else {
						this.violations = Math.max(this.violations - 0.05, 0.0);
					}
				}
			} else {
				this.lastMove = 0;
			}
		}
	}
}
