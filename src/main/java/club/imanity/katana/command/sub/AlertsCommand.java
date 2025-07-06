/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.command.sub;

import club.imanity.katana.katana;
import club.imanity.katana.command.CommandAPI;
import club.imanity.katana.manager.ConfigManager;
import club.imanity.katana.util.framework.Command;
import club.imanity.katana.util.framework.CommandArgs;
import club.imanity.katana.util.framework.CommandFramework;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AlertsCommand extends CommandAPI {
	public AlertsCommand(CommandFramework k) {
		super(k);
	}

	@Command(
		name = "alerts",
		permission = "katana.alerts"
	)
	public void onCommand(CommandArgs command) {
		Player player = command.getPlayer();
		String[] args = command.getArgs();
		if (command.getLabel().equalsIgnoreCase("alerts")) {
			ConfigManager cfg = katana.getInstance().getConfigManager();
			if (command.getSender() instanceof Player) {
				katana.getInstance().getAlertsManager().toggleAlerts(player);
				player.sendMessage(
					katana.getInstance().getAlertsManager().hasAlertsToggled(player.getUniqueId())
						? ChatColor.translateAlternateColorCodes('&', cfg.getConfig().getString("commands.alerts.enabled"))
						: ChatColor.translateAlternateColorCodes('&', cfg.getConfig().getString("commands.alerts.disabled"))
				);
			}
		}
	}
}
