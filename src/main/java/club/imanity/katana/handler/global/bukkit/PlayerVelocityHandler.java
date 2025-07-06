/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.global.bukkit;

import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class PlayerVelocityHandler implements Listener {
	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onPlayerVelocity(PlayerVelocityEvent event) {
		if (!event.isCancelled()) {
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(event.getPlayer().getUniqueId());
			if (data != null) {
				data.sendTransaction();
				data.setBrokenVelocityVerify(!data.hasSentTickFirst);
				data.setPlayerVelocityCalled(true);
			}
		}
	}

	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onExplode(EntityExplodeEvent event) {
		if (!event.isCancelled()) {
			for (Entity entity : event.getEntity().getNearbyEntities(4.0, 4.0, 4.0)) {
				if (entity instanceof Player && !entity.hasMetadata("NPC")) {
					KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(((Player)entity).getUniqueId());
					if (data != null) {
						data.sendTransaction();
						data.setPlayerExplodeCalled(true);
						data.setBrokenVelocityVerify(true);
						data.setPlayerVelocityCalled(true);
					}
				}
			}
		}
	}
}
