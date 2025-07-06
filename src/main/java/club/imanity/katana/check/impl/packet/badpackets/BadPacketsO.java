/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.util.Vector3f;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.InteractEvent;

@CheckInfo(
	name = "BadPackets (O)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsO extends PacketCheck {
	public BadPacketsO(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof InteractEvent) {
			InteractEvent interactEvent = (InteractEvent)packet;
			if (interactEvent.isPlayer()) {
				Vector3f vector3d = interactEvent.getVec3D();
				if (vector3d != null && interactEvent.isAt()) {
					double x = (double)Math.abs(vector3d.x);
					double y = (double)vector3d.y;
					double z = (double)Math.abs(vector3d.z);
					EntityType e = this.data.getEntityData().get(((InteractEvent)packet).getEntityId()).getType();
					double expandX = x - 0.4005;
					double expandY = y - 1.905;
					double expandZ = z - 0.4005;
					String entityName = e.getName().toString();
					if (expandX > 0.0 || expandY > 0.0 || y < -0.105 || expandZ > 0.0) {
						int expand = (int)Math.round((expandX > 0.0 ? expandX : (0.0 + expandY > 0.0 ? expandY : (0.0 + expandZ > 0.0 ? expandZ : 0.0))) * 100.0);
						this.fail(
							"* Wrong hitbox in packet\n§f* expand §b" + expand + "%" + String.format("\n§f* x§b %.3f§f, y§b %.3f§f, z§b %.3f", expandX, expandY, expandZ) + "\n§f* type §b" + entityName,
							420L
						);
					}
				}
			}
		}
	}
}
