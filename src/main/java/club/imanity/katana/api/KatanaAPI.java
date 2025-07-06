/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api;

import club.imanity.katana.api.event.registry.EventRegistry;
import club.imanity.katana.api.event.registry.KatanaEventListenerRegistry;
import club.imanity.katana.api.users.UserAccessor;
import org.bukkit.plugin.java.JavaPlugin;

public final class KatanaAPI extends JavaPlugin {
	private static KatanaEventListenerRegistry eventRegistry = null;
	private static UserAccessor userAccessor = null;

	public void onEnable() {
	}

	public void onDisable() {
		eventRegistry.shutdown();
	}

	public static UserAccessor getUserAccessor() {
		if (userAccessor == null) {
			userAccessor = new UserAccessor();
		}

		return userAccessor;
	}

	public static KatanaEventListenerRegistry getEventRegistry() {
		if (eventRegistry == null) {
			eventRegistry = new EventRegistry();
		}

		return eventRegistry;
	}

	public static void shutdown() {
		if (eventRegistry != null) {
			eventRegistry.shutdown();
		}
	}

	public static long getFreeMemory() {
		Runtime r = Runtime.getRuntime();
		return r.freeMemory() / 1024L / 1024L;
	}

	public static long getMaxMemory() {
		Runtime r = Runtime.getRuntime();
		return r.maxMemory() / 1024L / 1024L;
	}

	public static long getTotalMemory() {
		Runtime r = Runtime.getRuntime();
		return r.totalMemory() / 1024L / 1024L;
	}
}
