/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import lombok.SneakyThrows;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(
	name = "Scaffold (H)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = false
)
public final class ScaffoldH extends PacketCheck {
	public ScaffoldH(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			if (((BlockPlaceEvent)packet).isUsableItem() || this.data.isPossiblyTeleporting() || !this.isNotGroundBridging()) {
				return;
			}

			if (this.data.deltas.deltaPitch > 15.0F && this.data.deltas.deltaYaw == 0.0F && this.data.deltas.deltaXZ > 0.2) {
				if (++this.violations > 4.0) {
					this.fail("* Weird stuff\n §f* deltaPitch: §b" + this.data.deltas.deltaPitch + "\n §f* deltaXZ: §b" + this.data.deltas.deltaXZ, this.getBanVL(), 120L);
				}
			} else {
				this.decrease(0.25);
			}
		}
	}

	@SneakyThrows
	public boolean isNotGroundBridging() {

			Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(this.data.getLocation().clone().subtract(0.0, 2.0, 0.0).toLocation(this.data.getWorld()));
			if (block == null) {
				return false;
			} else {
				return block.getType() == Material.AIR;
			}

	}
}
