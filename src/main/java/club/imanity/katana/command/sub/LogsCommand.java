/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.command.sub;

import java.util.List;
import club.imanity.katana.katana;
import club.imanity.katana.check.api.ViolationX;
import club.imanity.katana.command.CommandAPI;
import club.imanity.katana.manager.ConfigManager;
import club.imanity.katana.manager.alert.AlertsManager;
import club.imanity.katana.util.framework.Command;
import club.imanity.katana.util.framework.CommandArgs;
import club.imanity.katana.util.framework.CommandFramework;
import club.imanity.katana.util.text.TextUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LogsCommand extends CommandAPI {
	public LogsCommand(CommandFramework k) {
		super(k);
	}

	@Command(
		name = "logs",
		permission = "katana.logs"
	)
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		if (command.getSender() instanceof Player) {
			ConfigManager cfg = katana.getInstance().getConfigManager();
			if (command.getLabel().equalsIgnoreCase("logs") && (!(command.getSender() instanceof Player) || player.hasPermission("katana.logs"))) {
				Bukkit.getScheduler()
					.runTaskAsynchronously(
						katana.getInstance().getPlug(),
						() -> {
							if (args.length >= 1) {
								String uuid = this.findUUID(args[0]);
								int page = args.length == 2 ? Integer.parseInt(args[1]) : 0;
								List<ViolationX> vls = katana.storage.getViolations(uuid, null, page, 10, -1L, -1L);
								if (vls.isEmpty()) {
									player.sendMessage("§cPlayer has no logs!");
									if (!katana.getInstance().getConfigManager().isCrackedServer()) {
										return;
									}
		
									uuid = Bukkit.getOfflinePlayer(args[0]).getName();
									vls = katana.storage.getViolations(uuid, null, page, 10, -1L, -1L);
									if (vls.isEmpty()) {
										player.sendMessage("§cPlayer has no logs!");
										return;
									}
								}
		
								int maxPages = katana.storage.getAllViolations(uuid).size() / 10;
								player.sendMessage("§7Showing logs of " + cfg.getLogsHighlight() + args[0] + " §7(§a" + page + "§7/§2" + maxPages + "§7)");
		
								for (ViolationX v : vls) {
									if (!v.data.contains("PUNISHED")) {
										String textMsg = "§7* "
											+ cfg.getLogsHighlight()
											+ v.type
											+ " §7VL: "
											+ cfg.getLogsHighlight()
											+ TextUtils.format((double)v.vl, 1)
											+ " §7(§a"
											+ TextUtils.formatMillis(System.currentTimeMillis() - v.time)
											+ " ago§7)";
										BaseComponent msg = new TextComponent(
											"§7* "
												+ cfg.getLogsHighlight()
												+ v.type
												+ " §7VL: "
												+ cfg.getLogsHighlight()
												+ TextUtils.format((double)v.vl, 1)
												+ " §7(§a"
												+ TextUtils.formatMillis(System.currentTimeMillis() - v.time)
												+ " ago§7)"
										);
										msg.setHoverEvent(
											new HoverEvent(
												Action.SHOW_TEXT,
												new ComponentBuilder(
														ChatColor.translateAlternateColorCodes('&', v.data.replaceAll("§b", katana.getInstance().getConfigManager().getAlertHoverMessageHighlight()))
															+ "\n"
															+ cfg.getLogsHighlight()
															+ v.ping
															+ "§7ms, "
															+ cfg.getLogsHighlight()
															+ v.TPS
															+ "TPS"
													)
													.create()
											)
										);
										msg.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/katana teleport " + v.location + " " + v.world));
										if (!katana.getInstance().getConfigManager().getConfig().getBoolean("hoverless-alert")
											&& (player.hasPermission("katana.hover-debug") || AlertsManager.ADMINS.contains(player.getUniqueId()))) {
											if (katana.getInstance().getConfigManager().getConfig().getBoolean("spigot-api-alert")) {
												player.spigot().sendMessage(msg);
											} else {
												player.spigot().sendMessage(msg);
											}
										} else {
											player.sendMessage(textMsg);
										}
									} else {
										String textMsg = "§7* "
											+ cfg.getLogsBan()
											+ v.type
											+ " §7VL: "
											+ cfg.getLogsBan()
											+ TextUtils.format((double)v.vl, 1)
											+ " §7("
											+ cfg.getLogsBan()
											+ TextUtils.formatMillis(System.currentTimeMillis() - v.time)
											+ " ago§7)";
										BaseComponent msg = new TextComponent(
											"§7* "
												+ cfg.getLogsBan()
												+ v.type
												+ " §7VL: "
												+ cfg.getLogsBan()
												+ TextUtils.format((double)v.vl, 1)
												+ " §7("
												+ cfg.getLogsBan()
												+ TextUtils.formatMillis(System.currentTimeMillis() - v.time)
												+ " ago§7)"
										);
										msg.setHoverEvent(
											new HoverEvent(
												Action.SHOW_TEXT,
												new ComponentBuilder(
														ChatColor.translateAlternateColorCodes('&', v.data.replaceAll("§b", katana.getInstance().getConfigManager().getAlertHoverMessageHighlight()))
															+ "\n"
															+ cfg.getLogsHighlight()
															+ v.ping
															+ "§7ms, "
															+ cfg.getLogsHighlight()
															+ v.TPS
															+ "TPS"
													)
													.create()
											)
										);
										msg.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/katana teleport " + v.location + " " + v.world));
										if (!katana.getInstance().getConfigManager().getConfig().getBoolean("hoverless-alert")
											&& (player.hasPermission("katana.hover-debug") || AlertsManager.ADMINS.contains(player.getUniqueId()))) {
											if (katana.getInstance().getConfigManager().getConfig().getBoolean("spigot-api-alert")) {
												player.spigot().sendMessage(msg);
											} else {
												player.spigot().sendMessage(msg);
											}
										} else {
											player.sendMessage(textMsg);
										}
									}
								}
							} else {
								command.getSender().sendMessage("§c/" + katana.getInstance().getConfigManager().getName().toLowerCase() + " logs <player> <page>");
							}
						}
					);
			}
		}
	}

	private String findUUID(String arg) {
		if (katana.getInstance().getConfigManager().isCrackedServer()) {
			Player target = Bukkit.getPlayer(arg);
			return target != null ? arg : arg;
		} else {
			Player target = Bukkit.getPlayer(arg);
			return target != null ? target.getUniqueId().toString() : Bukkit.getOfflinePlayer(arg).getUniqueId().toString();
		}
	}
}
