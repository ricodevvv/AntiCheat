/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.collision;

import club.imanity.katana.katana;
import club.imanity.katana.handler.collision.enums.CollisionType;
import club.imanity.katana.handler.collision.type.MaterialChecks;
import club.imanity.katana.handler.interfaces.AbstractPredictionHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class BlockCollisionHandler {
	public static void run(Block b, CollisionType type, AbstractPredictionHandler data) {
		if (katana.SERVER_VERSION.getProtocolVersion() >= 47) {
			if (b.getType() != Material.AIR) {
				switch (type) {
					case LANDED:
						if (!MaterialChecks.SLIME.contains(b.getType()) || !(Math.abs(data.motY) < 0.1) || data.getData().isSneaking()) {
							break;
						}

						double d0 = 0.4 + Math.abs(data.motY) * 0.2;
						data.motX *= d0;
						data.motZ *= d0;
					case WALKING:
						if (MaterialChecks.SLIME.contains(b.getType())) {
							if (data.getData().isSneaking()) {
								data.motY = 0.0;
							} else if (data.motY < 0.0) {
								data.motY = -data.motY;
							}
						} else {
							data.motY = 0.0;
						}
				}
			}
		}
	}
}
