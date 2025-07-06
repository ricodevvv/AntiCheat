/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.block;

import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.DigEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.event.RespawnEvent;

@CheckInfo(
	name = "FastBreak (A)",
	category = Category.WORLD,
	subCategory = SubCategory.BLOCK,
	experimental = false
)
public final class FastBreakA extends PacketCheck {
	private boolean tickStarted;

	public FastBreakA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (this.data.getClientVersion().getProtocolVersion() <= 47) {
			if (packet instanceof FlyingEvent) {
				this.tickStarted = false;
			} else if (packet instanceof DigEvent) {
				DiggingAction digType = ((DigEvent)packet).getDigType();
				switch (digType) {
					case START_DIGGING:
						this.tickStarted = true;
						break;
					case FINISHED_DIGGING:
						if (this.tickStarted) {
							this.fail("* Fastbreak (instant)\n\n(no debug provided)", this.getBanVL(), 600L);
						}
				}
			} else if (packet instanceof RespawnEvent) {
				this.tickStarted = false;
			}
		}
	}
}
