/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.world.chunk;

import club.imanity.katana.katana;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public final class ChunkListeners implements Listener {
	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onChunkLoad(ChunkLoadEvent e) {
		katana.getInstance().getChunkManager().onChunkLoad(e.getChunk());
	}

	@EventHandler(
		priority = EventPriority.LOWEST
	)
	public void onChunkUnload(ChunkUnloadEvent e) {
		katana.getInstance().getChunkManager().onChunkUnload(e.getChunk());
	}

	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		katana.getInstance().getChunkManager().addWorld(e.getWorld());

		for (Chunk chunk : e.getWorld().getLoadedChunks()) {
			katana.getInstance().getChunkManager().onChunkLoad(chunk);
		}
	}

	@EventHandler(
		priority = EventPriority.MONITOR
	)
	public void onWorldUnload(WorldUnloadEvent e) {
		if (!e.isCancelled()) {
			katana.getInstance().getChunkManager().removeWorld(e.getWorld());
		}
	}
}
