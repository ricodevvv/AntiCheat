/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api;

public final class KatanaLogger {
	public static void info(String info) {
		System.out.println("[Katana] " + info);
	}

	public static void critical(String info) {
		System.out.println("");
		System.out.println("[Katana] ERROR: " + info);
		System.out.println("");
	}
}
