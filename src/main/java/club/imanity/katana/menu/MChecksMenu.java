/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.menu;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import java.util.Arrays;
import club.imanity.katana.katana;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.manager.ConfigManager;
import club.imanity.katana.util.gui.Button;
import club.imanity.katana.util.gui.Gui;
import club.imanity.katana.util.gui.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MChecksMenu {
	private static ConfigManager cfg = katana.getInstance().getConfigManager();

	public static void openMainMenu(Player opener) {
		int[] blueGlass = new int[]{1, 3, 5, 7, 9, 17, 19, 25, 27, 35, 37, 39, 41, 43};
		int[] whiteGlass = new int[]{0, 2, 4, 6, 8, 10, 16, 18, 26, 28, 34, 36, 38, 42, 44};
		String highlight = katana.getInstance().getConfigManager().getAlertHoverMessageHighlight();
		String acname = katana.getInstance().getConfigManager().getName();
		final Gui gui = new Gui(ChatColor.translateAlternateColorCodes('&', highlight + "&l" + acname + "&7 - Checks"), 45);
		if (katana.SERVER_VERSION.isNewerThan(ServerVersion.V_1_12_2)) {
			for (int pos : blueGlass) {
				gui.addItem(1, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE), pos);
			}

			for (int pos : whiteGlass) {
				gui.addItem(1, new ItemStack(Material.WHITE_STAINED_GLASS_PANE), pos);
			}
		} else {
			for (int pos : blueGlass) {
				gui.addItem(1, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short)3), pos);
			}

			for (int pos : whiteGlass) {
				gui.addItem(1, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short)0), pos);
			}
		}

		for (final SubCategory categoryShit : SubCategory.values()) {
			Material type = categoryShit.getItem();
			String name = categoryShit.name();
			gui.addButton(
				new Button(
					1, categoryShit.getSlot(), ItemUtil.makeItem(type, 1, cfg.getGuiHighlightColor() + name, Arrays.asList("§7§m", "§7Manage checks", "§7§m"))
				) {
					@Override
					public void onClick(Player clicker, ClickType clickType) {
						gui.close(clicker);
						ChecksMenuLegacy.openCheckSettingGUI(clicker, categoryShit);
					}
				}
			);
		}

		gui.addButton(
			new Button(
				1,
				40,
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
}
