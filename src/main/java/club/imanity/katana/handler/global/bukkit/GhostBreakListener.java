/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.global.bukkit;

import club.imanity.katana.katana;
import club.imanity.katana.check.impl.world.block.GhostBreak;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.handler.collision.type.MaterialChecks;
import club.imanity.katana.util.MathUtil;
import club.imanity.katana.util.mc.axisalignedbb.AxisAlignedBB;
import club.imanity.katana.util.mc.axisalignedbb.Ray;
import club.imanity.katana.util.player.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class GhostBreakListener implements Listener {
	@EventHandler(
		priority = EventPriority.HIGHEST
	)
	public void onGhostBreak(BlockBreakEvent e) {
		if (!e.isCancelled()) {
			Player p = e.getPlayer();
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(p.getUniqueId());
			if (data != null) {
				GhostBreak check = data.getCheckManager().getCheck(GhostBreak.class);
				if (katana.getInstance().getCheckState().isEnabled(check.getName())) {
					if (data.isRiding()) {
						return;
					}

					Block block = e.getBlock();
					Location playerLoc = e.getPlayer().getLocation();
					if (playerLoc.getWorld() == null) {
						return;
					}

					Location loc = block.getLocation();
					Vector locVec = loc.toVector();
					Material material = block.getType();
					if (MaterialChecks.STAIRS.contains(material)
						|| block.isLiquid()
						|| MaterialChecks.TORCHES.contains(material)
						|| MaterialChecks.BUTTONS.contains(material)
						|| MaterialChecks.SIGNS.contains(material)
						|| MaterialChecks.CLIMBABLE.contains(material)
						|| MaterialChecks.HALFS.contains(material)
						|| MaterialChecks.TRAPS.contains(material)
						|| material.name().contains("SNOW")) {
						return;
					}

					Vector direction = MathUtil.getDirection(data.getLocation().getYaw(), data.getLocation().getPitch());
					Vector direction2 = MathUtil.getDirection(data.getLastLocation().getYaw(), data.getLastLocation().getPitch());
					float sneakAmount1_8 = !data.isWasSneaking() && !data.isWasWasSneaking() ? (data.isGliding() ? 0.4F : (data.isRiptiding() ? 0.4F : 1.62F)) : 1.54F;
					float sneakAmount1_13 = !data.isWasSneaking() && !data.isWasWasSneaking() ? (data.isGliding() ? 0.4F : (data.isRiptiding() ? 0.4F : 1.62F)) : 1.27F;
					Vector eyeLocation = playerLoc.toVector().clone().add(new Vector(0.0F, !data.isNewerThan12() ? sneakAmount1_8 : sneakAmount1_13, 0.0F));
					AxisAlignedBB targetAABB = new AxisAlignedBB(locVec, locVec, true);
					Vector bounds = BlockUtil.getBlockBounds(material);
					targetAABB = targetAABB.addCoord(bounds.getX(), bounds.getY(), bounds.getZ());
					double distance = targetAABB.distance(eyeLocation);
					BlockIterator iter = new BlockIterator(playerLoc.getWorld(), eyeLocation, direction, 0.0, (int)distance + 2);

					while (iter.hasNext()) {
						Block bukkitBlock = iter.next();
						Location bukkitBlockLocation = bukkitBlock.getLocation();
						Material iteratedMaterial = bukkitBlock.getType();
						if (!bukkitBlockLocation.equals(loc)
							&& !MaterialChecks.AIR.contains(iteratedMaterial)
							&& !MaterialChecks.STAIRS.contains(iteratedMaterial)
							&& !bukkitBlock.isLiquid()
							&& !MaterialChecks.TORCHES.contains(iteratedMaterial)
							&& !MaterialChecks.BUTTONS.contains(iteratedMaterial)
							&& !MaterialChecks.SIGNS.contains(iteratedMaterial)
							&& !MaterialChecks.CLIMBABLE.contains(iteratedMaterial)
							&& !MaterialChecks.HALFS.contains(iteratedMaterial)
							&& !MaterialChecks.ONETAPS.contains(iteratedMaterial)
							&& !MaterialChecks.GRASS.contains(iteratedMaterial)
							&& !iteratedMaterial.name().contains("SNOW")) {
							Vector bukkitLocVec = bukkitBlockLocation.toVector();
							AxisAlignedBB iteratedBlockAABB = new AxisAlignedBB(bukkitLocVec, bukkitLocVec, true);
							Vector boundsIterated = BlockUtil.getBlockBounds(bukkitBlock.getType());
							iteratedBlockAABB = iteratedBlockAABB.addCoord(boundsIterated.getX(), boundsIterated.getY(), boundsIterated.getZ());
							Vector occludeIntersection = iteratedBlockAABB.intersectsRay(new Ray(eyeLocation, direction), 0.0F, Float.MAX_VALUE);
							Vector occludeIntersection2 = iteratedBlockAABB.intersectsRay(new Ray(eyeLocation, direction2), 0.0F, Float.MAX_VALUE);
							if (occludeIntersection != null && occludeIntersection2 != null) {
								double dist = occludeIntersection.distance(eyeLocation);
								double dist2 = occludeIntersection2.distance(eyeLocation);
								check.setViolations(check.getViolations() + 1.0);
								if (dist < distance - 0.1 && dist2 < distance) {
									if (check.getViolations() > (double)(MathUtil.getPingInTicks(data.getTransactionPing()) + 3)) {
										check.fail(
											"Tried to break block behind walls (cancelled)\n* distance: " + distance + " | " + dist + "\n* looking at: " + bukkitBlock.getType() + "\n* broke: " + block.getType(),
											check.getBanVL(),
											200L
										);
									}

									e.setCancelled(true);
								}
								break;
							}

							check.setViolations(Math.max(0.0, check.getViolations() - 0.5));
						}
					}
				}
			}
		}
	}
}
