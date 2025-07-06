/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.world.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.Map;
import club.imanity.katana.util.gui.Callback;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public interface IChunkManager {
	void getChunk(Location location, Callback<Chunk> callback);

	Block getChunkBlockAt(Location location);

	void onChunkUnload(Chunk chunk);

	void onChunkLoad(Chunk chunk);

	void addWorld(World world);

	void removeWorld(World world);

	boolean isChunkLoaded(Location location);

	void unloadAll();

	int getCacheSize(World world);

	Map<World, Long2ObjectMap<Chunk>> getLoadedChunks();
}
