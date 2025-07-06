/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util;

public class Teleport {
	public final TeleportPosition position;
	public boolean accepted = false;
	public boolean moved = false;

	public Teleport(TeleportPosition position) {
		this.position = position;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
