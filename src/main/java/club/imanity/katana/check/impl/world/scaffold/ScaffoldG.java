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
import club.imanity.katana.util.MathUtil;

@CheckInfo(
	name = "Scaffold (G)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = true
)
public final class ScaffoldG extends PacketCheck {
	private double lastDeltaPitch;

	public ScaffoldG(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent && !((BlockPlaceEvent)packet).isUsableItem()) {
			boolean validY = MathUtil.isNearlySame(this.data.getLocation().y, (double)((BlockPlaceEvent)packet).getBlockPos().getBlockY(), 2.0);
			if ((double)this.data.deltas.deltaPitch == this.lastDeltaPitch
				&& this.data.deltas.deltaYaw == 0.0F
				&& this.data.deltas.deltaXZ > 0.21
				&& this.data.deltas.deltaPitch > 1.0F
				&& validY) {
				if (++this.violations > 35.0) {
					this.fail("* Weird stuff\n §f* deltaPitch: §b" + this.data.deltas.deltaPitch + "\n §f* deltaXZ: §b" + this.data.deltas.deltaXZ, this.getBanVL(), 120L);
				}
			} else {
				this.decrease(0.35);
			}

			this.lastDeltaPitch = (double)this.data.deltas.deltaPitch;
		}
	}
}
