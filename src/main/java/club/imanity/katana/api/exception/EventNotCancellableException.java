/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.exception;

import club.imanity.katana.api.event.KatanaEvent;

public final class EventNotCancellableException extends RuntimeException {
	public EventNotCancellableException(KatanaEvent event) {
		super(event.getClass().getSimpleName() + " cannot be force-cancelled (is it cancellable?)");
	}
}
