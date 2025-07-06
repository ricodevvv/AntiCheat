/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import club.imanity.katana.katana;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.api.Check;
import club.imanity.katana.data.KatanaPlayer;
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

public class ChecksMenuLegacy {
	private static ConfigManager cfg = katana.getInstance().getConfigManager();

	public static void openCheckSettingGUI(Player opener, SubCategory subCategory) {
		final Gui gui = new Gui(
			ChatColor.translateAlternateColorCodes(
				'&', katana.getInstance().getConfigManager().getAlertHoverMessageHighlight() + "&l" + katana.getInstance().getConfigManager().getName() + "&7 - " + subCategory.name()
			),
			subCategory.name().equals("AUTOCLICKER") ? 45 : 27
		);
		KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(opener);
		List<Check> guis = Arrays.stream(data.getCheckManager().getChecks()).filter(check -> check.getSubCategory() == subCategory).collect(Collectors.toList());
		int currentSlot = 0;
		gui.addButton(
			new Button(
				1,
				subCategory.name().equals("AUTOCLICKER") ? 44 : 26,
				ItemUtil.makeItem(Material.EMERALD, 1, cfg.getGuiHighlightColor() + "Back", Arrays.asList("§7§m", "§7Go back to the last menu", "§7§m"))
			) {
				@Override
				public void onClick(Player clicker, ClickType clickType) {
					gui.close(clicker);
					MChecksMenu.openMainMenu(clicker);
				}
			}
		);

		for (final Check checkClass : guis) {
			if (!checkClass.isSilent()) {
				gui.addButton(
					new Button(
						1,
						currentSlot,
						ItemUtil.makeItem(
							katana.getInstance().getCheckState().isEnabled(checkClass.getName()) ? Material.ENCHANTED_BOOK : Material.BOOK,
							1,
							cfg.getGuiHighlightColor() + (checkClass.isExperimental() ? checkClass.getName() + "§aΔ" : checkClass.getName()),
							checkClass.getCredits().equals("")
								? Arrays.asList(
									"§7§m",
									"§7Enabled: " + getCheckMark(checkClass, false),
									"§7Punishable: " + getCheckMark(checkClass, true),
									"",
									"§7Punish-VL: " + cfg.getGuiHighlightColor() + checkClass.getBanVL(),
									"§7§m"
								)
								: Arrays.asList(
									"§7§m",
									"§7Enabled: " + getCheckMark(checkClass, false),
									"§7Punishable: " + getCheckMark(checkClass, true),
									"",
									"§7Punish-VL: " + cfg.getGuiHighlightColor() + checkClass.getBanVL(),
									"",
									"" + checkClass.getCredits(),
									"§7§m"
								)
						)
					) {
						@Override
						public void onClick(Player clicker, ClickType clickType) {
							boolean openAgain = true;
							if (clickType == ClickType.LEFT) {
								if (katana.getInstance().getCheckState().isEnabled(checkClass.getName())) {
									ChecksMenuLegacy.updateCheckStatus(checkClass, false, false);
									katana.getInstance().getCheckState().setEnabled(checkClass.getName(), false);
								} else {
									ChecksMenuLegacy.updateCheckStatus(checkClass, false, true);
									katana.getInstance().getCheckState().setEnabled(checkClass.getName(), true);
								}
							} else if (clickType == ClickType.RIGHT) {
								if (katana.getInstance().getCheckState().isAutoban(checkClass.getName())) {
									ChecksMenuLegacy.updateCheckStatus(checkClass, true, false);
									katana.getInstance().getCheckState().setAutoban(checkClass.getName(), false);
								} else {
									ChecksMenuLegacy.updateCheckStatus(checkClass, true, true);
									katana.getInstance().getCheckState().setAutoban(checkClass.getName(), true);
								}
							} else if (clickType == ClickType.MIDDLE) {
								gui.close(clicker);
								ViolationMenu.openViolationGui(clicker, checkClass, checkClass.getCheckInfo(), subCategory);
								openAgain = false;
							}
	
							if (openAgain) {
								katana.getInstance().getConfigManager().saveChecks();
								katana.getInstance().getConfigManager().loadChecks(katana.getInstance().getPlug(), true);
								ItemStack stack = this.item;
								ItemMeta meta = stack.getItemMeta();
								meta.setLore(
									checkClass.getCredits().equals("")
										? Arrays.asList(
											"§7§m",
											"§7Enabled: " + ChecksMenuLegacy.getCheckMark(checkClass, false),
											"§7Punishable: " + ChecksMenuLegacy.getCheckMark(checkClass, true),
											"",
											"§7Punish-VL: " + ChecksMenuLegacy.cfg.getGuiHighlightColor() + checkClass.getBanVL(),
											"§7§m"
										)
										: Arrays.asList(
											"§7§m",
											"§7Enabled: " + ChecksMenuLegacy.getCheckMark(checkClass, false),
											"§7Punishable: " + ChecksMenuLegacy.getCheckMark(checkClass, true),
											"",
											"§7Punish-VL: " + ChecksMenuLegacy.cfg.getGuiHighlightColor() + checkClass.getBanVL(),
											"",
											"" + checkClass.getCredits(),
											"§7§m"
										)
								);
								stack.setItemMeta(meta);
								stack.setType(katana.getInstance().getCheckState().isEnabled(checkClass.getName()) ? Material.ENCHANTED_BOOK : Material.BOOK);
								this.inv.setItem(this.pos, stack);
							}
						}
					}
				);
				++currentSlot;
			}
		}

		gui.open(opener);
		opener.updateInventory();
	}

	public static String getCheckMark(Check cs, boolean ab) {
		if (ab) {
			return katana.getInstance().getCheckState().isAutoban(cs.getName()) ? "§a✔" : "§c✗";
		} else {
			return katana.getInstance().getCheckState().isEnabled(cs.getName()) ? "§a✔" : "§c✗";
		}
	}

	public static void updateCheckStatus(Check check, boolean autoban, boolean status) {
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
		if (autoban) {
			checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".autoban", status);
		} else {
			checkConfiguration.set(category + "." + realTypeName + "." + typeChars + ".enabled", status);
		}
	}
}
