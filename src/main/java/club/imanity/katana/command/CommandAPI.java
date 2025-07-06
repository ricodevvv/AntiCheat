/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.command;

import club.imanity.katana.util.framework.CommandFramework;

public class CommandAPI {
	public CommandAPI(CommandFramework k) {
		k.registerCommands(this);
	}
}
