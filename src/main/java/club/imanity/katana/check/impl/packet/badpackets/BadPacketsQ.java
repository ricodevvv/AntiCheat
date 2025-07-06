/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.packet.badpackets;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;

@CheckInfo(
	name = "BadPackets (Q)",
	category = Category.PACKET,
	subCategory = SubCategory.BADPACKETS,
	experimental = false
)
public final class BadPacketsQ extends PacketCheck {
	public BadPacketsQ(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (packet instanceof BlockPlaceEvent && club.imanity.katana.katana.SERVER_VERSION.isOlderThanOrEquals(ServerVersion.V_1_8_8)) {
			int face = ((BlockPlaceEvent)packet).getFace();
			double blockX = ((BlockPlaceEvent)packet).getBlockX();
			double blockY = ((BlockPlaceEvent)packet).getBlockY();
			double blockZ = ((BlockPlaceEvent)packet).getBlockZ();
			boolean invalidX = blockX > 1.0 || blockX < 0.0;
			boolean invalidY = blockY > 1.0 || blockY < 0.0;
			boolean invalidZ = blockZ > 1.0 || blockZ < 0.0;
			if (invalidX || invalidY || invalidZ) {
				this.fail("* Invalid blockplace\n §f* FACE: §b" + face + "\n §f* SUM2: §b" + (blockX + blockY + blockZ), this.getBanVL(), 310L);
			}
		}
	}
}
