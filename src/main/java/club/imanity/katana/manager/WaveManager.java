/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.manager;

import java.util.ArrayList;
import java.util.UUID;
import club.imanity.katana.katana;
import club.imanity.katana.check.api.BanWaveX;
import club.imanity.katana.util.task.Tasker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WaveManager {
	private final ArrayList<String> playersToBan = new ArrayList<>();
	private boolean runningBanwave = false;
	private int bans = 0;

	public void startBanwave() {
		Tasker.taskAsync(() -> {
			int max = this.playersToBan.size();
			if (max > 0) {
				if (!this.runningBanwave) {
					this.importFromDb();
				}

				this.runningBanwave = true;
				++this.bans;
				String pre = this.playersToBan.get(0);
				String player = pre.contains("-") ? this.findName(pre) : pre;
				Bukkit.broadcastMessage(katana.getInstance().getConfigManager().getBanwaveCaught().replaceAll("%player%", player));
				this.removeFromWave(pre);
				String punish = katana.getInstance().getConfigManager().getBanwavePunish().replaceAll("%player%", player);
				if (!katana.getInstance().getConfigManager().isBungeeCommand()) {
					Tasker.run(() -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), punish));
				}

				Tasker.runTaskLaterAsync(this::startBanwave, 20L);
			} else {
				this.completeBanWave();
			}
		});
	}

	public void completeBanWave() {
		Bukkit.getScheduler().runTaskLaterAsynchronously(katana.getInstance().getPlug(), () -> {
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(katana.getInstance().getConfigManager().getBanwaveComplete().replaceAll("%bans%", String.valueOf(this.bans)));
			Bukkit.broadcastMessage("");
			this.bans = 0;
			this.runningBanwave = false;
		}, 20L);
	}

	public void addToWave(String uuid, String check) {
		if (!this.playersToBan.contains(uuid)) {
			this.playersToBan.add(uuid);
			BanWaveX bwRequest = new BanWaveX(uuid, check, 1, System.currentTimeMillis());
			katana.getStorage().addToBanWave(bwRequest);
		}
	}

	public void removeFromWave(String uuid) {
		this.playersToBan.remove(uuid);
		katana.getStorage().removeFromBanWave(uuid);
	}

	public void importFromDb() {
		this.playersToBan.clear();
		this.playersToBan.addAll(katana.storage.getBanwaveList());
	}

	private String findName(String arg) {
		Player target = Bukkit.getPlayer(UUID.fromString(arg));
		return target != null ? target.getName() : Bukkit.getOfflinePlayer(UUID.fromString(arg)).getName();
	}

	public ArrayList<String> getPlayersToBan() {
		return this.playersToBan;
	}

	public boolean isRunningBanwave() {
		return this.runningBanwave;
	}

	public int getBans() {
		return this.bans;
	}
}
