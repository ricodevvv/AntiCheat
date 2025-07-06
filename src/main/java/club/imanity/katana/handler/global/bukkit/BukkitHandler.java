/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.handler.global.bukkit;

import com.github.retrooper.packetevents.manager.server.ServerVersion;
import java.util.UUID;
import club.imanity.katana.katana;
import club.imanity.katana.antivpn.VPNCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.manager.alert.MiscellaneousAlertPoster;
import club.imanity.katana.util.APICaller;
import club.imanity.katana.util.player.PlayerUtil;
import club.imanity.katana.util.task.Tasker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public final class BukkitHandler implements Listener {
	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onJoin(PlayerJoinEvent event) {
		long now = System.nanoTime();
		Player player = event.getPlayer();
		if ((!katana.getInstance().getConfigManager().isGeyserSupport() || !PlayerUtil.isGeyserPlayer(player.getUniqueId()))
			&& (!katana.getInstance().getConfigManager().isGeyserPrefixCheck() || !player.getName().contains(katana.getInstance().getConfigManager().getGeyserPrefix()))) {
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(player.getUniqueId());
			if (data == null) {
				katana.getInstance().getDataManager().add(player.getUniqueId(), now);
			} else {
				data.setBukkitPlayer(event.getPlayer());
			}

			if (katana.isAPIAvailable()) {
				APICaller.callRegister(player);
			}

			boolean permission = player.hasPermission("katana.alerts");
			Tasker.taskAsync(() -> {
				if (permission) {
					if (katana.getInstance().getConfigManager().isCrackedServer()) {
						katana.getInstance().getAlertsManager().setReceiveAlerts(player, katana.getStorage().getAlerts(player.getName()));
					} else {
						katana.getInstance().getAlertsManager().setReceiveAlerts(player, katana.getStorage().getAlerts(player.getUniqueId().toString()));
					}
				}

				if (data != null && !katana.getInstance().getConfigManager().isLogSync()) {
					if (!katana.getInstance().getConfigManager().isCrackedServer()) {
						katana.getStorage().loadActiveViolations(player.getUniqueId().toString(), data);
					} else {
						katana.getStorage().loadActiveViolations(player.getName(), data);
					}
				}
			});
		}
	}

	@EventHandler(
		priority = EventPriority.MONITOR
	)
	public void onJoinMonitor(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (katana.getInstance().getConfigManager().isGeyserSupport() && PlayerUtil.isGeyserPlayer(player)) {
			katana.getInstance().printCool(ChatColor.RED + player.getName() + " joined using geyser");
			katana.getInstance().getDataManager().remove(player.getUniqueId());
		}
	}

	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onQuit(PlayerQuitEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		if (katana.getInstance().getAlertsManager().hasAlertsToggled(uuid)) {
			katana.getInstance().getAlertsManager().removeFromList(uuid);
		}

		katana.getInstance().getDataManager().remove(uuid);
		if (katana.isAPIAvailable()) {
			APICaller.callUnregister(null);
		}
	}

	@EventHandler(
		priority = EventPriority.HIGHEST,
		ignoreCancelled = true
	)
	public void onTeleport(PlayerTeleportEvent e) {
		if (katana.SERVER_VERSION.isNewerThanOrEquals(ServerVersion.V_1_13)) {
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(e.getPlayer());
			if (data != null) {
				data.getCollisionHandler().cacheBlocks();
			}
		}
	}

	@EventHandler(
		priority = EventPriority.HIGHEST,
		ignoreCancelled = true
	)
	public void onMove(PlayerMoveEvent e) {
		Location to = e.getTo();
		Location from = e.getFrom();
		if (to != null && to.getWorld() == from.getWorld() && to.distanceSquared(from) >= 2.0E-4) {
			Player p = e.getPlayer();
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(p);
			if (data != null) {
				if (data.getBukkitPlayer() == null) {
					data.setBukkitPlayer(p);
				}

				if (++data.moveCalls % 5 == 0 && katana.SERVER_VERSION.isOlderThan(ServerVersion.V_1_19)) {
					data.getWrappedEntity().initChunks();
				}

				if (katana.SERVER_VERSION.isNewerThanOrEquals(ServerVersion.V_1_13)) {
					data.getCollisionHandler().cacheBlocks();
				}
			}
		}
	}

	@EventHandler(
		priority = EventPriority.HIGHEST
	)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!e.isCancelled() && e.getDamager() instanceof Player) {
			Player player = (Player)e.getDamager();
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(player);
			if (data != null) {
				boolean hitbox = katana.getInstance().getConfigManager().isHitboxCancel();
				boolean reach = katana.getInstance().getConfigManager().isReachCancel();
				boolean tripleBlock = katana.getInstance().getConfigManager().isTriplehitBlock();
				if (data.isCancelNextHitR() && reach) {
					e.setDamage(0.0);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> hit cancelled after reach flag");
				} else if (data.isCancelNextHitH() && hitbox) {
					e.setDamage(0.0);
					e.setCancelled(true);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> hit cancelled after hitbox flag");
				}

				if (data.elapsed(data.getCancelHitsTick()) < 5) {
					e.setDamage(0.0);
					e.setCancelled(true);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> hit cancelled for suspicious actions");
				}

				if (data.isForceCancelReach() && e.getEntity().getEntityId() == data.getEntityIdCancel()) {
					e.setDamage(0.0);
					e.setCancelled(true);
					data.setForceCancelReach(false);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> hit cancelled, katana doesn't track opponent locations");
				}

				if (tripleBlock && data.isCancelTripleHit()) {
					e.setDamage(0.0);
					e.setCancelled(true);
					data.setForceCancelReach(false);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> hit cancelled for triple hit");
				}

				if (data.isReduceNextDamage()) {
					e.setDamage(e.getDamage() * 0.75);
					data.setReduceNextDamage(false);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> damage slightly reduced for suspicious actions (1)");
				}

				if (data.isAbusingVelocity()) {
					e.setDamage(e.getDamage() * 0.7);
					data.setAbusingVelocity(false);
					MiscellaneousAlertPoster.postMitigation(player.getName() + " -> damage slightly reduced for suspicious actions (2)");
				}
			}
		}
	}

	@EventHandler(
		priority = EventPriority.HIGHEST
	)
	public void onBlockBreak(BlockBreakEvent e) {
		if (!e.isCancelled()) {
			Player player = e.getPlayer();
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(player);
			if (data != null && data.isCancelBreak()) {
				e.setCancelled(true);
				data.setCancelBreak(false);
			}
		}
	}

	@EventHandler(
		priority = EventPriority.MONITOR
	)
	public void onPreJoin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		if (katana.getInstance().getConfigManager().isAntivpn()
			&& (katana.getInstance().getConfigManager().isProxycheck() || katana.getInstance().getConfigManager().isMaliciouscheck())
			&& !katana.getInstance().getConfigManager().getAntiVpnBypass().contains(player.getUniqueId().toString())) {
			katana.getInstance().getAntiVPNThread().execute(() -> {
				if (VPNCheck.checkAddress(e.getAddress())) {
					e.disallow(Result.KICK_BANNED, katana.getInstance().getConfigManager().getAntivpnKickMsg());
				}
			});
		}
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		Player p = event.getPlayer();
		KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(p);
		if (data != null) {
			this.recorrectPlayerStates(data);
		}
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		if (e.getEntity().getType() == EntityType.PLAYER) {
			Player p = (Player)e.getEntity();
			KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(p);
			if (data != null && data.isRecorrectingSprint()) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(
		priority = EventPriority.MONITOR,
		ignoreCancelled = true
	)
	public void onEvent(BlockPistonExtendEvent event) {
		World blockWorld = event.getBlock().getWorld();
		Location blockLocation = event.getBlock().getLocation();

		for (Player player : event.getBlock().getWorld().getPlayers()) {
			if (!blockWorld.equals(player.getWorld())) {
				return;
			}

			if (blockLocation.distance(player.getLocation()) <= 14.0) {
				KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(player);
				data.queueToPrePing(task -> {
					data.setLastPistonPush(data.getTotalTicks());
					data.setLastSlimePistonPush(data.getTotalTicks());
				});
			}
		}
	}

	@EventHandler(
		priority = EventPriority.MONITOR,
		ignoreCancelled = true
	)
	public void onEvent(BlockPistonRetractEvent event) {
		Block block = event.getBlock();
		Location blockLocation = block.getLocation();

		for (Player player : block.getWorld().getPlayers()) {
			if (!block.getWorld().equals(player.getWorld())) {
				return;
			}

			if (blockLocation.distance(player.getLocation()) <= 14.0) {
				KatanaPlayer data = katana.getInstance().getDataManager().getPlayerData(player);
				data.queueToPrePing(task -> {
					data.setLastPistonPush(data.getTotalTicks());
					data.setLastSlimePistonPush(data.getTotalTicks());
				});
			}
		}
	}

	private void recorrectPlayerStates(KatanaPlayer data) {
		data.setRecorrectingSprint(true);
		data.setDesyncSprint(true);
		data.setLastWorldChange(data.getTotalTicks());
		data.getBukkitPlayer().setSprinting(true);
		data.getBukkitPlayer().setSprinting(false);
	}
}
