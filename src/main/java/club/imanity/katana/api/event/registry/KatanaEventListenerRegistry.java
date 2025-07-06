/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.api.event.registry;

import club.imanity.katana.api.event.KatanaEvent;
import club.imanity.katana.api.event.KatanaListener;

public interface KatanaEventListenerRegistry {
	boolean fireEvent(KatanaEvent katanaEvent);

	void shutdown();

	void addListener(KatanaListener katanaListener);

	void removeListener(KatanaListener katanaListener);
}
