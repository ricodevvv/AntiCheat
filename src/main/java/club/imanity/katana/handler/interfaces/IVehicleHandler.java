/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.interfaces;

import org.bukkit.entity.Entity;

public interface IVehicleHandler {
	void handle(Entity entity);

	void handleMove();
}
