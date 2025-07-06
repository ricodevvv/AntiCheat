/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import club.imanity.katana.katana;

public class Thread {
	private final ExecutorService executorService = Executors.newSingleThreadExecutor(
		new ThreadFactoryBuilder().setNameFormat("katana-user-thread-" + katana.getInstance().getThreadManager().getUserThreads().size()).build()
	);
	public int count;

	public ExecutorService getExecutorService() {
		return this.executorService;
	}

	public int getCount() {
		return this.count;
	}
}
