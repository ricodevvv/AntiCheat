/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.api;

public final class AlertsX {
	public String player;
	public int status;

	public AlertsX() {
	}

	public AlertsX(String player, int status) {
		this.player = player;
		this.status = status;
	}
}
