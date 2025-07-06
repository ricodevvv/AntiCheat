/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.registry;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import club.imanity.katana.api.KatanaLogger;
import club.imanity.katana.api.event.KatanaEvent;
import club.imanity.katana.api.event.KatanaListener;

public final class EventRegistry implements KatanaEventListenerRegistry {
	private final List<KatanaListener> listeners = new CopyOnWriteArrayList<>();
	private int errors;
	private boolean forceShutdown;

	@Override
	public boolean fireEvent(KatanaEvent event) {
		if (!this.forceShutdown) {
			try {
				this.listeners.forEach(l -> l.onEvent(event));
				return !event.isCancelled();
			} catch (Throwable var3) {
				var3.printStackTrace();
				if (++this.errors >= 5) {
					KatanaLogger.critical("The event service has been set to idle due to numerous unknown errors\nIf you still want to utilize the event system, please restart your server");
					this.forceShutdown = true;
				}

				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public void addListener(KatanaListener listener) {
		if (!this.forceShutdown) {
			if (!this.listeners.contains(listener)) {
				this.listeners.add(listener);
			}
		} else {
			throw new RuntimeException("event registry is halted");
		}
	}

	@Override
	public void removeListener(KatanaListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void shutdown() {
		this.listeners.clear();
	}

	public void setForceShutdown(boolean forceShutdown) {
		this.forceShutdown = forceShutdown;
	}
}
