/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.menu;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import java.util.Arrays;
import club.imanity.katana.katana;
import club.imanity.katana.manager.ConfigManager;
import club.imanity.katana.util.gui.Button;
import club.imanity.katana.util.gui.Gui;
import club.imanity.katana.util.gui.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsMenu {
	private static ConfigManager cfg = katana.getInstance().getConfigManager();

	public static Material getItem(boolean conf) {
		return conf ? Material.ENCHANTED_BOOK : Material.BOOK;
	}

	public static void openSettingMenu(Player opener) {
		int[] blueGlass = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
		final Gui gui = new Gui(ChatColor.translateAlternateColorCodes('&', cfg.getGuiHighlightColor() + katana.getInstance().getConfigManager().getName() + "§7 - Settings"), 27);
		if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
			for (int pos : blueGlass) {
				gui.addItem(1, new ItemStack(Material.WHITE_STAINED_GLASS_PANE), pos);
			}
		} else {
			for (int pos : blueGlass) {
				gui.addItem(1, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short)0), pos);
			}
		}

		if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
			gui.addItem(1, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE), 9);
			gui.addItem(1, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE), 17);
		} else {
			gui.addItem(1, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short)3), 9);
			gui.addItem(1, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short)3), 17);
		}

		gui.addButton(
			new Button(
				1,
				10,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isAnticrash()),
					1,
					cfg.getGuiHighlightColor() + "Anticrash",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isAnticrash()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isAnticrash()) {
						katana.getInstance().getConfigManager().getConfig().set("anticrash.enabled", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("anticrash.enabled", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isAnticrash()), "§7§m"));
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isAnticrash()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				11,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isAutoban()),
					1,
					cfg.getGuiHighlightColor() + "Autoban",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isAutoban()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isAutoban()) {
						katana.getInstance().getConfigManager().getConfig().set("autoban", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("autoban", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isAutoban()), "§7§m"));
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isAutoban()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				12,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isPunishBroadcast()),
					1,
					cfg.getGuiHighlightColor() + "Punishment broadcast",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isPunishBroadcast()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isPunishBroadcast()) {
						katana.getInstance().getConfigManager().getConfig().set("Punishments.broadcast", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("Punishments.broadcast", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(
						Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isPunishBroadcast()), "§7§m")
					);
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isPunishBroadcast()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				13,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isBypass()),
					1,
					cfg.getGuiHighlightColor() + "Bypass permission",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isBypass()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isBypass()) {
						katana.getInstance().getConfigManager().getConfig().set("bypass-permission", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("bypass-permission", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isBypass()), "§7§m"));
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isBypass()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				14,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isClientCheck()),
					1,
					cfg.getGuiHighlightColor() + "Client check",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isClientCheck()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isClientCheck()) {
						katana.getInstance().getConfigManager().getConfig().set("client-check", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("client-check", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(
						Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isClientCheck()), "§7§m")
					);
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isClientCheck()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				15,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isPullback()),
					1,
					cfg.getGuiHighlightColor() + "Pullback",
					Arrays.asList(
						"§7§m",
						"§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isPullback()),
						"§7Mode: §b" + katana.getInstance().getConfigManager().getPullbackMode(),
						"§7§m"
					)
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (clickType == ClickType.LEFT) {
						if (katana.getInstance().getConfigManager().isPullback()) {
							katana.getInstance().getConfigManager().getConfig().set("pullback.enabled", false);
						} else {
							katana.getInstance().getConfigManager().getConfig().set("pullback.enabled", true);
						}
					} else if (clickType == ClickType.RIGHT) {
						if (katana.getInstance().getConfigManager().getPullbackMode().equalsIgnoreCase("to-the-void")) {
							katana.getInstance().getConfigManager().getConfig().set("pullback.type", "generic");
						} else {
							katana.getInstance().getConfigManager().getConfig().set("pullback.type", "to-the-void");
						}
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(
						Arrays.asList(
							"§7§m",
							"§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isPullback()),
							"§7Mode: §b" + katana.getInstance().getConfigManager().getPullbackMode(),
							"§7§m"
						)
					);
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isPullback()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				16,
				ItemUtil.makeItem(
					getItem(katana.getInstance().getConfigManager().isDiscordAlert()),
					1,
					cfg.getGuiHighlightColor() + "Discord webhook",
					Arrays.asList("§7§m", "§7Enabled: " + getCheckMark(katana.getInstance().getConfigManager().isDiscordAlert()), "§7§m")
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					if (katana.getInstance().getConfigManager().isDiscordAlert()) {
						katana.getInstance().getConfigManager().getConfig().set("discord.enabled", false);
					} else {
						katana.getInstance().getConfigManager().getConfig().set("discord.enabled", true);
					}
	
					katana.getInstance().getConfigManager().save();
					katana.getInstance().getConfigManager().loadConfig(katana.getInstance().getPlug(), true);
					ItemStack stack = this.item;
					ItemMeta meta = stack.getItemMeta();
					meta.setLore(
						Arrays.asList("§7§m", "§7Enabled: " + SettingsMenu.getCheckMark(katana.getInstance().getConfigManager().isDiscordAlert()), "§7§m")
					);
					stack.setItemMeta(meta);
					stack.setType(SettingsMenu.getItem(katana.getInstance().getConfigManager().isDiscordAlert()));
					this.inv.setItem(this.pos, stack);
				}
			}
		);
		gui.addButton(
			new Button(
				1,
				26,
				ItemUtil.makeItem(Material.EMERALD, 1, cfg.getGuiHighlightColor() + "Back", Arrays.asList("§7§m", "§7Go back to the last menu", "§7§m"))
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					gui.close(clicker);
					KatanaMenu.openMenu(clicker);
				}
			}
		);
		gui.open(opener);
		opener.updateInventory();
	}

	public static String getCheckMark(boolean conf) {
		return conf ? "§a✔" : "§c✗";
	}
}
