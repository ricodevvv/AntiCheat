/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.manager.alert;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import club.imanity.katana.katana;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.discord.Webhook;
import club.imanity.katana.util.discord.Webhook.EmbedObject;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class MiscellaneousAlertPoster {
	public static void postMisc(String debug, KatanaPlayer data, String type) {
		String message = katana.getInstance().getConfigManager().getMiscPrefix() + debug;
		katana.getInstance().getAlertsManager().getAlertsToggled().stream().<Player>map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(staff -> staff.sendMessage(message));
		if (type.contains("Crash")) {
			handleDiscord(debug, data);
		}
	}

	public static void postMiscTp(BaseComponent hover) {
		katana.getInstance().getAlertsManager().getMiscDebugToggled().stream().<Player>map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(admin -> admin.spigot().sendMessage(hover));
	}

	public static void postMiscPrivate(String msg) {
		katana.getInstance().getAlertsManager().getMiscDebugToggled().stream().<Player>map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(admin -> admin.sendMessage(msg));
	}

	public static void postMitigation(String msg) {
		katana.getInstance().getAlertsManager().getMitigationToggled().stream().<Player>map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(admin -> admin.sendMessage(msg));
	}

	public static void postSetback(String msg) {
		katana.getInstance().getAlertsManager().getSetbackToggled().stream().<Player>map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(admin -> admin.sendMessage(msg));
	}

	public static void handleDiscord(String data, KatanaPlayer pdata) {
		Player player = pdata.getBukkitPlayer();
		String hookURL = katana.getInstance().getConfigManager().getConfig().getString("discord.crash-webhook-url");
		Webhook discord = new Webhook(hookURL);
		boolean showWorld = katana.getInstance().getConfigManager().getConfig().getBoolean("discord.show-world");
		boolean showStats = katana.getInstance().getConfigManager().getConfig().getBoolean("discord.show-statistics");
		boolean showIcon = katana.getInstance().getConfigManager().getConfig().getBoolean("discord.show-icon-thumbnail");
		discord.setUsername(katana.getInstance().getConfigManager().getName());
		discord.setTts(false);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		if (showIcon) {
			discord.addEmbed(
				new EmbedObject()
					.setTitle("```" + player.getName() + "``` | Crash ")
					.setThumbnail("https://minotar.net/avatar/" + player.getName() + "/50.png")
					.setDescription(ChatColor.stripColor(data.replaceAll("\n", "")))
					.setColor(Color.ORANGE)
					.addField(
						"Info",
						(
								showWorld
									? "World: "
										+ player.getWorld().getName()
										+ " | Coords: "
										+ format(1, player.getLocation().getX())
										+ "/"
										+ format(1, player.getLocation().getY())
										+ "/"
										+ format(1, player.getLocation().getZ())
									: " Coords: " + format(1, player.getLocation().getX()) + "/" + format(1, player.getLocation().getY()) + "/" + format(1, player.getLocation().getZ())
							)
							+ (showStats ? " | TPS: " + katana.getInstance().getTPS() + " | Ping: " + pdata.getTransactionPing() + "ms" : ""),
						false
					)
					.addField("Date", dtf.format(now), false)
			);
		} else {
			discord.addEmbed(
				new EmbedObject()
					.setTitle("```" + player.getName() + "``` | Crash ")
					.setDescription(ChatColor.stripColor(data.replaceAll("\n", "")))
					.setColor(Color.ORANGE)
					.addField(
						"Info",
						(
								showWorld
									? "World: "
										+ player.getWorld().getName()
										+ " | Coords: "
										+ format(1, player.getLocation().getX())
										+ "/"
										+ format(1, player.getLocation().getY())
										+ "/"
										+ format(1, player.getLocation().getZ())
									: " Coords: " + format(1, player.getLocation().getX()) + "/" + format(1, player.getLocation().getY()) + "/" + format(1, player.getLocation().getZ())
							)
							+ (showStats ? " | TPS: " + katana.getInstance().getTPS() + " | Ping: " + pdata.getTransactionPing() + "ms" : ""),
						false
					)
					.addField("Date", dtf.format(now), false)
			);
		}

		try {
			discord.execute();
		} catch (IOException var11) {
			if (var11.toString().contains("429")) {
				katana.getInstance().getPlug().getLogger().warning("Unable to post discord webhook: 429 Too many requests");
			} else if (!var11.getMessage().contains("no protocol")) {
				katana.getInstance().getPlug().getLogger().warning("Unable to post discord webhook: " + var11.getMessage());
			}
		}
	}

	private static String format(int places, Object obj) {
		return String.format("%." + places + "f", obj);
	}
}
