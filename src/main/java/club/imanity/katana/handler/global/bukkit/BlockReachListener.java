/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.global.bukkit;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import club.imanity.katana.katana;
import club.imanity.katana.check.impl.world.block.BlockReach;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.mc.axisalignedbb.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

public class BlockReachListener implements Listener {
	@EventHandler(
		priority = EventPriority.HIGHEST
	)
	public void onBlockReach(BlockBreakEvent e) {
		Player p = e.getPlayer();
		KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(p.getUniqueId());
		if (data != null) {
			BlockReach check = data.getCheckManager().getCheck(BlockReach.class);
			if (katana.getInstance().getCheckState().isEnabled(check.getName())) {
				Block block = e.getBlock();
				Location playerLoc = p.getLocation();
				if (playerLoc.getWorld() == null) {
					return;
				}

				Location loc = block.getLocation();
				float sneakAmount1_8 = !data.isWasSneaking() && !data.isWasWasSneaking() ? (data.isGliding() ? 0.4F : (data.isRiptiding() ? 0.4F : 1.62F)) : 1.54F;
				float sneakAmount1_13 = !data.isWasSneaking() && !data.isWasWasSneaking() ? (data.isGliding() ? 0.4F : (data.isRiptiding() ? 0.4F : 1.62F)) : 1.27F;
				Vector eyeLocation = playerLoc.toVector().clone().add(new Vector(0.0F, !data.isNewerThan12() ? sneakAmount1_8 : sneakAmount1_13, 0.0F));
				AxisAlignedBB targetAABB = new AxisAlignedBB(loc.toVector(), loc.toVector(), true);
				targetAABB = targetAABB.addCoord(1.0, 1.0, 1.0);
				double distance = targetAABB.distance(eyeLocation);
				if (distance > (data.getGameMode() == GameMode.CREATIVE ? 6.1 : 5.1) && !data.isRiding()) {
					check.fail("Tried to break block too far (cancelled)\n* distance: " + distance + "\n* broke: " + block.getType(), check.getBanVL(), 200L);
					e.setCancelled(true);
				}
			}
		}
	}
}
