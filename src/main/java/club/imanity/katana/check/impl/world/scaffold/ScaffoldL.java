/*
 * Katana Anticheat - BETA
 * This Anticheat belongs to Imanity Network
 */

package club.imanity.katana.check.impl.world.scaffold;

import lombok.SneakyThrows;
import club.imanity.katana.api.check.Category;
import club.imanity.katana.api.check.CheckInfo;
import club.imanity.katana.api.check.SubCategory;
import club.imanity.katana.check.type.PacketCheck;
import club.imanity.katana.data.KatanaPlayer;
import club.imanity.katana.event.BlockPlaceEvent;
import club.imanity.katana.event.Event;
import club.imanity.katana.event.FlyingEvent;
import club.imanity.katana.util.player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

@CheckInfo(
	name = "Scaffold (L)",
	category = Category.WORLD,
	subCategory = SubCategory.SCAFFOLD,
	experimental = true
)
public final class ScaffoldL extends PacketCheck {
	private int movements;
	private int jumps;
	private int noJump;
	private int sameYStreak;
	private double lastY;
	private boolean sameY;
	private boolean placed;

	public ScaffoldL(KatanaPlayer data, club.imanity.katana.katana katana) {
		super(data, katana);
	}

	@Override
	public void handle(Event packet) {
		if (!this.data.isNewerThan16()) {
			if (packet instanceof BlockPlaceEvent) {
				Vector pos = ((BlockPlaceEvent)packet).getBlockPos();
				if (pos.getX() != -1.0 && pos.getY() != 255.0 && pos.getY() != -1.0 && pos.getZ() != -1.0 && this.movements <= 10) {
					ItemStack item = ((BlockPlaceEvent)packet).getItemStack();
					if (item != null && item.getType().isBlock()) {
						Vector location = this.data.getLocation().toVector();
						Vector blockLocation = ((BlockPlaceEvent)packet).getBlockPos();
						if (location.distance(blockLocation) <= 2.0 && this.isNotGroundBridging() && this.isNotGroundBridging2() && location.getY() > blockLocation.getY()) {
							this.sameY = this.lastY == blockLocation.getY();
							this.lastY = blockLocation.getY();
							this.placed = true;
						}
					}
				}

				this.movements = 0;
			} else if (packet instanceof FlyingEvent) {
				++this.movements;
				if (this.placed) {
					boolean eligible = this.data.deltas.deltaXZ > (double)PlayerUtil.getBaseSpeedAttribute(this.data, 1.8F) && this.data.elapsed(this.data.getLastVelocityTaken()) > 5;
					if (eligible) {
						if (!this.sameY) {
							this.sameYStreak = 0;
						}

						if (this.data.isJumped() || this.data.isJumpedLastTick() || !this.data.isOnGroundPacket() && this.sameY) {
							if (this.sameY) {
								++this.sameYStreak;
							}

							++this.jumps;
						} else {
							++this.noJump;
						}
					}
				}

				if (this.jumps > 10 && this.noJump >= 0) {
					if (this.jumps > this.noJump && this.sameYStreak > 2) {
						String info = String.format("J %s, NJ %s SY %s", this.jumps, this.noJump, this.sameYStreak);
						this.fail("* Scaffold pattern\n" + info, this.getBanVL(), 250L);
					}

					this.sameYStreak = this.jumps = this.noJump = 0;
				}

				this.placed = false;
			}
		}
	}

	@SneakyThrows
	public boolean isNotGroundBridging() {
			Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(this.data.getLocation().clone().subtract(0.0, 2.0, 0.0).toLocation(this.data.getWorld()));
			if (block == null) {
				return false;
			} else {
				return block.getType() == Material.AIR;
			}

	}
	@SneakyThrows
	public boolean isNotGroundBridging2() {
		Block block = club.imanity.katana.katana.getInstance().getChunkManager().getChunkBlockAt(this.data.getLocation().clone().subtract(0.0, 3.0, 0.0).toLocation(this.data.getWorld()));
		if (block == null) {
			return false;
		} else {
			return block.getType() == Material.AIR;
		}
	}
}
