/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.entity.Player;

public final class KatanaPlayerUnregisterEvent extends KatanaEvent {
	private final Player player;

	@Override
	public boolean isCancellable() {
		return false;
	}

	public KatanaPlayerUnregisterEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}
}
