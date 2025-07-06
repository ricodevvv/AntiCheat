/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class KatanaPullbackEvent extends KatanaEvent {
	private final Player player;
	private final CheckData check;
	private Location to;

	public KatanaPullbackEvent(Player player, CheckData check, Location to) {
		this.player = player;
		this.check = check;
		this.to = to.clone();
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public Player getPlayer() {
		return this.player;
	}

	public CheckData getCheck() {
		return this.check;
	}

	public Location getTo() {
		return this.to;
	}
}
