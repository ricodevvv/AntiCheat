/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.interfaces;

import club.imanity.katana.util.location.CustomLocation;

public interface ICrashHandler {
	void handleClientKeepAlive();

	void handleFlying(boolean boolean1, boolean boolean2, CustomLocation customLocation3, CustomLocation customLocation4);

	void handleArm();

	void handleWindowClick(int integer1, int integer2, int integer3, int integer4);

	void handleSlot();

	void handleCustomPayload();

	void handlePlace();
}
