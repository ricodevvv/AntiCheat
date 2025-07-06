/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;

@CheckInfo(
	name = "Scaffold (A)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = false
)
public final class ScaffoldA extends PacketCheck {
	private long lastFlying;

	public ScaffoldA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			long diff = ((BlockPlaceEvent)packet).getTimeMillis() - this.lastFlying;
			if (((BlockPlaceEvent)packet).getItemStack() != null && ((BlockPlaceEvent)packet).getItemStack().getType().isBlock() && !this.data.isPossiblyTeleporting()) {
				if (diff >= 10L
					|| this.data.isLagging(this.data.getTotalTicks())
					|| this.data.elapsed(this.data.getLastPacketDrop()) <= 5
					|| this.getKatana().isServerLagging(((BlockPlaceEvent)packet).getTimeMillis())
					|| !(this.getKatana().getTPS() >= 19.98)) {
					this.decrease(0.8);
				} else if (++this.violations > 8.0) {
					this.fail("* Irregular place\n §f* delta: §b" + diff + "\n §f* deltaXZ: §b" + this.data.deltas.deltaXZ, this.getBanVL(), 120L);
				}
			}
		} else if (packet instanceof FlyingEvent) {
			this.lastFlying = ((FlyingEvent)packet).getCurrentTimeMillis();
		}
	}
}
