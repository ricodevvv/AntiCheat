/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.manager;

import com.github.retrooper.packetevents.protocol.player.User;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import club.imanity.katana.data.KatanaPlayer;
import org.bukkit.entity.Player;

public final class PlayerDataManager {
	private final Map<UUID, KatanaPlayer> playerDataMap = new ConcurrentHashMap<>();
	private final club.imanity.katana.katana katana;

	public PlayerDataManager(club.imanity.katana.katana katana) {
		this.katana = katana;
	}

	public KatanaPlayer getPlayerData(Player player) {
		return this.playerDataMap.get(player.getUniqueId());
	}

	public KatanaPlayer getPlayerData(User user) {
		return this.playerDataMap.get(user.getUUID());
	}

	public KatanaPlayer getPlayerData(UUID uuid) {
		return this.playerDataMap.get(uuid);
	}

	public KatanaPlayer remove(UUID uuid) {
		KatanaPlayer data = this.getPlayerData(uuid);
		if (data != null) {
			data.setRemovingObject(true);
			club.imanity.katana.katana.getInstance().getThreadManager().shutdownThread(data);
		}

		return this.playerDataMap.remove(uuid);
	}

	public KatanaPlayer add(UUID uuid, long now) {
		return this.playerDataMap.put(uuid, new KatanaPlayer(uuid, this.katana, now));
	}

	public Map<UUID, KatanaPlayer> getPlayerDataMap() {
		return this.playerDataMap;
	}
}
