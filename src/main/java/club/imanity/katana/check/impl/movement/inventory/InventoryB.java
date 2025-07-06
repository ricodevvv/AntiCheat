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
	name = "Inventory (B)",
	category = Category.MOVEMENT,
	subCategory = SubCategory.INVENTORY,
	experimental = true
)
public final class InventoryB extends PacketCheck {
	public InventoryB(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof WindowEvent) {
			if (this.data.getLastAttackTick() <= 1) {
				if (++this.violations > 1.0) {
					this.fail("* Attacking while clicking inventory\n §f* deltaXZ §b" + this.data.deltas.deltaXZ, this.getBanVL(), 300L);
					if (this.violations > 2.0) {
						Player player = this.data.getBukkitPlayer();
						if (player != null) {
							Tasker.run(player::closeInventory);
							MiscellaneousAlertPoster.postMitigation(this.data.getName() + " -> inventory closed (B)");
						}
					}
				}
			} else {
				this.violations = Math.max(this.violations - 0.5, 0.0);
			}
		}
	}
}
