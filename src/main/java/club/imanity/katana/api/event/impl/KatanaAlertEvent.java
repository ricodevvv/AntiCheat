/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.data.CheckData;
import club.imanity.katana.api.data.DebugData;
import club.imanity.katana.api.event.KatanaEvent;
import org.bukkit.entity.Player;

public final class KatanaAlertEvent extends KatanaEvent {
	private final Player player;
	private final CheckData check;
	private final DebugData debug;
	private final int violations;

	public Player getPlayer() {
		return this.player;
	}

	public CheckData getCheck() {
		return this.check;
	}

	public DebugData getDebug() {
		return this.debug;
	}

	public int getViolations() {
		return this.violations;
	}

	public KatanaAlertEvent(Player player, CheckData check, DebugData debug, int violations) {
		this.player = player;
		this.check = check;
		this.debug = debug;
		this.violations = violations;
	}
}
