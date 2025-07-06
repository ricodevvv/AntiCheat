/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class VersionBridgeHelper {
	public static ItemStack getStackInHand(KatanaPlayer data) {
		return getStackInHand(data.getBukkitPlayer(), data);
	}

	public static ItemStack getStackInHand(Player data, KatanaPlayer katanaPlayer) {
		return katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_8_8) ? data.getInventory().getItemInHand() : katanaPlayer.getStackInHand();
	}

	public static ItemStack getStackInOffHand(Player data) {
		return katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_8_8) ? data.getInventory().getItemInHand() : null;
	}

	public static boolean hasGravity(Entity e) {
		return !katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_9_4) || e.hasGravity();
	}

	public static boolean isInvulnerable(Entity e) {
		return katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2) && e.isInvulnerable();
	}

	public static boolean isValidAttack(KatanaPlayer data, boolean sprinting, ItemStack s, Entity entity) {
		if (!data.isNewerThan8()) {
			return entity instanceof Player && (sprinting || s != null && s.hasItemMeta() && s.getItemMeta().hasEnchant(Enchantment.KNOCKBACK));
		} else if (data.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_16)) {
			return false;
		} else {
			float f2 = 1.0F;
			boolean flag = f2 > 0.9F && entity instanceof Player;
			int i = s != null && s.hasItemMeta() && s.getItemMeta().hasEnchant(Enchantment.KNOCKBACK) ? 1 : 0;
			if (data.isSprinting() && flag) {
				++i;
			}

			boolean flag5 = !isInvulnerable(entity) && entity instanceof Player;
			if (flag5) {
				return i > 0;
			} else {
				return false;
			}
		}
	}

	public static boolean isValidAttack(KatanaPlayer data, boolean sprinting, Entity entity) {
		if (!data.isNewerThan8()) {
			return entity instanceof Player && sprinting;
		} else if (data.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_16)) {
			return false;
		} else {
			float f2 = 1.0F;
			boolean flag = f2 > 0.9F && entity instanceof Player;
			int i = 0;
			if (data.isSprinting() && flag) {
				++i;
			}

			boolean flag5 = !isInvulnerable(entity) && entity instanceof Player;
			if (flag5) {
				return i > 0;
			} else {
				return false;
			}
		}
	}
}
