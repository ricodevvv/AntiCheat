/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.collision;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import club.imanity.katana.handler.collision.enums.Boxes;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.mc.axisalignedbb.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public final class CollisionBoxParser {
	public static AxisAlignedBB from(Entity e) {
		Location l = e.getLocation();
		AxisAlignedBB b;
		switch (e.getType()) {
			case BOAT:
				b = fromBoxEnum(l, Boxes.BOAT);
				break;
			case PLAYER:
				b = fromBoxEnum(l, Boxes.PLAYER);
				break;
			default:
				b = MathUtil.getEntityBoundingBox(l);
		}

		return b;
	}

	public static Boxes from(EntityType e) {
		return EntityTypes.BOAT.equals(e) ? Boxes.BOAT : Boxes.PLAYER;
	}

	private static AxisAlignedBB fromBoxEnum(Location l, Boxes e) {
		return new AxisAlignedBB(
			l.getX() - (double)e.getWidth(), l.getY(), l.getZ() - (double)e.getWidth(), l.getX() + (double)e.getWidth(), l.getY() + (double)e.getHeight(), l.getZ() + (double)e.getWidth()
		);
	}
}
