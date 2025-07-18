/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.event;

public class AttackEvent extends Event {
	private final int entityId;
	private final long now;
	private final long timeMillis;
	private final boolean player;

	public AttackEvent(int entityId, boolean player, long now, long timeMillis) {
		this.entityId = entityId;
		this.player = player;
		this.now = now;
		this.timeMillis = timeMillis;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public long getNow() {
		return this.now;
	}

	public long getTimeMillis() {
		return this.timeMillis;
	}

	public boolean isPlayer() {
		return this.player;
	}
}
