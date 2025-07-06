/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.movement.inventory;

import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.WindowEvent;
import club.imanity.katana.manager.alert.MiscellaneousAlertPoster;
import club.imanity.katana.util.task.Tasker;
import org.bukkit.entity.Player;

@CheckInfo(
	name = "Inventory (A)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.INVENTORY,
	experimental = true
)
public final class InventoryA extends PacketCheck {
	public InventoryA(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof WindowEvent) {
			if (this.data.elapsed(this.data.getLastPistonPush()) <= 3) {
				return;
			}

			double offsetH = this.data.deltas.deltaXZ;
			double lastOffsetH = this.data.deltas.lastDXZ;
			if (!(offsetH - lastOffsetH >= 0.0) || !(offsetH > 0.1) || this.data.isAllowFlying() || this.data.isPossiblyTeleporting() || this.data.getVelocityHorizontal() != 0.0) {
				this.violations = Math.max(this.violations - 0.5, 0.0);
			} else if (++this.violations > (double)(this.data.isSprinting() ? 2 : 4)) {
				this.fail("* Moving while clicking inventory slots\n §f* deltaXZ §b" + offsetH, this.getBanVL(), 300L);
				if (this.violations > (double)(this.data.isSprinting() ? 3 : 5)) {
					Player player = this.data.getBukkitPlayer();
					if (player != null) {
						Tasker.run(player::closeInventory);
						MiscellaneousAlertPoster.postMitigation(this.data.getName() + " -> inventory closed (A)");
					}
				}
			}
		}
	}
}
