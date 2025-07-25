/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.player;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPing;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowConfirmation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes.PropertyModifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public final class PlayerUtil {
	private static final UUID SPRINTING_SPEED_BOOST = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
	public static final String legacyMovementSpeed = "generic.movementSpeed";
	public static final String movementSpeed = "minecraft:generic.movement";

	public static float getScaledFriction(KatanaPlayer data) {
		float f4 = data.getCurrentFriction();
		float f = 0.16277136F / (f4 * f4 * f4);
		float f5;
		if (data.isLastOnGroundPacket()) {
			f5 = data.getAttributeSpeed() * f;
		} else {
			f5 = data.isWasWasSprinting() ? 0.025999999F : 0.02F;
		}

		return f5;
	}

	public static float getScaledFriction(KatanaPlayer data, boolean sprinting) {
		float f4 = data.getCurrentFriction();
		float f = 0.16277136F / (f4 * f4 * f4);
		float f5;
		if (data.isLastOnGroundPacket()) {
			f5 = data.getAttributeSpeed() * f;
		} else {
			f5 = sprinting ? 0.025999999F : 0.02F;
		}

		return f5;
	}

	public static double getMovementSpeed(List<PropertyModifier> collection, double base) {
		for (PropertyModifier modifier : getModifiers(PlayerUtil.Operation.ADDITION, collection)) {
			base += modifier.getAmount();
		}

		double moveSpeed = base;

		for (PropertyModifier modifier : getModifiers(PlayerUtil.Operation.MULTIPLY_BASE, collection)) {
			moveSpeed += base * modifier.getAmount();
		}

		for (PropertyModifier modifier : getModifiers(PlayerUtil.Operation.MULTIPLY_TOTAL, collection)) {
			moveSpeed *= 1.0 + modifier.getAmount();
		}

		return moveSpeed;
	}

	private static List<PropertyModifier> getModifiers(PlayerUtil.Operation operation, List<PropertyModifier> modifiers) {
		List<PropertyModifier> results = new ArrayList<>();

		for (PropertyModifier modifier : modifiers) {
			if (!SPRINTING_SPEED_BOOST.equals(modifier.getUUID()) && getOperation(modifier.getOperation()) == operation) {
				results.add(modifier);
			}
		}

		return results;
	}

	private static PlayerUtil.Operation getOperation(com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes.PropertyModifier.Operation operation) {
		switch (operation) {
			case ADDITION:
				return PlayerUtil.Operation.ADDITION;
			case MULTIPLY_BASE:
				return PlayerUtil.Operation.MULTIPLY_BASE;
			case MULTIPLY_TOTAL:
				return PlayerUtil.Operation.MULTIPLY_TOTAL;
			default:
				return null;
		}
	}

	public static float getJumpHeight(KatanaPlayer data, float base) {
		return base + getJumpBooster(data);
	}

	public static float getJumpHeight(KatanaPlayer data) {
		return Math.max(0.0F, 0.42F * data.getJumpFactor() + getJumpBooster(data, false));
	}

	public static float getJumpBooster(KatanaPlayer data) {
		return (float)getJumpBoostLevel(data) * 0.1F;
	}

	public static float getJumpBooster(KatanaPlayer data, boolean maxed) {
		return (float)getJumpBoostLevel(data, maxed) * 0.1F;
	}

	public static int getJumpBoostLevel(KatanaPlayer data) {
		return Math.max(0, Math.max(data.getJumpBoost(), data.getCacheBoost()));
	}

	public static int getJumpBoostLevel(KatanaPlayer data, boolean maxed) {
		return maxed ? Math.max(0, Math.max(data.getJumpBoost(), data.getCacheBoost())) : Math.max(data.getJumpBoost(), data.getCacheBoost());
	}

	public static float getBaseSpeedAttribute(KatanaPlayer data, float mult) {
		return data.getWalkSpeed() * mult;
	}

	public static float getBaseSpeedAirAttribute(KatanaPlayer data, float multi) {
		return 0.35F + data.getAttributeSpeed() * multi;
	}

	public static void sendPacket(Player player, PacketWrapper<?> wrapper) {
		PacketEvents.getAPI().getPlayerManager().sendPacket(player, wrapper);
	}

	public static void sendPacket(Player player, short id) {
		if (katana.PING_PONG_MODE) {
			WrapperPlayServerPing ping = new WrapperPlayServerPing(id);
			PacketEvents.getAPI().getPlayerManager().sendPacket(player, ping);
		} else {
			WrapperPlayServerWindowConfirmation transaction = new WrapperPlayServerWindowConfirmation(0, id, false);
			PacketEvents.getAPI().getPlayerManager().sendPacket(player, transaction);
		}
	}

	public static void sendPacket(Object channel, PacketWrapper<?> wrapper) {
		PacketEvents.getAPI().getPlayerManager().sendPacket(channel, wrapper);
	}

	public static boolean isGeyserPlayer(Player player) {
		return !katana.getInstance().isFloodgate() ? false : FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
	}

	public static boolean isGeyserPlayer(UUID uuid) {
		return !katana.getInstance().isFloodgate() ? false : FloodgateApi.getInstance().isFloodgatePlayer(uuid);
	}

	public static enum Operation {
		ADDITION,
		MULTIPLY_BASE,
		MULTIPLY_TOTAL;
	}
}
