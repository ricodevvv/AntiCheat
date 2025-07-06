/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler;

import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.handler.interfaces.IVehicleHandler;
import club.imanity.katana.manager.alert.MiscellaneousAlertPoster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;

public final class VehicleHandler implements IVehicleHandler {
	private final KatanaPlayer data;
	private int lastDismount;

	@Override
	public void handle(Entity e) {
		if (this.data.getTotalTicks() >= 20) {
			if (e == this.data.getBukkitPlayer()) {
				if (katana.getInstance().getConfigManager().isVehicleHandler()) {
					MiscellaneousAlertPoster.postMisc(
						katana.getInstance().getConfigManager().getConfig().getString("VehicleHandlerMessage").replaceAll("%player%", this.data.getName()), this.data, "Vehicle"
					);
					this.forceDismount();
				}
			} else if (e instanceof Vehicle) {
				double dist = e.getLocation().distanceSquared(this.data.getLocation().toLocation(this.data.getWorld()));
				if (dist <= 20.0) {
					this.data.setRiding(true);
				}
			}
		}
	}

	@Override
	public void handleMove() {
		if (this.data.getTotalTicks() - this.lastDismount > 1) {
			this.data.setExitingVehicle(false);
		}
	}

	private void forceDismount() {
		if (katana.getInstance().getConfigManager().isVehicleHandler() && this.data.getBukkitPlayer().getVehicle() != null && this.data.getBukkitPlayer().isInsideVehicle()) {
			this.data.getBukkitPlayer().leaveVehicle();
		}
	}

	public VehicleHandler(KatanaPlayer data) {
		this.data = data;
	}
}
