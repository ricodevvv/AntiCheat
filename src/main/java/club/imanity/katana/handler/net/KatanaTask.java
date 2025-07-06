/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.net;

import club.imanity.katana.util.gui.Callback;

public class KatanaTask {
	private int id;
	private final Callback<Integer> callback;

	public KatanaTask(Callback<Integer> callback) {
		this.callback = callback;
	}

	public KatanaTask(Callback<Integer> callback, int id) {
		this.callback = callback;
		this.id = id;
	}

	public void runTask() {
		this.callback.call(this.id);
	}

	public int getId() {
		return this.id;
	}

	public Callback<Integer> getCallback() {
		return this.callback;
	}

	public void setId(int id) {
		this.id = id;
	}
}
