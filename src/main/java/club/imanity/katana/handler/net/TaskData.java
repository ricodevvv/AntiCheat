/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.net;

import java.util.LinkedList;
import club.imanity.katana.util.gui.Callback;

public class TaskData {
	private final int id;
	private long timestamp;
	private final LinkedList<KatanaTask> tasks = new LinkedList<>();

	public TaskData(int id, long timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}

	public TaskData(int id, Callback<Integer> callback) {
		this.id = id;
		this.addTask(callback);
	}

	public void addTask(Callback<Integer> callback) {
		this.tasks.add(new KatanaTask(callback, this.id));
	}

	public void addTask(KatanaTask katanaTask) {
		katanaTask.setId(this.id);
		this.tasks.add(katanaTask);
	}

	public void consumeTask() {
		this.tasks.forEach(KatanaTask::runTask);
	}

	public boolean hasTask() {
		return this.tasks.size() > 0;
	}

	public int getId() {
		return this.id;
	}

	public long getTimestamp() {
		return this.timestamp;
	}
}
