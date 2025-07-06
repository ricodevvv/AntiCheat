/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.entity.Player;

public final class KatanaPreCheckEvent extends KatanaEvent implements RawPacketInspectableEvent {
	private final Player player;
	private final CheckData check;
	private final Object packet;

	@Override
	public Object getPacket() {
		return this.packet;
	}

	public Player getPlayer() {
		return this.player;
	}

	public CheckData getCheck() {
		return this.check;
	}

	public KatanaPreCheckEvent(Player player, CheckData check, Object packet) {
		this.player = player;
		this.check = check;
		this.packet = packet;
	}
}
