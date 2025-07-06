/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.player.MovementUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class PlayerHandler {
	public static void checkConditions(KatanaPlayer data) {
		if (data.getBukkitPlayer() != null) {
			if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_7_10)) {
				data.setDepthStriderLevel(MovementUtils.getDepthStriderLevel(data.getBukkitPlayer()));
			}

			if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_8_8)) {
				data.setGliding(data.getBukkitPlayer().isGliding());
				data.setLastGlide(data.getBukkitPlayer().isGliding() ? data.getTotalTicks() : data.getLastGlide());
			}

			if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
				data.setRiptiding(data.getBukkitPlayer().isRiptiding());
				data.setLastRiptide(data.getBukkitPlayer().isRiptiding() ? data.getTotalTicks() : data.getLastRiptide());
			}

			if (katana.SERVER_VERSION.isNewerThanOrEquals(ServerVersion.V_1_16)) {
				data.setSoulSpeedLevel(MovementUtils.getSoulSpeedLevel(data.getBukkitPlayer()));
			}
		}
	}

	public static boolean hasEnchantment(ItemStack item, Enchantment enchantment) {
		return item.getEnchantments().containsKey(enchantment);
	}
}
