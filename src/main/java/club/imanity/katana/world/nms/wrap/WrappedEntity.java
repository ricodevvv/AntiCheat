/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.world.nms.wrap;

public abstract class WrappedEntity {
	protected final float w;
	protected final float h;

	public WrappedEntity(float height, float width) {
		this.h = height;
		this.w = width;
	}
}
