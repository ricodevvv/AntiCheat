/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.menu;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import java.util.Arrays;
import club.imanity.katana.katana;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.api.Check;
import club.imanity.katana.manager.ConfigManager;
import club.imanity.katana.util.gui.Button;
import club.imanity.katana.util.gui.Gui;
import club.imanity.katana.util.gui.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ViolationMenu {
	private static ConfigManager cfg = katana.getInstance().getConfigManager();

	public static void openViolationGui(Player opener, Check check, CheckInfo checkInfo, SubCategory subCategory) {
		final Gui gui = new Gui(
			ChatColor.translateAlternateColorCodes(
				'&', katana.getInstance().getConfigManager().getAlertHoverMessageHighlight() + "&l" + katana.getInstance().getConfigManager().getName() + "&7 - " + checkInfo.name()
			),
			27
		);
		final String name = checkInfo.name();
		if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
			gui.addButton(
				new Button(
					1,
					9,
					ItemUtil.makeItem(
						Material.RED_STAINED_GLASS_PANE, 1, cfg.getGuiHighlightColor() + "§2-1VL", Arrays.asList("§7§m", "§7Subtract by §c1 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 1, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(1, 10, ItemUtil.makeItem(Material.RED_STAINED_GLASS_PANE, 1, "§c-5VL", Arrays.asList("§7§m", "§7Subtract by 5 vl", "§7§m"))) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 5, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(1, 11, ItemUtil.makeItem(Material.RED_STAINED_GLASS_PANE, 1, "§c-10VL", Arrays.asList("§7§m", "§7Subtract by §c10 vl", "§7§m"))) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 10, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
		} else {
			gui.addButton(
				new Button(
					1,
					9,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)14,
						1,
						cfg.getGuiHighlightColor() + "§c-1VL",
						Arrays.asList("§7§m", "§7Substract by §c1 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 1, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(
					1,
					10,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)14,
						1,
						cfg.getGuiHighlightColor() + "§c-5VL",
						Arrays.asList("§7§m", "§7Substract by §c5 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 5, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(
					1,
					11,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)14,
						1,
						cfg.getGuiHighlightColor() + "§c-10VL",
						Arrays.asList("§7§m", "§7Substract by §c10 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 10, false);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
		}

		gui.addButton(
			new Button(
				1,
				13,
				ItemUtil.makeItem(
					katana.getInstance().getCheckState().isEnabled(checkInfo.name()) ? Material.ENCHANTED_BOOK : Material.BOOK,
					1,
					cfg.getGuiHighlightColor() + (checkInfo.experimental() ? checkInfo.name() + "§aΔ" : checkInfo.name()),
					checkInfo.credits().equals("")
						? Arrays.asList(
							"§7§m",
							"§7Enabled: " + getCheckMark(name, false),
							"§7Punishable: " + getCheckMark(name, true),
							"",
							"§7Punish-VL: " + cfg.getGuiHighlightColor() + katana.getInstance().getCheckState().getCheckVl(name),
							"§7§m"
						)
						: Arrays.asList(
							"§7§m",
							"§7Enabled: " + getCheckMark(name, false),
							"§7Punishable: " + getCheckMark(name, true),
							"",
							"§7Punish-VL: " + cfg.getGuiHighlightColor() + katana.getInstance().getCheckState().getCheckVl(name),
							"",
							"" + checkInfo.credits(),
							"§7§m"
						)
				)
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					gui.close(clicker);
					MChecksMenu.openMainMenu(clicker);
				}
			}
		);
		if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
			gui.addButton(
				new Button(
					1,
					15,
					ItemUtil.makeItem(
						Material.GREEN_STAINED_GLASS_PANE, 1, cfg.getGuiHighlightColor() + "§21VL", Arrays.asList("§7§m", "§7Additon by §21 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 1, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(1, 16, ItemUtil.makeItem(Material.GREEN_STAINED_GLASS_PANE, 1, "§25VL", Arrays.asList("§7§m", "§7Additon by §25 vl", "§7§m"))) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 5, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(1, 17, ItemUtil.makeItem(Material.GREEN_STAINED_GLASS_PANE, 1, "§2-0VL", Arrays.asList("§7§m", "§7Additon by §210 vl", "§7§m"))) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 10, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
		} else {
			gui.addButton(
				new Button(
					1,
					15,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)5,
						1,
						cfg.getGuiHighlightColor() + "§21VL",
						Arrays.asList("§7§m", "§7Additon by §21 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 1, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(
					1,
					16,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)5,
						1,
						cfg.getGuiHighlightColor() + "§25VL",
						Arrays.asList("§7§m", "§7Additon by §25 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 5, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
			gui.addButton(
				new Button(
					1,
					17,
					ItemUtil.makeItem(
						Material.getMaterial("STAINED_GLASS_PANE"),
						(short)5,
						1,
						cfg.getGuiHighlightColor() + "§210VL",
						Arrays.asList("§7§m", "§7Additon by §210 vl", "§7§m")
					)
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						int newVl = ViolationMenu.updateCheckVl(check, 10, true);
						katana.getInstance().getCheckState().setPunishVl(checkInfo.name(), newVl);
						katana.getInstance().getConfigManager().saveChecks();
						katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
						ViolationMenu.updateCheckMiddle(this, checkInfo, name);
					}
				}
			);
		}

		gui.addButton(
			new Button(
				1,
				26,
				ItemUtil.makeItem(Material.EMERALD, 1, cfg.getGuiHighlightColor() + "Back", Arrays.asList("§7§m", "§7Go back to the last menu", "§7§m"))
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					gui.close(clicker);
					ChecksMenuLegacy.openCheckSettingGUI(clicker, subCategory);
				}
			}
		);
		gui.open(opener);
		opener.updateInventory();
	}

	public static String getCheckMark(String name, boolean ab) {
		if (ab) {
			return katana.getInstance().getCheckState().isAutoban(name) ? "§a✔" : "§c✗";
		} else {
			return katana.getInstance().getCheckState().isEnabled(name) ? "§a✔" : "§c✗";
		}
	}

	public static int updateCheckVl(Check check, int decinc, boolean increment) {
		ConfigManager checkConfig = katana.getInstance().getConfigManager();
		FileConfiguration checkConfiguration = checkConfig.getChecks();
		String name = check.getName();
		String category = check.getCategory().name();
		String[] idk;
		if (name.contains(" ")) {
			idk = name.split(" ");
		} else {
			idk = new String[]{name, "(A)"};
		}

		String realTypeName = idk[0];
		String typeChars = idk[1].replaceAll("[^a-zA-Z0-9]", "");
		int vl;
		if (increment) {
			vl = katana.getInstance().getCheckState().getCheckVl(name) + decinc;
		} else {
			vl = katana.getInstance().getCheckState().getCheckVl(name) - decinc;
		}

		if (vl < 1) {
			vl = 1;
		}

		checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".punish-vl", vl);
		return vl;
	}

	public static void updateCheckMiddle(Button button, CheckInfo checkInfo, String name) {
		ItemStack stack = button.item;
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(
			checkInfo.credits().equals("")
				? Arrays.asList(
					"§7§m",
					"§7Enabled: " + getCheckMark(name, false),
					"§7Punishable: " + getCheckMark(name, true),
					"",
					"§7Punish-VL: " + cfg.getGuiHighlightColor() + katana.getInstance().getCheckState().getCheckVl(name),
					"§7§m"
				)
				: Arrays.asList(
					"§7§m",
					"§7Enabled: " + getCheckMark(name, false),
					"§7Punishable: " + getCheckMark(name, true),
					"",
					"§7Punish-VL: " + cfg.getGuiHighlightColor() + katana.getInstance().getCheckState().getCheckVl(name),
					"",
					"" + checkInfo.credits(),
					"§7§m"
				)
		);
		stack.setItemMeta(meta);
		stack.setType(katana.getInstance().getCheckState().isEnabled(checkInfo.name()) ? Material.ENCHANTED_BOOK : Material.BOOK);
		button.inv.setItem(13, stack);
	}
}
