/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.entity.Player;

public final class KatanaPostCheckEvent extends KatanaEvent implements RawPacketInspectableEvent {
	private final boolean failed;
	private final CheckData check;
	private final Player player;
	private final Object packet;

	@Override
	public boolean isCancellable() {
		return false;
	}

	@Override
	public Object getPacket() {
		return this.packet;
	}

	public KatanaPostCheckEvent(boolean failed, CheckData check, Player player, Object packet) {
		this.failed = failed;
		this.check = check;
		this.player = player;
		this.packet = packet;
	}

	public boolean isFailed() {
		return this.failed;
	}

	public CheckData getCheck() {
		return this.check;
	}

	public Player getPlayer() {
		return this.player;
	}
}
