/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.api;

public final class BanWaveX {
	public String player;
	public String type;
	public int totalLogs;
	public long time;

	public BanWaveX() {
	}

	public BanWaveX(String player, String type, int totalLogs, long time) {
		this.player = player;
		this.type = type;
		this.totalLogs = totalLogs;
		this.time = time;
	}
}
