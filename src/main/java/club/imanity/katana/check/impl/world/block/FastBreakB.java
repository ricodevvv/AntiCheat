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
import club.imanity.katana.event.RespawnEvent;
import club.imanity.katana.event.SwingEvent;

@CheckInfo(
	name = "FastBreak (B)",
	category = Category.WORLD,
	subCategory = SubCategory.BLOCK,
	experimental = false
)
public final class FastBreakB extends PacketCheck {
	private int blockHitDelay;

	public FastBreakB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof SwingEvent) {
			if (this.blockHitDelay > 0) {
				--this.blockHitDelay;
			}
		} else if (packet instanceof DigEvent) {
			DiggingAction digType = ((DigEvent)packet).getDigType();
			switch (digType) {
				case START_DIGGING:
					this.blockHitDelay = 5;
					break;
				case FINISHED_DIGGING:
					this.blockHitDelay = 5;
			}
		} else if (packet instanceof RespawnEvent) {
			this.blockHitDelay = 0;
		}
	}
}
