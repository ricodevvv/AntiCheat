/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.data.task;

public interface IAbstractTickTask<T> {
	Runnable getRunnable();

	EmptyPredicate conditionUntil();

	String getId();
}
