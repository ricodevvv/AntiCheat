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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
	name = "Scaffold (I)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = true
)
public final class ScaffoldI extends PacketCheck {
	public ScaffoldI(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			ItemStack stack = ((BlockPlaceEvent)packet).getItemStack();
			boolean validBlock = stack != null && stack.getType() != Material.AIR;
			if (validBlock && !((BlockPlaceEvent)packet).isUsableItem()) {
				if (this.data.deltas.deltaYaw > 1.5F && this.data.deltas.deltaXZ > 0.12 && MathUtil.isNearlySame(this.data.deltas.accelXZ, this.data.deltas.lastAccelXZ, 1.0E-7)) {
					if (++this.violations > 3.0) {
						this.fail(
							"* Not slowing down\n §f* deltaYaw: §b"
								+ this.data.deltas.deltaYaw
								+ "\n §f* deltaXZ: §b"
								+ this.data.deltas.deltaXZ
								+ "\n §f* ac: §b"
								+ Math.abs(this.data.deltas.accelXZ - this.data.deltas.lastAccelXZ),
							this.getBanVL(),
							150L
						);
					}
				} else {
					this.violations = Math.max(this.violations - 0.075, 0.0);
				}
			}
		}
	}
}
