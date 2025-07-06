/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event;

import club.imanity.katana.api.exception.EventNotCancellableException;

public abstract class KatanaEvent {
	private boolean cancelled = false;

	public final void cancel() {
		if (!this.isCancellable()) {
			throw new EventNotCancellableException(this);
		} else {
			this.cancelled = true;
		}
	}

	public boolean isCancellable() {
		return true;
	}

	public final boolean isCancelled() {
		return this.isCancellable() && this.cancelled;
	}
}
