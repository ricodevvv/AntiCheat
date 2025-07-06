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
import club.imanity.katana.event.FlyingEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;

@CheckInfo(
	name = "Scaffold (D)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = false
)
public final class ScaffoldD extends PacketCheck {
	private int delay;

	public ScaffoldD(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent) {
			int face = ((BlockPlaceEvent)packet).getFace();
			if (((BlockPlaceEvent)packet).isUsableItem()) {
				return;
			}

			if (face < 0 || face > 6) {
				return;
			}

			this.delay = 0;
		} else if (packet instanceof FlyingEvent
			&& ++this.delay <= 6
			&& !this.data.isPossiblyTeleporting()
			&& this.isNotGroundBridging()
			&& ((FlyingEvent)packet).hasLooked()
			&& Math.abs(((FlyingEvent)packet).getPitch()) < 90.0F) {
			if (this.data.deltas.deltaPitch < 1.0F && this.data.deltas.deltaYaw > 200.0F
				|| this.data.deltas.deltaPitch > 50.0F && this.data.deltas.deltaYaw < 1.0F
				|| this.data.deltas.deltaPitch < 70.0F
					&& this.data.deltas.deltaPitch > 20.0F
					&& this.data.deltas.deltaYaw < 200.0F
					&& this.data.deltas.deltaYaw > 100.0F
					&& this.data.deltas.deltaXZ > 0.16) {
				if (++this.violations > 5.25) {
					this.fail(
						"* Invalid rotation\n §f* X | Y: §b" + this.data.deltas.deltaYaw + " | " + this.data.deltas.deltaPitch + "\n §f* deltaXZ: §b" + this.data.deltas.deltaXZ,
						this.getBanVL(),
						120L
					);
				}
			} else {
				this.violations = Math.max(this.violations - 0.125, 0.0);
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
