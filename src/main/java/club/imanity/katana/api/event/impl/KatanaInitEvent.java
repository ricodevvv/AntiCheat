/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.impl;

import club.imanity.katana.api.event.KatanaEvent;

public final class KatanaInitEvent extends KatanaEvent {
	private final long loadTime;

	@Override
	public boolean isCancellable() {
		return false;
	}

	public long getLoadTime() {
		return this.loadTime;
	}

	public KatanaInitEvent(long loadTime) {
		this.loadTime = loadTime;
	}
}
