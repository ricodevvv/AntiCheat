/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.entity.Player;

public final class KatanaBanEvent extends KatanaEvent {
	private final Player player;
	private final CheckData check;

	public Player getPlayer() {
		return this.player;
	}

	public CheckData getCheck() {
		return this.check;
	}

	public KatanaBanEvent(Player player, CheckData check) {
		this.player = player;
		this.check = check;
	}
}
