/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.api;

public final class BanX {
	public String player;
	public String type;
	public long time;
	public String data;
	public long ping;
	public double TPS;

	public BanX() {
	}

	public BanX(String player, String type, long time, String data, long ping, double TPS) {
		this.player = player;
		this.type = type;
		this.time = time;
		this.data = data;
		this.ping = ping;
		this.TPS = TPS;
	}
}
