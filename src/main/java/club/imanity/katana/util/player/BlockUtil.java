/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.util.player;

import java.util.List;
import java.util.function.Consumer;
import club.imanity.katana.katana;
import club.imanity.katana.handler.collision.type.MaterialChecks;
import club.imanity.katana.util.mc.boundingbox.BoundingBox;
import club.imanity.katana.util.task.Tasker;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public final class BlockUtil {
	public static void getTileEntitiesSync(BoundingBox box, Consumer<List<Block>> listConsumer) {
		Tasker.run(() -> listConsumer.accept(box.getCollidingAir()));
	}

	public static boolean chunkLoaded(World w, int x, int z) {
		Location loc = new Location(w, (double)x, 0.0, (double)z);
		return chunkLoaded(loc);
	}

	public static boolean chunkLoaded(Location loc) {
		return katana.getInstance().getChunkManager().isChunkLoaded(loc);
	}

	public static long getChunkPair(Chunk chunk) {
		return (long)chunk.getX() << 32 | (long)chunk.getZ() & 4294967295L;
	}

	public static long getChunkPair(int x, int z) {
		return ((long)x & 4294967295L) << 32 | (long)z & 4294967295L;
	}

	public static long getChunkPair(Location location) {
		return (long)(location.getBlockX() >> 4) << 32 | (long)(location.getBlockZ() >> 4) & 4294967295L;
	}

	public static Vector getBlockBounds(Material material) {
		return MaterialChecks.BED.contains(material)
			? new Vector(1.0F, 0.5625F, 1.0F)
			: (
				MaterialChecks.FENCES.contains(material)
					? new Vector(1.0F, 1.5F, 1.0F)
					: (
						MaterialChecks.CLIMBABLE.contains(material)
							? new Vector(0.8625F, 1.0F, 0.8625F)
							: (
								MaterialChecks.CARPETS.contains(material)
									? new Vector(1.0F, 0.0625F, 1.0F)
									: (
										MaterialChecks.HALFS.contains(material)
											? new Vector(1.0F, 0.5F, 1.0F)
											: (MaterialChecks.PORTAL.contains(material) ? new Vector(1.0F, 0.8125F, 1.0F) : new Vector(1.0F, 1.0F, 1.0F))
									)
							)
					)
			);
	}
}
