/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.Collection;
import club.imanity.katana.katana;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BungeeAPI {
	public static void sendCommand(String args) {
		sendPluginMessage("katana:bban", args);
	}

	public static void sendAlert(String msg) {
		sendPluginMessage("katana:alert", msg);
	}

	public static void sendPluginMessage(String subChannel, String... args) {
		Player messenger = getRandomPlayer();
		if (messenger != null) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF(subChannel);

			for (String arg : args) {
				out.writeUTF(arg);
			}

			messenger.sendPluginMessage(katana.getInstance().getPlug(), katana.getInstance().getBungeeChannel(), out.toByteArray());
		} else {
			katana.getInstance().printCool("No player found for " + subChannel + "!");
		}
	}

	public static Player getRandomPlayer() {
		Collection<? extends Player> players = Bukkit.getServer().getOnlinePlayers();
		if (!players.isEmpty()) {
			int i = (int)((double)players.size() * Math.random());
			return players.toArray(new Player[0])[i];
		} else {
			return null;
		}
	}
}
