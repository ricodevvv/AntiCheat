/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.interfaces;

public interface KatanaHandler {
	void handleLastTicks();

	void handle(boolean boolean1);

	void cacheBlocks();

	void handleTicks();
}
