/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.world.packet;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Map;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.util.player.BlockUtil;
import club.imanity.katana.world.packet.util.WrappedChunk;

public class KatanaWorld {
	public final KatanaPlayer data;
	public final Map<Long, WrappedChunk> chunks;

	public KatanaWorld(KatanaPlayer data) {
		this.data = data;
		this.chunks = new Long2ObjectOpenHashMap(81, 0.5F);
	}

	public void updateBlock(int x, int y, int z, int blockID) {
		WrappedChunk wrappedChunk = this.getChunk(x >> 4, z >> 4);
	}

	public WrappedChunk getChunk(int chunkX, int chunkZ) {
		long chunkPosition = BlockUtil.getChunkPair(chunkX, chunkZ);
		return this.chunks.get(chunkPosition);
	}
}
