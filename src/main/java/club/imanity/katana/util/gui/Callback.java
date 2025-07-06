/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.gui;

@FunctionalInterface
public interface Callback<T> {
	void call(T object);
}
